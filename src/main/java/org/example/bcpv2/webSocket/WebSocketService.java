package org.example.bcpv2.webSocket;

import org.example.bcpv2.dto.ChessGameDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyPlayers(String gameId, ChessGameDto chessGameDto) {
        messagingTemplate.convertAndSend("/topic/game/" + gameId, chessGameDto);
    }
}
