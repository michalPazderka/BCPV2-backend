package org.example.bcpv2.Config;


import lombok.RequiredArgsConstructor;
import org.example.bcpv2.Service.ChessService;
import org.example.bcpv2.Service.GameService;
import org.example.bcpv2.Service.OthelloService;
import org.example.bcpv2.games.abstractGame.AbsGame;
import org.example.bcpv2.games.chess.ChessGame;
import org.example.bcpv2.games.othello.OthelloGame;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class GameServicesConfig {

    @Bean
    public Map<Class<? extends AbsGame>, GameService<?, ?>> gameServices(
            ChessService chessService,
            OthelloService othelloService) {
        return Map.of(
                ChessGame.class, chessService,
                OthelloGame.class, othelloService
        );
    }

}
