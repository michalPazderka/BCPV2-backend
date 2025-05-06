package org.example.bcpv2.games.chess.pieces;

import org.example.bcpv2.boards.ChessBoard;
import org.example.bcpv2.boards.Square;
import org.example.bcpv2.games.chess.eunums.Color;
import org.example.bcpv2.games.chess.eunums.Pieces;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Pawn extends Piece<ChessBoard> {
    int direction;

    public Pawn(Color color, Pieces pieceType, Square square, ChessBoard board) {
        super(color, pieceType, square, board);
        if (color == Color.BLACK) {
            this.direction = -1;
        } else {
            this.direction = 1;
        }
    }

    @Override
    public List<Square> getPossibleMoves(ChessBoard chessBoard) {
        var moves = new ArrayList<Square>();
        int x = getSquare().getX();
        int y = getSquare().getY();
        if (board.isInBounds(x + direction, y) && board.isEmpty(x + direction, y)
                && board.changePosition(x + direction, y, this.getSquare().getX(), this.getSquare().getY())) {
            moves.add(new Square(x + direction, y));
        }
        if (board.isInBounds(x + direction, y + 1) && board.isEnemy(x + direction, y + 1, getColor())
                && board.changePosition(x + direction, y, this.getSquare().getX(), this.getSquare().getY())) {
            moves.add(new Square(x + direction, y + 1));
        }
        if (board.isInBounds(x + direction, y - 1) && board.isEnemy(x + direction, y - 1, getColor())
                && board.changePosition(x + direction, y, this.getSquare().getX(), this.getSquare().getY())) {
            moves.add(new Square(x + direction, y - 1));
        }
        if (((this.getColor() == Color.WHITE && x == 1) || (this.getColor() == Color.BLACK && x == 6))
                && board.isEmpty(x + direction, y)
                && board.isEmpty(x + 2 * direction, y)
                && board.changePosition(x + direction, y, this.getSquare().getX(), this.getSquare().getY())) {
            moves.add(new Square(x + 2 * direction, y));
        }
        return moves;
    }

    @Override
    public List<Square> getPossibleTheoreticalMove(ChessBoard tempBoard) {
        var moves = new ArrayList<Square>();
        int x = getSquare().getX();
        int y = getSquare().getY();
        if (board.isInBounds(x + direction, y) && board.isEmpty(x + direction, y)) {
            moves.add(new Square(x + direction, y));
        }
        if (board.isInBounds(x + direction, y + 1) && board.isEnemy(x + direction, y + 1, getColor())) {
            moves.add(new Square(x + direction, y + 1));
        }
        if (board.isInBounds(x + direction, y - 1) && board.isEnemy(x + direction, y - 1, getColor())) {
            moves.add(new Square(x + direction, y - 1));
        }
        if (((this.getColor() == Color.WHITE && x == 1) || (this.getColor() == Color.BLACK && x == 6))
                && board.isEmpty(x + direction, y)
                && board.isEmpty(x + 2 * direction, y)) {
            moves.add(new Square(x + 2 * direction, y));
        }
        return moves;
    }

    @Override
    public Pawn clone() {
        return (Pawn) super.clone();
    }
}
