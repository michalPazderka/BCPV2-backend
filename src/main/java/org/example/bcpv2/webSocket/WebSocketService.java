package org.example.bcpv2.webSocket;

import org.example.bcpv2.dto.ChessGameDto;
import org.example.bcpv2.dto.OthelloGameDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyChessPlayers(String gameId, ChessGameDto chessGameDto) {
        messagingTemplate.convertAndSend("/topic/game/" + gameId, chessGameDto);
    }

    public void notifyOthelloPlayers(String gameId, OthelloGameDto othelloGameDto) {
        messagingTemplate.convertAndSend("/topic/game/" + gameId, othelloGameDto);
    }
}
