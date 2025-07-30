package org.example.bcpv2.Service;


import lombok.AllArgsConstructor;
import org.example.bcpv2.dto.AbsGameDto;
import org.example.bcpv2.dto.AbsLobbyDto;
import org.example.bcpv2.dto.ChessGameDto;
import org.example.bcpv2.dto.OthelloGameDto;
import org.example.bcpv2.games.abstractGame.AbsGame;
import org.example.bcpv2.mapper.GeneralMapperS;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class GeneralService {

    private final Map<Class<?extends AbsGame>, GameService<?,?>> gameServices;
    private final ChessService chessService;
    private final OthelloService othelloService;
    private final GeneralMapperS generalMapper;

    public ResponseEntity<Page<AbsLobbyDto>> getGames(Pageable pageable) {
        List<ChessGameDto> chessGames = chessService.getGames();
        List<OthelloGameDto> othelloGames = othelloService.getGames();

        List<AbsGameDto> listOfGames = Stream.concat(
                chessGames.stream(),
                othelloGames.stream()
        ).toList();

        var lobbyDtoList = listOfGames.stream()
                .map(generalMapper::gameToAbsLobbyDto)
                .toList();

        Page<AbsLobbyDto> pagedResult = createPage(lobbyDtoList, pageable);

        return ResponseEntity.ok(pagedResult);
    }

    public Page<AbsLobbyDto> createPage(List<AbsLobbyDto> allGames, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allGames.size());

        if (start > allGames.size()) {
            return new PageImpl<>(
                    new ArrayList<>(),
                    pageable,
                    allGames.size()
            );
        }

        return new PageImpl<>(
                allGames.subList(start, end),
                pageable,
                allGames.size()
        );
    }


    public AbsGameDto getGameDto(String id) {
        var chessGame = chessService.getGameDto(id);
        if (chessGame != null) return chessGame;
        return othelloService.getGameDto(id);
    }

    public AbsGame getGame(String id) {
        return gameServices.values().stream()
                .map(service -> service.getGame(id))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    public void killGame(String id) {
        var game = getGame(id);
        if (game == null) {
            throw new IllegalArgumentException("Game does not exist");
        }
        GameService<?,?> gameService = gameServices.get(game.getClass());
        gameService.killGame(id);
    }
}
