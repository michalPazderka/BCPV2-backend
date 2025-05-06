package org.example.bcpv2.mapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bcpv2.dto.ChessGameDto;
import org.example.bcpv2.dto.ColorDto;
import org.example.bcpv2.dto.OthelloGameDto;
import org.example.bcpv2.games.chess.eunums.Color;
import org.example.bcpv2.games.chess.pieces.Piece;
import org.example.bcpv2.games.othello.OthelloGame;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OthelloGameMapper implements OthelloGameMapperS{

    @Override
    public OthelloGameDto othelloGameToOthelloGameDto(OthelloGame othelloGame) {
        var othelloGameDto = new OthelloGameDto();
        othelloGameDto.setGameId(othelloGame.getGameId());
        othelloGameDto.setIsPlaying(othelloGame.getIsPlaying() == Color.WHITE ? 0 : 1);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Optional<Piece> pieceOpt = othelloGame.getBoard().getBoard()[i][j].getPiece();
                if(pieceOpt.isPresent()){
                    othelloGameDto.setBoardValue(i,j, pieceOpt.get());
                }
            }
        }
        othelloGameDto.setRestOfColors(othelloGame.getAvailableColors());
        return othelloGameDto;
    }

    public ColorDto colorToColorDto(List<Color> color){
        var colorDto = new ColorDto();
        colorDto.setColor(color);
        return colorDto;
    }
}
