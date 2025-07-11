package org.example.bcpv2.mapper;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bcpv2.dto.AbsGameDto;
import org.example.bcpv2.dto.AbsLobbyDto;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeneralMapper implements GeneralMapperS {

    @Override
    public AbsLobbyDto gameToAbsLobbyDto(AbsGameDto gameDto) {
        var lobbyDto = new AbsLobbyDto();
        lobbyDto.setId(gameDto.getGameId());
        lobbyDto.setGameName(gameDto.getGame());
        lobbyDto.setNumberOfFreePlaces((long) gameDto.getRestOfColors().size());
        return lobbyDto;
    }
}
