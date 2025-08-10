package org.example.bcpv2.games.chess.rules;

import org.example.bcpv2.boards.ChessBoard;
import org.example.bcpv2.boards.Square;
import org.example.bcpv2.games.chess.eunums.Color;
import org.example.bcpv2.games.chess.pieces.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChessRules {

    /*private ChessBoard chessBoard;

    public ChessRules(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }*/
    private Color isPlaying;

    public ChessRules(Color isPlaying) {
        this.isPlaying = isPlaying;
    }

    public void setIsPlaying(Color isPlaying) {
        this.isPlaying = isPlaying;
    }

    public Color getIsPlaying() {
        return isPlaying;
    }

    public boolean isTheoreticalCheck(Color color, ChessBoard tempBoard) {
        Square king = tempBoard.getKingSquare(color);
        List<Square> theoreticalMoves = getEveryTheoreticalMove(tempBoard.changeColor(this.isPlaying), tempBoard);
        return theoreticalMoves.stream()
                .anyMatch(square -> king.getRow() == square.getRow() && king.getCol() == square.getCol());
    }

    public List<Square> getEveryTheoreticalMove(Color color, ChessBoard tempBoard) {
        var moves = new ArrayList<Square>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Optional<Piece> piece = tempBoard.getPiece(i, j);
                if (piece.isPresent()) {
                    if (piece.get().getColor() == color) {
                        List<Square> theoreticalMoves = piece.get().getPossibleTheoreticalMove(tempBoard);
                        if (!theoreticalMoves.isEmpty()) {
                            for (Square theoreticalMove : theoreticalMoves) {
                                if (moves.stream().noneMatch(square -> theoreticalMove.getRow() == square.getRow() && square.getCol() == theoreticalMove.getCol())) {
                                    moves.add(new Square(theoreticalMove.getRow(), theoreticalMove.getCol()));
                                }
                            }
                        }
                    }
                }

            }
        }
        return moves;
    }

    public List<Square> getEveryMove(Color color, ChessBoard board) {
        var moves = new ArrayList<Square>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Optional<Piece> piece = board.getPiece(i, j);
                if (piece.isPresent()) {
                    if (piece.get().getColor() == color) {
                        List<Square> theoreticalMoves = piece.get().getPossibleMoves(board);
                        if (!theoreticalMoves.isEmpty()) {
                            for (Square theoreticalMove : theoreticalMoves) {
                                if (moves.stream().noneMatch(square -> theoreticalMove.getRow() == square.getRow() && square.getCol() == theoreticalMove.getCol())) {
                                    moves.add(new Square(theoreticalMove.getRow(), theoreticalMove.getCol()));
                                }
                            }
                        }
                    }
                }

            }
        }
        return moves;
    }

    public boolean isMate(ChessBoard board){
        List<Square> possibleMoves = getEveryMove(this.isPlaying, board);
        return possibleMoves.isEmpty() && this.isCheck(board);
    }

    public boolean isStaleMate(ChessBoard board) {
        List<Square> possibleMoves = getEveryMove(this.isPlaying, board);
        return possibleMoves.isEmpty() && !this.isCheck(board);
    }

    public boolean isCheck(ChessBoard board){
        Square king = board.getKingSquare(this.isPlaying);
        List<Square> possibleMoves = this.getEveryMove(switchColors(this.isPlaying), board);
        return possibleMoves.stream().anyMatch(square -> king.getRow() == square.getRow() && king.getCol() == square.getCol());
    }

    public boolean isSquareUnderAttack(Square square, ChessBoard board, Color color){
        var getEveryTheoreticalMove = this.getEveryTheoreticalMove(color, board);
        return getEveryTheoreticalMove.stream().anyMatch(square1 -> square1.getRow() == square.getRow() && square1.getCol() == square.getCol());
    }

    public Color switchColors(Color color){
        return color.equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

}
