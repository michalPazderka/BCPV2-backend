package org.example.bcpv2.games.chess;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.example.bcpv2.GameColors.GameColor;
import org.example.bcpv2.boards.ChessBoard;
import org.example.bcpv2.games.abstractGame.AbsGame;
import org.example.bcpv2.games.chess.eunums.Color;
import org.example.bcpv2.games.chess.rules.ChessRules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class ChessGame extends AbsGame {
    private ChessBoard board;

    public ChessGame(String gameId) {
        this.white = new GameColor(Color.WHITE);
        this.black = new GameColor(Color.BLACK);
        this.gameId = gameId;
        this.isPlaying = Color.WHITE;
        this.moves = new ArrayList<>();
        this.board = new ChessBoard(new ChessRules(this.isPlaying));
    }
}
