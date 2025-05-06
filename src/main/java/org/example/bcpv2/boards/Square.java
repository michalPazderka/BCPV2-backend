package org.example.bcpv2.boards;


import org.example.bcpv2.games.chess.pieces.Piece;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
public class Square {
    int row;
    int col;
    Optional<Piece> piece;

    public Square(int x, int y) {
        this.row = x;
        this.col = y;
        this.piece = Optional.empty();
    }

    public void setPiece(Piece piece) {
        this.piece = Optional.ofNullable(piece);
    }

    public boolean isEmpty() {
        return piece.isEmpty();
    }
}
