package org.example.bcpv2.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.bcpv2.games.chess.eunums.Color;
import org.example.bcpv2.games.chess.eunums.Pieces;
import org.example.bcpv2.games.chess.pieces.Piece;

import java.util.List;

@Data
@Setter
@Getter
public class ChessGameDto {
    String gameId;
    ShortPieceDto[][] chessBoard = new ShortPieceDto[8][8];
    int isPlaying;
    List<Color> restOfColors;
    String winner;

    public void setBoardValue(int row, int col, Piece piece) {
        var shortPieceDto = new ShortPieceDto();
        shortPieceDto.setPieceType(piece.getPieceType());
        shortPieceDto.setColor(piece.getColor());
        this.chessBoard[row][col] = shortPieceDto;
    }
}
