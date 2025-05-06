package org.example.bcpv2.games.chess;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.bcpv2.GameColors.GameColor;
import org.example.bcpv2.boards.ChessBoard;
import org.example.bcpv2.games.chess.eunums.Color;
import org.example.bcpv2.games.chess.rules.ChessRules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Getter
@Setter
public class ChessGame {
    private String gameId;
    private GameColor white;
    private GameColor black;
    private Color isPlaying;
    private List<String> moves;
    private ChessBoard board;

    public ChessGame(String gameId) {
        this.white = new GameColor(Color.WHITE);
        this.black = new GameColor(Color.BLACK);
        this.gameId = gameId;
        this.isPlaying = Color.WHITE;
        this.moves = new ArrayList<>();
        this.board = new ChessBoard(new ChessRules(this.isPlaying));
    }

    public void changeColor() {
        this.isPlaying = (this.isPlaying.equals(Color.WHITE)) ? Color.BLACK : Color.WHITE;
    }

    public List<Color> getAvailableColors(){
        List<Color> colors = new ArrayList<>();
        if (!white.isTaken()) colors.add(Color.WHITE);
        if (!black.isTaken()) colors.add(Color.BLACK);
        return colors;
    }

    public void setColorWhite(){
        this.white.setTaken(true);
    }

    public void setColorBlack() {
        this.black.setTaken(true);
    }
}
