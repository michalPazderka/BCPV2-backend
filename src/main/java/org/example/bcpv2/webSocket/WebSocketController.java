package org.example.bcpv2.webSocket;


import lombok.AllArgsConstructor;
import org.example.bcpv2.Service.ChessService;
import org.example.bcpv2.Service.GeneralService;
import org.example.bcpv2.Service.OthelloService;
import org.example.bcpv2.dto.ChessGameDto;
import org.example.bcpv2.dto.MoveDto;
import org.example.bcpv2.dto.OthelloGameDto;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Controller
@AllArgsConstructor
public class WebSocketController {

    private final GeneralService generalService;
    private final ChessService chessService;
    private final OthelloService othelloService;
    private final Map<String, Long> disconnectTimestamps = new ConcurrentHashMap<>();
    private final Map<String, String> sessionToGame = new ConcurrentHashMap<>();
    private final Map<String, String> sessionToPlayer = new ConcurrentHashMap<>();
    private final Map<String, String> playerToSession = new ConcurrentHashMap<>();

    @MessageMapping("/chess/move")
    @SendTo("/topic/game")
    public ChessGameDto handleChessMove(@RequestBody MoveDto move) {
        return chessService.makeMove(move.getGameId(), move.getMove());
    }

    @MessageMapping("/othello/move")
    @SendTo("/topic/game")
    public OthelloGameDto handleOthelloMove(@RequestBody MoveDto move) {
        return othelloService.makeMove(move.getGameId(), move.getMove());
    }

    @EventListener
    public void handleSessionConnected(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        String gameId = accessor.getFirstNativeHeader("gameId");
        String playerId = accessor.getFirstNativeHeader("playerId");

        if (gameId != null && playerId != null) {
            sessionToGame.put(sessionId, gameId);
            sessionToPlayer.put(sessionId, playerId);
            playerToSession.put(playerId, sessionId);
            System.out.println("✅ Připojení do hry: " + gameId + " hráč " + playerId + " (session " + sessionId + ")");
        }

        disconnectTimestamps.remove(sessionId);
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        String gameId = sessionToGame.get(sessionId);
        String playerId = sessionToPlayer.get(sessionId);

        System.out.println("⚠️ Odpojení session: " + sessionId);
        disconnectTimestamps.put(sessionId, System.currentTimeMillis());

        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            Long disconnectTime = disconnectTimestamps.get(sessionId);
            String currentSession = playerToSession.get(playerId);

            if (disconnectTime != null && currentSession != null && !currentSession.equals(sessionId)) {
                System.out.println("✅ Hráč " + playerId + " se připojil znovu.");
                disconnectTimestamps.remove(sessionId);
                return;
            }

            System.out.println("❌ Hráč " + playerId + " neobnovil spojení – ukončuji hru.");
            generalService.killGame(gameId);
            disconnectTimestamps.remove(sessionId);
            sessionToGame.remove(sessionId);
            sessionToPlayer.remove(sessionId);
            playerToSession.remove(playerId);
        }, 10, TimeUnit.SECONDS);
    }
}
