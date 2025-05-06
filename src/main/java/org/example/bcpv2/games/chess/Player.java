package org.example.bcpv2.games.chess;

import org.example.bcpv2.games.chess.eunums.Color;
import lombok.Getter;

@Getter
public class Player {
    Color color;

    public Player(Color color) {
        this.color = color;
    }

}
