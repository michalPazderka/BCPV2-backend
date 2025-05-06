package org.example.bcpv2.Service;

import lombok.AllArgsConstructor;
import org.example.bcpv2.dto.ColorDto;
import org.example.bcpv2.dto.OthelloGameDto;
import org.example.bcpv2.games.chess.pieces.Piece;
import org.example.bcpv2.games.othello.OthelloGame;
import org.example.bcpv2.mapper.OthelloGameMapperS;
import org.example.bcpv2.webSocket.WebSocketService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class OthelloService {
    private final Map<String, OthelloGame> activeGames = new ConcurrentHashMap<>();
    private final WebSocketService webSocketService;
    private final OthelloGameMapperS othelloGameMapper;


    public OthelloGameDto createGame(String color) {
        //String gameId = UUID.randomUUID().toString();
        String gameId = "1";
        OthelloGame othelloGame = new OthelloGame(gameId);
        switch (color) {
            case "WHITE":
                othelloGame.setColorWhite();
                break;
            case "BLACK":
                othelloGame.setColorBlack();
                break;
            default:
                throw new IllegalArgumentException("Color does not exist");
        }
        othelloGame.getBoard().initialize();
        activeGames.put(gameId, othelloGame);
        return othelloGameMapper.othelloGameToOthelloGameDto(othelloGame);
    }

    public OthelloGameDto joinGame(String id, String color) {
        var game = activeGames.get(id);
        if (game == null) throw new IllegalArgumentException("Game does not exist");
        switch (color) {
            case "WHITE":
                game.setColorWhite();
                break;
            case "BLACK":
                game.setColorBlack();
                break;
            default:
                throw new IllegalArgumentException("Color does not exist");
        }
        return othelloGameMapper.othelloGameToOthelloGameDto(game);
    }

    public OthelloGameDto getGame(String gameId) {
        return othelloGameMapper.othelloGameToOthelloGameDto(activeGames.get(gameId));
    }

    public OthelloGameDto makeMove(String gameId, String move) {
        OthelloGame game = activeGames.get(gameId);
        if (game != null) {
            String[] moves = move.split("");
            int r = Integer.parseInt(moves[0]);
            int c = Integer.parseInt(moves[1]);
            if (game.getBoard().isEmpty(r, c)) {
                if (game.getBoard().validMove(r, c, game.getIsPlaying())) {
                    game.getBoard().addPiece(r, c, game.getIsPlaying());
                    //todo: flip barvy testovat jestli je flip validní atd...
                } else throw new IllegalArgumentException("Move is not valid");
            } else throw new IllegalArgumentException("Square is not empty");
        }
        throw new IllegalArgumentException("Game id does not exist");
    }

    public ColorDto colorDto(String gameId) {
        OthelloGame game = activeGames.get(gameId);
        if (game != null) {
            var colorList = game.getAvailableColors();
            return othelloGameMapper.colorToColorDto(colorList);
        }
        throw new IllegalArgumentException("Game id does not exist");
    }

}