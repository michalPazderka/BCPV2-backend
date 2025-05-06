package org.example.bcpv2.games.chess.pieces;

import org.example.bcpv2.boards.ChessBoard;
import org.example.bcpv2.boards.Square;
import org.example.bcpv2.games.chess.eunums.Color;
import org.example.bcpv2.games.chess.eunums.Pieces;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece<ChessBoard>{

    public King(Color color, Pieces pieceType, Square square, ChessBoard board) {
        super(color, pieceType, square, board);
    }

    @Override
    public List<Square> getPossibleMoves(ChessBoard chessBoard) {
        var moves = new ArrayList<Square>();
        int[][] possible = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1,1}, {1,-1}, {-1,1}, {-1,-1}};
        for(int[] possible1D: possible){
            int x = getSquare().getX() + possible1D[0];
            int y = getSquare().getY() + possible1D[1];
            if(board.isInBounds(x,y) && (board.isEmpty(x,y)
                    || board.isEnemy(x,y,this.getColor()))
                    && board.changePosition(x,y, getSquare().getX(), getSquare().getY())){
                moves.add(new Square(x,y));
            }
        }
        return moves;
    }

    @Override
    public List<Square> getPossibleTheoreticalMove(ChessBoard tempBoard) {
        var moves = new ArrayList<Square>();
        int[][] possible = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1,1}, {1,-1}, {-1,1}, {-1,-1}};
        for(int[] possible1D: possible){
            int x = getSquare().getX() + possible1D[0];
            int y = getSquare().getY() + possible1D[1];
            if(board.isInBounds(x,y) && (board.isEmpty(x,y) || board.isEnemy(x,y,this.getColor()))){
                moves.add(new Square(x,y));
            }
        }
        return moves;
    }

    @Override
    public King clone() {
        return (King) super.clone();
    }
}
