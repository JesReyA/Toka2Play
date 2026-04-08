package com.bytebalance.toka2play.controllers;

import com.bytebalance.toka2play.models.PuntoRequest;
import com.bytebalance.toka2play.services.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Component;

@Component
public class TokaDoodleController {
    @Autowired
    private ScoreService scoreService;

    @MessageMapping("/doodle/puntuar")
    public void procesarPuntoDoodle(PuntoRequest request) {
        // Usamos el identificador "tokadoodlegame"
        scoreService.actualizarPuntaje("tokadoodlegame", request.getUsuario(), request.getPuntos());
    }
}
