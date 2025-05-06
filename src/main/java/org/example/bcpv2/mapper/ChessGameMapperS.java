package org.example.bcpv2.mapper;

import org.example.bcpv2.dto.ChessGameDto;
import org.example.bcpv2.dto.ColorDto;
import org.example.bcpv2.games.chess.ChessGame;
import org.example.bcpv2.games.chess.eunums.Color;

import java.util.List;

public interface ChessGameMapperS {

    ChessGameDto chessGameToChessGameDto(ChessGame chessGame);

    ColorDto colorToColorDto(List<Color> color);
}
