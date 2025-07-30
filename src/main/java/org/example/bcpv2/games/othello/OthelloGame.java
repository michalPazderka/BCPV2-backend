package org.example.bcpv2.games.othello;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.bcpv2.GameColors.GameColor;
import org.example.bcpv2.boards.OthelloBoard;
import org.example.bcpv2.games.abstractGame.AbsGame;
import org.example.bcpv2.games.chess.eunums.Color;
import org.example.bcpv2.games.othello.rules.OthelloRules;

import java.util.ArrayList;
import java.util.List;


@Data
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class OthelloGame extends AbsGame {

    protected OthelloBoard board;

    public OthelloGame(String gameId) {
        this.white = new GameColor(Color.WHITE);
        this.black = new GameColor(Color.BLACK);
        this.gameId = gameId;
        this.isPlaying = Color.BLACK;
        this.moves = new ArrayList<>();
        this.board = new OthelloBoard(new OthelloRules(this.isPlaying));
    }
}
