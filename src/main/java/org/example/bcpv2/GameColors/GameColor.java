package org.example.bcpv2.GameColors;

import lombok.Getter;
import lombok.Setter;
import org.example.bcpv2.games.chess.eunums.Color;

@Getter
public class GameColor {
    private final Color color;
    @Setter
    private boolean isTaken;

    public GameColor(Color color) {
        this.color = color;
        this.isTaken = false;
    }

}
