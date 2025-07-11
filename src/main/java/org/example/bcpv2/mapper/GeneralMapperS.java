package org.example.bcpv2.mapper;

import org.example.bcpv2.dto.AbsGameDto;
import org.example.bcpv2.dto.AbsLobbyDto;
import org.example.bcpv2.games.chess.Game;

public interface GeneralMapperS {

    public AbsLobbyDto gameToAbsLobbyDto(AbsGameDto gameDto);
}
