package org.example.bcpv2.games.chess;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.bcpv2.boards.ChessBoard;
import org.example.bcpv2.games.chess.pieces.Piece;

import java.util.Optional;

@AllArgsConstructor
@Getter
@Setter
public class Move {
    String from;
    String to;
    Piece<ChessBoard> piece;
    Optional<Piece> capturedPiece;
}
