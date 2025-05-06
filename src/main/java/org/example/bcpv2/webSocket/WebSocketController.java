package org.example.bcpv2.webSocket;


import lombok.AllArgsConstructor;
import org.example.bcpv2.Service.ChessService;
import org.example.bcpv2.Service.OthelloService;
import org.example.bcpv2.dto.ChessGameDto;
import org.example.bcpv2.dto.MoveDto;
import org.example.bcpv2.dto.OthelloGameDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class WebSocketController {
    private final ChessService chessService;
    private final OthelloService othelloService;

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
}
