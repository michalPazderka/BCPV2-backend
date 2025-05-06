package org.example.bcpv2.games.chess;

import org.example.bcpv2.boards.ChessBoard;
import org.example.bcpv2.boards.Square;
import org.example.bcpv2.games.chess.eunums.Color;
import org.example.bcpv2.games.chess.pieces.Piece;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//TODO: celá třída projde ještě refaktorem, aktuálně píšu jen aby fungovala
@Getter
public class Game {
    Player player1;
    Player player2;
    @Setter
    Color isPlaying;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    /*public boolean isTheoreticalCheck(Color color, ChessBoard tempBoard){
        Square king = tempBoard.getKing(color);
        List<Square> theoreticalMoves = getEveryTheoreticalMove(tempBoard.changeColor(this.isPlaying),tempBoard);
            return theoreticalMoves.stream()
                    .anyMatch(square -> king.getX() == square.getX() && king.getY() == square.getY());
    }

    public List<Square> getEveryTheoreticalMove(Color color, ChessBoard tempBoard){
        var moves = new ArrayList<Square>();
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                Optional<Piece> piece = tempBoard.getPiece(i,j);
                if(piece.isPresent()){
                    if(piece.get().getColor() == color){
                        List<Square> theoreticalMoves = piece.get().getPossibleTheoreticalMove(tempBoard);
                        if(!theoreticalMoves.isEmpty()){
                            for(Square theoreticalMove : theoreticalMoves) {
                                if (moves.stream().noneMatch(square -> theoreticalMove.getX() == square.getX() && square.getY() == theoreticalMove.getY())){
                                    moves.add(new Square(theoreticalMove.getX(), theoreticalMove.getY()));
                                }
                            }
                        }
                    }
                }

            }
        }
        return moves;
    }*/

}
