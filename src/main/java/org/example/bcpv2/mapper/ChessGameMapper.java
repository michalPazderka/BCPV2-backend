package org.example.bcpv2.mapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bcpv2.dto.ChessGameDto;
import org.example.bcpv2.dto.ColorDto;
import org.example.bcpv2.games.chess.ChessGame;
import org.example.bcpv2.games.chess.eunums.Color;
import org.example.bcpv2.games.chess.pieces.Piece;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChessGameMapper implements ChessGameMapperS {

    public ChessGameDto chessGameToChessGameDto(ChessGame chessGame) {
        var chessGameDto = new ChessGameDto();
        chessGameDto.setGameId(chessGame.getGameId());
        chessGameDto.setIsPlaying(chessGame.getIsPlaying() == Color.WHITE ? 0 : 1);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Optional<Piece> pieceOpt = chessGame.getBoard().getBoard()[i][j].getPiece();
                if(pieceOpt.isPresent()){
                    chessGameDto.setBoardValue(i,j, pieceOpt.get());
                }
            }
        }
        chessGameDto.setGame("Chess");
        chessGameDto.setRestOfColors(chessGame.getAvailableColors());
        return chessGameDto;
    }

    public ColorDto colorToColorDto(List<Color> color){
        var colorDto = new ColorDto();
        colorDto.setColor(color);
        return colorDto;
    }
}
