package com.bytebalance.toka2play.controllers;

import com.bytebalance.toka2play.models.PuntoRequest;
import com.bytebalance.toka2play.services.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class TokaArkanoidController {

    @Autowired
    private ScoreService scoreService;

    @MessageMapping("/arkanoid/puntuar")
    public void procesarPuntoArkanoid(PuntoRequest request) {
        scoreService.actualizarPuntaje("tokaarkanoid", request.getUsuario(), request.getPuntos());
    }
}
