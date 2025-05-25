package org.example.bcpv2.boards;

import lombok.Getter;
import lombok.Setter;
import org.example.bcpv2.games.chess.eunums.Color;
import org.example.bcpv2.games.chess.eunums.Pieces;
import org.example.bcpv2.games.chess.pieces.Piece;
import org.example.bcpv2.games.othello.pieces.Stone;
import org.example.bcpv2.games.othello.rules.OthelloRules;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Getter
@Setter
public class OthelloBoard extends AbsBoard {
    Square[][] board;
    OthelloRules othelloRules;

    public OthelloBoard(OthelloRules othelloRules) {
        this.othelloRules = othelloRules;
        this.board = new Square[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.board[i][j] = new Square(i, j);
            }
        }
    }

    public OthelloBoard(OthelloBoard other, OthelloRules othelloRules) {
        this.othelloRules = othelloRules;
        this.board = new Square[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.board[i][j] = new Square(i, j);
                if (other.board[i][j].getPiece().isPresent()) {
                    this.board[i][j].setPiece(other.board[i][j].getPiece().get().clone());
                }
            }
        }
    }

    public void initialize() {
        this.board[3][3].setPiece(new Stone(Color.BLACK, Pieces.STONE, new Square(3, 3), this));
        this.board[3][4].setPiece(new Stone(Color.WHITE, Pieces.STONE, new Square(3, 4), this));
        this.board[4][3].setPiece(new Stone(Color.WHITE, Pieces.STONE, new Square(4, 3), this));
        this.board[4][4].setPiece(new Stone(Color.BLACK, Pieces.STONE, new Square(4, 4), this));
    }

    public int getSize() {
        return board.length;
    }

    public Optional<Piece> getPiece(int x, int y) {
        return this.board[x][y].getPiece();
    }

    public List<Square> getPossibleMoves(Color color) {
        List<Square> squares = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                List<Square> possibleMoves = this.getFlipablePieces(i, j, color);
                if (!possibleMoves.isEmpty()) {
                    squares.add(new Square(i, j));
                }
            }
        }
        /*squares.forEach(square -> {
            System.out.println("souřadnice : " + square.getRow() + square.getCol());
        });*/
        return squares;
    }

    public boolean validMove(int r, int c, Color color) {
        var possibleMoves = getPossibleMoves(color);
        for(Square possibleMove: possibleMoves){
            if(possibleMove.getRow() == r && possibleMove.getCol() == c){
                return true;
            }
        }
        return false;
    }

    public void addPiece(int x, int y, Color color) {
        this.board[x][y].setPiece(new Stone(color, Pieces.STONE, new Square(x, y), this));
        List<Square> squares = getFlipablePieces(x, y, color);
        squares.forEach(square -> {
            System.out.println("souřadnice : " + square.getRow() + square.getCol());
        });
        flipPieces(squares, color);
    }

    public List<Square> getFlipablePieces(int row, int col, Color color) {
        List<Square> flippablePieces = new ArrayList<>();
        if (!isEmpty(row, col)) {
            return flippablePieces;
        }

        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1}, {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        for (int[] dir : directions) {
            int r = row + dir[0], c = col + dir[1];
            List<Square> tempFlip = new ArrayList<>();

            while (isInBounds(r, c) && board[r][c].getPiece().isPresent() && isEnemy(r, c, color)) {
                tempFlip.add(new Square(r, c));
                r += dir[0];
                c += dir[1];
            }

            if (isInBounds(r, c) && isAllay(r, c, color)) {
                flippablePieces.addAll(tempFlip);
            }
        }
        return flippablePieces;
    }

    public void flipPieces(List<Square> squares, Color color) {
        for (Square square : squares) {
            board[square.getRow()][square.getCol()].getPiece().get().setColor(color);
        }
    }


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

    public int numberOfStones(Color color){
        int count = 0;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(this.board[i][j].getPiece().isPresent() && this.board[i][j].getPiece().get().getColor() == color){
                    count++;
                }
            }
        }
        return count;
    }
}
