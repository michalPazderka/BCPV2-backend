package org.example.bcpv2.games.chess.pieces;

import org.example.bcpv2.boards.ChessBoard;
import org.example.bcpv2.boards.Square;
import org.example.bcpv2.games.chess.eunums.Color;
import org.example.bcpv2.games.chess.eunums.Pieces;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece<ChessBoard> {
    public Knight(Color color, Pieces pieceType, Square square, ChessBoard board) {
        super(color, pieceType, square, board);
    }

    @Override
    public List<Square> getPossibleMoves(ChessBoard chessBoard) {
        var moves = new ArrayList<Square>();
        int[][] possible = new int[][]{{1, 2}, {2, 1}, {1, -2}, {2, -1}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}};
        for (int[] possible1D : possible) {
            int x = this.getSquare().getRow() + possible1D[0];
            int y = this.getSquare().getCol() + possible1D[1];
            if (board.isInBounds(x, y) && (board.isEmpty(x, y) || board.isEnemy(x, y,getColor()))) {
                if(board.changePosition(x,y,this.getSquare().getRow(),this.getSquare().getCol())){
                    moves.add(new Square(x, y));
                }
            }
        }
        return moves;
    }

    @Override
    public List<Square> getPossibleTheoreticalMove(ChessBoard tempBoard) {
        var moves = new ArrayList<Square>();
        int[][] possible = new int[][]{{1, 2}, {2, 1}, {1, -2}, {2, -1}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}};
        for (int[] possible1D : possible) {
            int x = this.getSquare().getRow() + possible1D[0];
            int y = this.getSquare().getCol() + possible1D[1];
            if (board.isInBounds(x, y) && (board.isEmpty(x, y) || board.isEnemy(x, y,getColor()))) {
                    moves.add(new Square(x, y));
            }
        }
        return moves;
    }

    @Override
    public Knight clone() {
        return (Knight) super.clone();
    }
}
