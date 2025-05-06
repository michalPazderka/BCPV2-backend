package org.example.bcpv2.games.othello;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.bcpv2.GameColors.GameColor;
import org.example.bcpv2.boards.OthelloBoard;
import org.example.bcpv2.games.chess.eunums.Color;
import org.example.bcpv2.games.othello.rules.OthelloRules;

import java.util.ArrayList;
import java.util.List;


@Data
@Getter
@Setter
public class OthelloGame {

    private String gameId;
    private GameColor white;
    private GameColor black;
    private Color isPlaying;
    private List<String> moves;
    private OthelloBoard board;

    public OthelloGame(String gameId) {
        this.white = new GameColor(Color.WHITE);
        this.black = new GameColor(Color.BLACK);
        this.gameId = gameId;
        this.isPlaying = Color.BLACK;
        this.moves = new ArrayList<>();
        this.board = new OthelloBoard(new OthelloRules(this.isPlaying));
    }

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

}
