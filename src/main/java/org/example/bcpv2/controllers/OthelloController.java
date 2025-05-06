package org.example.bcpv2.controllers;


import lombok.RequiredArgsConstructor;
import org.example.bcpv2.Service.OthelloService;
import org.example.bcpv2.dto.ChessGameDto;
import org.example.bcpv2.dto.ColorDto;
import org.example.bcpv2.dto.OthelloGameDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/othello")
public class OthelloController {

    private final OthelloService othelloService;

    @PostMapping("/new")
    public OthelloGameDto createGame(@RequestBody String color){
        return othelloService.createGame(color);
    }


    @GetMapping("/{gameId}")
    public ResponseEntity<OthelloGameDto> getGame(@PathVariable String gameId) {
        OthelloGameDto game = othelloService.getGame(gameId);
        if (game == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(game);
    }
    @PostMapping("/join/{gameId}")
    public OthelloGameDto joinGame(@PathVariable String gameId, @RequestBody String color){
        return othelloService.joinGame(gameId, color);
    }

    @GetMapping("/{gameId}/restOfColors")
    public ResponseEntity<ColorDto> getRestOfColors(@PathVariable String gameId){
        return ResponseEntity.ok(othelloService.colorDto(gameId));

    }
}
