package org.example.bcpv2.games.chess.pieces;

import org.example.bcpv2.boards.ChessBoard;
import org.example.bcpv2.boards.Square;
import org.example.bcpv2.games.chess.eunums.Color;
import org.example.bcpv2.games.chess.eunums.Pieces;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece<ChessBoard>{
    public Bishop(Color color, Pieces pieceType, Square square, ChessBoard board) {
        super(color, pieceType, square, board);
    }

    @Override
    public List<Square> getPossibleMoves(ChessBoard chessBoard) {
        var moves = new ArrayList<Square>();
        int[][] possible =  new int[][]{{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for(int[] possible1D : possible){
            int x = this.getSquare().getRow() + possible1D[0];
            int y = this.getSquare().getCol() + possible1D[1];
            while(board.isInBounds(x,y)){
                if(board.isEmpty(x,y)){
                    if(board.changePosition(x,y, this.getSquare().getRow(), this.getSquare().getCol())) {
                        moves.add(new Square(x, y));
                    }
                } else if(board.isAllay(x,y, getColor())){
                    break;
                } else if (board.isEnemy(x,y, this.getColor())) {
                    if(board.changePosition(x,y, this.getSquare().getRow(), this.getSquare().getCol())) {
                        moves.add(new Square(x, y));
                    }
                    break;
                }
            x+=possible1D[0];
            y+=possible1D[1];
            }
        }
        return moves;
    }


    @Override
    public List<Square> getPossibleTheoreticalMove(ChessBoard tempBoard) {
        var moves = new ArrayList<Square>();
        int[][] possible =  new int[][]{{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for(int[] possible1D : possible){
            int x = this.getSquare().getRow() + possible1D[0];
            int y = this.getSquare().getCol() + possible1D[1];
            while(board.isInBounds(x,y)){
                if(board.isEmpty(x,y)){
                    moves.add(new Square(x, y));
                } else if(board.isAllay(x,y, getColor())){
                    break;
                } else if (board.isEnemy(x,y, this.getColor())) {
                    moves.add(new Square(x, y));
                    break;
                }
                x+=possible1D[0];
                y+=possible1D[1];
            }
        }
        return moves;
    }

    @Override
    public Bishop clone() {
        return (Bishop) super.clone();
    }
}
