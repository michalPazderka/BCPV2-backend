package org.example.bcpv2.controllers;


import lombok.RequiredArgsConstructor;
import org.example.bcpv2.Service.GeneralService;
import org.example.bcpv2.dto.AbsGameDto;
import org.example.bcpv2.dto.AbsLobbyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping()
    public ResponseEntity<Page<AbsLobbyDto>> getChessGames(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
                                                           Pageable pageable) {
        return generalService.getGames(pageable);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<AbsGameDto> getGame(@PathVariable String gameId) {
        var game = generalService.getGame(gameId);
        if (game == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(game);
    }

}
