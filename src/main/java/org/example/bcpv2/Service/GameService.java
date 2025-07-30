package org.example.bcpv2.Service;

import org.example.bcpv2.dto.AbsGameDto;
import org.example.bcpv2.dto.ColorDto;
import org.example.bcpv2.games.abstractGame.AbsGame;

import java.util.List;

public interface GameService<T extends AbsGame, D extends AbsGameDto> {

    List<D> getGames();
    D createGame(String color);
    D joinGame(String id, String color);
    D getGameDto(String gameId);
    T getGame(String gameId);
    D makeMove(String gameId, String move);
    ColorDto colorDto(String gameId);
    void killGame(String gameId);

}
