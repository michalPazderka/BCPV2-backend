package org.example.bcpv2.Service;

import lombok.AllArgsConstructor;
import org.example.bcpv2.dto.ColorDto;
import org.example.bcpv2.dto.OthelloGameDto;
import org.example.bcpv2.games.chess.eunums.Color;
import org.example.bcpv2.games.othello.OthelloGame;
import org.example.bcpv2.mapper.OthelloGameMapperS;
import org.example.bcpv2.webSocket.WebSocketService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class OthelloService implements GameService<OthelloGame, OthelloGameDto>{
    private final Map<String, OthelloGame> activeGames = new ConcurrentHashMap<>();
    private final WebSocketService webSocketService;
    private final OthelloGameMapperS othelloGameMapper;

    public List<OthelloGameDto> getGames() {
        return activeGames.values().stream().map(othelloGameMapper::othelloGameToOthelloGameDto).toList();
    }

    public ColorDto getColors(){
        return othelloGameMapper.colorToColorDto(List.of(Color.WHITE, Color.BLACK));
    }

    public OthelloGameDto createGame(String color) {
        String gameId = UUID.randomUUID().toString();
        //String gameId = "1";
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

    public OthelloGameDto getGameDto(String gameId) {
        if (activeGames.get(gameId) != null) {
            return othelloGameMapper.othelloGameToOthelloGameDto(activeGames.get(gameId));
        }
        return null;
    }

    public OthelloGame getGame(String gameId){
        return activeGames.get(gameId);
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
                    game.changeColor();
                    game.getBoard().getOthelloRules().setIsPlaying(game.getIsPlaying());
                    if (game.getBoard().getPossibleMoves(game.getIsPlaying()).isEmpty()) {
                        game.changeColor();
                        game.getBoard().getOthelloRules().setIsPlaying(game.getIsPlaying());
                    }
                    var othelloGameDto = othelloGameMapper.othelloGameToOthelloGameDto(game);
                    if (game.getBoard().getOthelloRules().isGameOver(game.getBoard())) {
                        var color = game.getBoard().getOthelloRules().getWinner(game.getBoard());
                        othelloGameDto.setWinner(color.toString());
                        killGame(gameId);
                    }
                    webSocketService.notifyOthelloPlayers(game.getGameId(), othelloGameDto);
                    return othelloGameDto;
                } else throw new IllegalArgumentException("Move is not valid");
            } else throw new IllegalArgumentException("Square is not empty");
        } else throw new IllegalArgumentException("Game id does not exist");
    }

    public ColorDto colorDto(String gameId) {
        OthelloGame game = activeGames.get(gameId);
        if (game != null) {
            var colorList = game.getAvailableColors();
            return othelloGameMapper.colorToColorDto(colorList);
        }
        throw new IllegalArgumentException("Game id does not exist");
    }

    public void killGame(String gameId){
        activeGames.remove(gameId);
    }

}