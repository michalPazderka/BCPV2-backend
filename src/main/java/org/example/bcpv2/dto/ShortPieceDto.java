package org.example.bcpv2.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.bcpv2.games.chess.eunums.Color;
import org.example.bcpv2.games.chess.eunums.Pieces;

@Data
@Getter
@Setter
public class ShortPieceDto {
    Pieces pieceType;
    Color color;
}
