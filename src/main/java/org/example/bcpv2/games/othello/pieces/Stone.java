package org.example.bcpv2.games.othello.pieces;

import org.example.bcpv2.boards.ChessBoard;
import org.example.bcpv2.boards.OthelloBoard;
import org.example.bcpv2.boards.Square;
import org.example.bcpv2.games.chess.eunums.Color;
import org.example.bcpv2.games.chess.eunums.Pieces;
import org.example.bcpv2.games.chess.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class Stone extends Piece<OthelloBoard> {

    public Stone(Color color, Pieces pieceType, Square square, OthelloBoard board) {
        super(color, pieceType, square, board);
    }

    @Override
    public List<Square> getPossibleMoves(ChessBoard chessBoard) {
        List<Square> moves = new ArrayList<>();
        int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.getPiece(row, col).isEmpty()) {
                    if (isValidMove(row, col, directions)) {
                        moves.add(new Square(row, col));
                    }
                }
            }
        }
        return moves;
    }

    private boolean isValidMove(int row, int col, int[][] directions) {
        for (int[] dir : directions) {
            int r = row + dir[0], c = col + dir[1];
            boolean foundOpponent = false;

            while (board.isInBounds(r, c) && board.getPiece(r, c).isPresent()) {
                if (board.getPiece(r, c).get().getColor() == this.getColor()) {
                    if (foundOpponent) return true;
                    else break;
                } else {
                    foundOpponent = true;
                }
                r += dir[0];
                c += dir[1];
            }
        }
        return false;
    }

    @Override
    public List<Square> getPossibleTheoreticalMove(ChessBoard tempBoard) {
        return null;
    }

    @Override
    public Stone clone() {
        return (Stone) super.clone();
    }
}
