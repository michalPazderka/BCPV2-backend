package org.example.bcpv2.controllers;


import lombok.RequiredArgsConstructor;
import org.example.bcpv2.Service.GeneralService;
import org.example.bcpv2.dto.AbsGameDto;
import org.example.bcpv2.dto.ChessGameDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/general")
public class GeneralController {

    private final GeneralService generalService;

    @GetMapping("/{gameId}")
    public ResponseEntity<AbsGameDto> getGame(@PathVariable String gameId) {
        var game = generalService.getGame(gameId);
        if (game == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(game);
    }

}
