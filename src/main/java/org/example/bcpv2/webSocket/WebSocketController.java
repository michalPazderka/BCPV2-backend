package org.example.bcpv2.webSocket;


import lombok.AllArgsConstructor;
import org.example.bcpv2.Service.ChessService;
import org.example.bcpv2.dto.ChessGameDto;
import org.example.bcpv2.dto.MoveDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class WebSocketController {
    private final ChessService chessService;

    @MessageMapping("/move")
    @SendTo("/topic/game")
    public ChessGameDto handleMove(@RequestBody MoveDto move) {
        return chessService.makeMove(move.getGameId(), move.getMove());
    }
}
