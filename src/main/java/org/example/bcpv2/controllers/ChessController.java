package org.example.bcpv2.controllers;

import lombok.RequiredArgsConstructor;
import org.example.bcpv2.Service.ChessService;
import org.example.bcpv2.dto.ChessGameDto;
import org.example.bcpv2.dto.ColorDto;
import org.example.bcpv2.games.chess.ChessGame;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chess")
public class ChessController {

    private final ChessService chessService;

    @PostMapping("/new")
    public ChessGameDto createGame(@RequestBody String color){
        return chessService.createGame(color);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<ChessGameDto> getGame(@PathVariable String gameId) {
        ChessGameDto game = chessService.getGame(gameId);
        if (game == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(game);
    }
    @PostMapping("/join/{gameId}")
    public ChessGameDto joinGame(@PathVariable String gameId, @RequestBody String color){
         return chessService.joinGame(gameId, color);
    }

    @GetMapping("/{gameId}/restOfColors")
    public ResponseEntity<ColorDto> getRestOfColors(@PathVariable String gameId){
        return ResponseEntity.ok(chessService.colorDto(gameId));

    }

    @PostMapping("/{gameId}/move")
    public ChessGameDto makeMove(@PathVariable String gameId, @RequestBody String move) {
        return chessService.makeMove(gameId, move);
    }

}
