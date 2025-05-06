package org.example.bcpv2.Service;

import lombok.AllArgsConstructor;
import org.example.bcpv2.boards.Square;
import org.example.bcpv2.dto.ChessGameDto;
import org.example.bcpv2.dto.ColorDto;
import org.example.bcpv2.games.chess.ChessGame;
import org.example.bcpv2.games.chess.eunums.Color;
import org.example.bcpv2.games.chess.pieces.Piece;
import org.example.bcpv2.mapper.ChessGameMapperS;
import org.example.bcpv2.webSocket.WebSocketService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class ChessService {
    private final Map<String, ChessGame> activeGames = new ConcurrentHashMap<>();
    private final ChessGameMapperS chessGameMapper;
    private final WebSocketService webSocketService;

    public ChessGameDto createGame(String color) {
        //String gameId = UUID.randomUUID().toString();
        String gameId = "1";
        ChessGame chessGame = new ChessGame(gameId);
        switch (color){
            case "WHITE":
                chessGame.setColorWhite();
                break;
            case "BLACK":
                chessGame.setColorBlack();
                break;
            default:
                throw new IllegalArgumentException("Color does not exist");
        }
        chessGame.getBoard().initialize();
        activeGames.put(gameId, chessGame);
        return chessGameMapper.chessGameToChessGameDto(chessGame);
    }

    public ChessGameDto joinGame(String id, String color){
        var game = activeGames.get(id);
        if (game == null) throw new IllegalArgumentException("Game does not exist");
        switch (color){
            case "WHITE":
                game.setColorWhite();
                break;
            case "BLACK":
                game.setColorBlack();
                break;
            default:
                throw new IllegalArgumentException("Color does not exist");
        }
        return chessGameMapper.chessGameToChessGameDto(game);
    }

    public ChessGameDto getGame(String gameId) {
        return chessGameMapper.chessGameToChessGameDto(activeGames.get(gameId));
    }

    public ChessGameDto makeMove(String gameId, String move) {
        ChessGame game = activeGames.get(gameId);
        if (game != null) {
            String[] moves = move.split("");
            Piece piece;
            if (game.getBoard().getPiece(Integer.parseInt(moves[0]), Integer.parseInt(moves[1])).isPresent()) {
                piece = game.getBoard().getPiece(Integer.parseInt(moves[0]), Integer.parseInt(moves[1])).get();
            } else {
                throw new IllegalArgumentException("Piece does not exist");
            }
            if(game.getIsPlaying() != piece.getColor()){
                throw new IllegalArgumentException("Opponent is playing");
            }
            List<Square> squareList = piece.getPossibleMoves(game.getBoard());
            for (Square square : squareList) {
                if (square.getX() == Integer.parseInt(moves[2]) && square.getY() == Integer.parseInt(moves[3])) {
                    game.getBoard().setPiecePostion(square.getX(), square.getY(), piece, game.getBoard());
                    game.changeColor();
                    game.getBoard().getChessRules().setIsPlaying((game.getBoard().getChessRules().getIsPlaying().equals(Color.WHITE)) ? Color.BLACK : Color.WHITE);
                    var chessGameDto = chessGameMapper.chessGameToChessGameDto(game);
                    if(game.getBoard().getChessRules().isMate(game.getBoard())){
                        chessGameDto.setWinner(game.getIsPlaying().equals(Color.WHITE) ? Color.BLACK.toString() : Color.WHITE.toString());
                    } else if(game.getBoard().getChessRules().isStaleMate(game.getBoard())){
                        chessGameDto.setWinner("DRAW");
                    }
                    webSocketService.notifyPlayers(game.getGameId(), chessGameDto);
                    return chessGameDto;
                }
            }
            throw new IllegalArgumentException("Moves is not possible");
        }
        throw new IllegalArgumentException("Game id does not exist");
    }

    public ColorDto colorDto(String gameId){
        ChessGame game = activeGames.get(gameId);
        if(game != null) {
            var colorList = game.getAvailableColors();
            return chessGameMapper.colorToColorDto(colorList);
        }
        throw new IllegalArgumentException("Game id does not exist");
    }
}
