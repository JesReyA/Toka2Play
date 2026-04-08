package com.bytebalance.toka2play.controllers;

import com.bytebalance.toka2play.models.PuntoRequest;
import com.bytebalance.toka2play.services.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Component;

@Component
public class TokaSnakeController {
    @Autowired
    private ScoreService scoreService;

    @MessageMapping("/snake/puntuar") // Recibe puntos desde el JS de Snake
    public void procesarPuntoSnake(PuntoRequest request) {
        scoreService.actualizarPuntaje("tokasnakegame", request.getUsuario(), request.getPuntos());
    }
}
