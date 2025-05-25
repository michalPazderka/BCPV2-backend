package org.example.bcpv2.games.othello.rules;

import lombok.Setter;
import org.example.bcpv2.boards.OthelloBoard;
import org.example.bcpv2.games.chess.eunums.Color;

public class OthelloRules {
    @Setter
    private Color isPlaying;

    public OthelloRules(Color isPlaying) {
        this.isPlaying = isPlaying;
    }

    public boolean isGameOver(OthelloBoard board) {
        Color color = this.isPlaying;
        var possibleMoves = board.getPossibleMoves(color);
        color = (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
        var possibleMoves2 = board.getPossibleMoves(color);
        System.out.println("possible moves 1: " + possibleMoves.isEmpty());
        System.out.println("possible moves 2: " + possibleMoves2.isEmpty());
        return possibleMoves.isEmpty() && possibleMoves2.isEmpty();
    }

    public Color getWinner(OthelloBoard board){
        Color color = this.isPlaying;
        var count1 = board.numberOfStones(color);
        Color color2 = (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
        var count2 = board.numberOfStones(color2);
        return count1 > count2 ? color : color2;
    }
}
