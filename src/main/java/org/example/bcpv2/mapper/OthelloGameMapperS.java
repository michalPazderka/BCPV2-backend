package org.example.bcpv2.mapper;

import org.example.bcpv2.dto.ColorDto;
import org.example.bcpv2.dto.OthelloGameDto;
import org.example.bcpv2.games.chess.eunums.Color;
import org.example.bcpv2.games.othello.OthelloGame;

import java.util.List;

public interface OthelloGameMapperS {

    OthelloGameDto othelloGameToOthelloGameDto(OthelloGame othelloGame);

    ColorDto colorToColorDto(List<Color> color);
}
