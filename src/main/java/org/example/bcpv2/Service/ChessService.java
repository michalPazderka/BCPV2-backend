package org.example.bcpv2.Service;

import lombok.AllArgsConstructor;
import org.example.bcpv2.boards.Square;
import org.example.bcpv2.dto.ChessGameDto;
import org.example.bcpv2.dto.ColorDto;
import org.example.bcpv2.games.chess.ChessGame;
import org.example.bcpv2.games.chess.Move;
import org.example.bcpv2.games.chess.eunums.Color;
import org.example.bcpv2.games.chess.pieces.King;
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
public class ChessService implements GameService<ChessGame, ChessGameDto> {
    private final Map<String, ChessGame> activeGames = new ConcurrentHashMap<>();
    private final ChessGameMapperS chessGameMapper;
    private final WebSocketService webSocketService;

    public List<ChessGameDto> getGames() {
        return activeGames.values().stream().map(chessGameMapper::chessGameToChessGameDto).toList();
    }

    public ColorDto getColors(){
        return chessGameMapper.colorToColorDto(List.of(Color.WHITE, Color.BLACK));
    }

    public ChessGameDto createGame(String color) {
        String gameId = UUID.randomUUID().toString();
        ChessGame chessGame = new ChessGame(gameId);
        switch (color) {
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

    public ChessGameDto joinGame(String id, String color) {
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
        return chessGameMapper.chessGameToChessGameDto(game);
    }

    public ChessGameDto getGameDto(String gameId) {
        if (activeGames.get(gameId) != null) {
            return chessGameMapper.chessGameToChessGameDto(activeGames.get(gameId));
        }
        return null;
    }

    public ChessGame getGame(String gameId) {
        return activeGames.get(gameId);
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
            if (game.getIsPlaying() != piece.getColor()) {
                throw new IllegalArgumentException("Opponent is playing");
            }
            List<Square> squareList = piece.getPossibleMoves(game.getBoard());
            for (Square square : squareList) {
                if (square.getRow() == Integer.parseInt(moves[2]) && square.getCol() == Integer.parseInt(moves[3])) {
                    if (move.length() > 4) {
                        King king = game.getBoard().getKing(game.getIsPlaying());
                        game.getBoard().castleMove(king, Integer.parseInt(moves[4]));
                    } else {
                        game.getBoard().setPiecePostion(square.getRow(), square.getCol(), piece, game.getBoard());
                    }
                    game.addMove(new Move(moves[0] + moves[1], moves[2] + moves[3], piece, game.getBoard().getPiece(Integer.parseInt(moves[2]), Integer.parseInt(moves[3]))));
                    game.getBoard().checkIfMovePromote(square.getRow(), square.getCol(), piece);
                    game.changeColor();
                    game.getBoard().getChessRules().setIsPlaying((game.getBoard().getChessRules().getIsPlaying().equals(Color.WHITE)) ? Color.BLACK : Color.WHITE);
                    var chessGameDto = chessGameMapper.chessGameToChessGameDto(game);
                    if (game.getBoard().getChessRules().isMate(game.getBoard())) {
                        chessGameDto.setWinner(game.getIsPlaying().equals(Color.WHITE) ? Color.BLACK.toString() : Color.WHITE.toString());
                        killGame(gameId);
                    } else if (game.getBoard().getChessRules().isStaleMate(game.getBoard())) {
                        chessGameDto.setWinner("DRAW");
                        killGame(gameId);
                    }
                    webSocketService.notifyChessPlayers(game.getGameId(), chessGameDto);
                    return chessGameDto;
                }
            }
            throw new IllegalArgumentException("Moves is not possible");
        }
        throw new IllegalArgumentException("Game id does not exist");
    }

    public ColorDto colorDto(String gameId) {
        ChessGame game = activeGames.get(gameId);
        if (game != null) {
            var colorList = game.getAvailableColors();
            return chessGameMapper.colorToColorDto(colorList);
        }
        throw new IllegalArgumentException("Game id does not exist");
    }

    public void killGame(String gameId) {
        activeGames.remove(gameId);
    }

}
