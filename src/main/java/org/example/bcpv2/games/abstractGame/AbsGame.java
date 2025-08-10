package org.example.bcpv2.games.abstractGame;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.bcpv2.GameColors.GameColor;
import org.example.bcpv2.games.chess.Move;
import org.example.bcpv2.games.chess.eunums.Color;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
public abstract class AbsGame {
    protected String gameId;
    protected GameColor white;
    protected GameColor black;
    protected Color isPlaying;
    protected List<Move> moves;

    public void changeColor() {
        this.isPlaying = (this.isPlaying.equals(Color.WHITE)) ? Color.BLACK : Color.WHITE;
    }

    public List<Color> getAvailableColors() {
        List<Color> colors = new ArrayList<>();
        if (!white.isTaken()) colors.add(Color.WHITE);
        if (!black.isTaken()) colors.add(Color.BLACK);
        return colors;
    }

    public void setColorWhite() {
        this.white.setTaken(true);
    }

    public void setColorBlack() {
        this.black.setTaken(true);
    }

    public void addMove(Move move) {
        this.moves.add(move);
    }

}
