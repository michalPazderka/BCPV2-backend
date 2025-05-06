package org.example.bcpv2.GameColors;

import lombok.Getter;
import org.example.bcpv2.games.chess.eunums.Color;

public class GameColor {
    @Getter
    private final Color color;
    private boolean isTaken;

    public GameColor(Color color) {
        this.color = color;
        this.isTaken = false;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }
}
