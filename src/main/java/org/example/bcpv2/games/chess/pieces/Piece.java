package org.example.bcpv2.games.chess.pieces;

import org.example.bcpv2.boards.AbsBoard;
import org.example.bcpv2.boards.ChessBoard;
import org.example.bcpv2.boards.Square;
import org.example.bcpv2.games.chess.eunums.Color;
import org.example.bcpv2.games.chess.eunums.Pieces;
import lombok.Getter;
import lombok.Setter;
import org.example.bcpv2.rules.AbsRules;

import java.util.List;

@Getter
@Setter
public abstract class Piece<T extends AbsBoard> implements Cloneable{
    private Color color;
    private Pieces pieceType;
    private Square square;
    protected T board;


    public Piece(Color color, Pieces pieceType, Square square, T board) {
        this.color = color;
        this.pieceType = pieceType;
        this.square = square;
        this.board = board;
    }

    public abstract List<Square> getPossibleMoves(ChessBoard chessBoard);

    public abstract List<Square> getPossibleTheoreticalMove(ChessBoard tempBoard);

    @Override
    public Piece clone() {
        try {
            return (Piece) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
