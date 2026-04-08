package com.bytebalance.toka2play.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ScoreService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void actualizarPuntaje(String juego, String usuario, int nuevosPuntos){
        messagingTemplate.convertAndSend("/topic/scores/" + juego,
                (Object)Map.of("usuario", usuario, "puntos", nuevosPuntos));
    }

}
