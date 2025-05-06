package org.example.bcpv2.boards;

import lombok.Getter;
import lombok.Setter;
import org.example.bcpv2.games.chess.eunums.Color;
import org.example.bcpv2.games.chess.eunums.Pieces;
import org.example.bcpv2.games.chess.pieces.*;
import org.example.bcpv2.games.chess.rules.ChessRules;

import java.util.Optional;


@Getter
@Setter
public class ChessBoard extends AbsBoard {
    Square[][] board;
    ChessRules chessRules;

    public ChessBoard(ChessRules chessRules) {
        this.chessRules = chessRules;
        this.board = new Square[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.board[i][j] = new Square(i, j);
            }
        }
    }

    public ChessBoard(ChessBoard other, ChessRules chessRules) {
        this.chessRules = chessRules;
        this.board = new Square[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.board[i][j] = new Square(i,j);
                if (other.board[i][j].getPiece().isPresent()) {
                    this.board[i][j].setPiece(other.board[i][j].getPiece().get().clone());
                }
            }
        }
    }

    public void initialize(){
        for(int i = 0; i < 8; i++){
            this.board[1][i].setPiece(new Pawn(Color.WHITE, Pieces.PAWN, new Square(1,i), this));
            this.board[6][i].setPiece(new Pawn(Color.BLACK, Pieces.PAWN, new Square(6,i), this));
        }
        this.board[0][0].setPiece(new Rook(Color.WHITE, Pieces.ROOK, new Square(0,0), this));
        this.board[0][7].setPiece(new Rook(Color.WHITE, Pieces.ROOK, new Square(0,7), this));
        this.board[7][0].setPiece(new Rook(Color.BLACK, Pieces.ROOK, new Square(7,0), this));
        this.board[7][7].setPiece(new Rook(Color.BLACK, Pieces.ROOK, new Square(7,7), this));

        this.board[0][1].setPiece(new Knight(Color.WHITE, Pieces.KNIGHT, new Square(0,1), this));
        this.board[0][6].setPiece(new Knight(Color.WHITE, Pieces.KNIGHT, new Square(0,6), this));
        this.board[7][1].setPiece(new Knight(Color.BLACK, Pieces.KNIGHT, new Square(7,1), this));
        this.board[7][6].setPiece(new Knight(Color.BLACK, Pieces.KNIGHT, new Square(7,6), this));

        this.board[0][2].setPiece(new Bishop(Color.WHITE, Pieces.BISHOP, new Square(0,2), this));
        this.board[0][5].setPiece(new Bishop(Color.WHITE, Pieces.BISHOP, new Square(0,5), this));
        this.board[7][2].setPiece(new Bishop(Color.BLACK, Pieces.BISHOP, new Square(7,2), this));
        this.board[7][5].setPiece(new Bishop(Color.BLACK, Pieces.BISHOP, new Square(7,5), this));

        this.board[0][3].setPiece(new Queen(Color.WHITE, Pieces.QUEEN, new Square(0,3), this));
        this.board[7][3].setPiece(new Queen(Color.BLACK, Pieces.QUEEN, new Square(7,3), this));

        this.board[0][4].setPiece(new King(Color.WHITE, Pieces.KING, new Square(0,4), this));
        this.board[7][4].setPiece(new King(Color.BLACK, Pieces.KING, new Square(7,4), this));
    }

    //TODO: zatím nechám na X,Y do budoucna pro lehčí práci bude možný posílat Square
    public boolean isInBounds(int x, int y) {
        return x <= 7 && x >= 0 && y <= 7 && y >= 0;
    }

    public boolean isEnemy(int x, int y, Color color) {
        return this.board[x][y].getPiece()
                .map(piece -> piece.getColor() != color)
                .orElse(false);
    }

    public boolean isAllay(int x, int y, Color color) {
        return this.board[x][y].getPiece()
                .map(piece -> piece.getColor() == color)
                .orElse(false);
    }

    public boolean isEmpty(int x, int y) {
        return this.board[x][y].isEmpty();
    }

    public Square getKing(Color color) {
        for (Square[] row : this.board) {
            for (Square square : row) {
                Piece<ChessBoard> piece = square.getPiece().orElse(null);
                if (piece instanceof King && color == piece.getColor()) {
                    return square;
                }
            }
        }
        return null;
    }

    public Optional<Piece> getPiece(int x, int y) {
        return this.board[x][y].getPiece();
    }

    public void setPiecePostion(int x, int y, Piece<ChessBoard> piece, ChessBoard tempBoard) {
        var arr = piece.getSquare();
        if (tempBoard.isEnemy(x, y, piece.getColor())) {
            tempBoard.destroyPiece(x, y);
        }
        tempBoard.board[arr.getX()][arr.getY()].setPiece(null);
        tempBoard.board[x][y].setPiece(piece);
        piece.setSquare(new Square(x, y));
    }

    public void destroyPiece(int x, int y) {
        this.board[x][y].setPiece(null);
    }

    public Color changeColor(Color color) {
        if (color == Color.WHITE) return Color.BLACK;
        return Color.WHITE;
    }


    public boolean changePosition(int ep1, int ep2, int e1, int e2) {
        ChessBoard tempBoard = new ChessBoard(this, this.chessRules);
        var enemyPiece = tempBoard.getPiece(ep1, ep2);
        var piece = tempBoard.getPiece(e1, e2);
        if (enemyPiece.isPresent() && piece.isPresent()) {
            Square enemyPiecePosition = enemyPiece.get().getSquare();
            tempBoard.setPiecePostion(enemyPiecePosition.getX(), enemyPiecePosition.getY(), piece.get(), tempBoard);
            Square king = tempBoard.getKing(piece.get().getColor());
            return !chessRules.isTheoreticalCheck(king.getPiece().get().getColor(), tempBoard);
        } else if (piece.isPresent()) {
            tempBoard.setPiecePostion(ep1, ep2, piece.get(), tempBoard);
            Square king = tempBoard.getKing(piece.get().getColor());
            return !chessRules.isTheoreticalCheck(king.piece.get().getColor(), tempBoard);
        }
        return true;
    }
}
