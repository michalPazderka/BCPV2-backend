package org.example.bcpv2.boards;

import org.example.bcpv2.games.chess.eunums.Color;
import org.example.bcpv2.rules.AbsRules;

public abstract class AbsBoard {
    Square[][] board;
    AbsRules rules;

    /*public boolean isInBounds(int x, int y) {
        return x <= 7 && x >= 0 && y <= 7 && y >= 0;
    }

    public boolean isEmpty(int x, int y) {
        return this.board[x][y].isEmpty();
    }

    public boolean isEnemy(int x, int y, Color color) {
        return this.board[x][y].getPiece()
                .map(piece -> piece.getColor() != color)
                .orElse(false);
    }*/


}
