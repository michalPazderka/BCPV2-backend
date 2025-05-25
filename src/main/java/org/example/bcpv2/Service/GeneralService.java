package org.example.bcpv2.Service;


import lombok.AllArgsConstructor;
import org.example.bcpv2.dto.AbsGameDto;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GeneralService {

    private final ChessService chessService;
    private final OthelloService othelloService;

    public AbsGameDto getGame(String id) {
        var chessGame = chessService.getGame(id);
        if (chessGame != null) return chessGame;
        return othelloService.getGame(id);
    }
}
