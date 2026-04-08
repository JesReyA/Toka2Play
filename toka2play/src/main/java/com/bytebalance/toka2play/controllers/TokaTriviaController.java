package com.bytebalance.toka2play.controllers;

import com.bytebalance.toka2play.models.PuntoRequest;
import com.bytebalance.toka2play.models.TriviaAccionRequest;
import com.bytebalance.toka2play.services.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class TokaTriviaController {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/trivia/unirse")
    public void unirseTrivia(TriviaAccionRequest request) {
        System.out.println(request.getUsuario() + " se unió a la sala: " + request.getCodigoTrivia());
        
        String mensajeAviso = "El jugador " + request.getUsuario() + " ha entrado a la arena.";
        messagingTemplate.convertAndSend("/topic/trivia/" + request.getCodigoTrivia(), mensajeAviso);
    }
    @MessageMapping("/trivia/responder")
    public void procesarRespuesta(TriviaAccionRequest request) {
        System.out.println(request.getUsuario() + " respondió: " + request.getRespuesta());
        
        boolean esCorrecta = request.getRespuesta().equals("PAC-MAN"); // Ejemplo
        
        String resultado = esCorrecta ? "¡CORRECTO!" : "INCORRECTO";
        messagingTemplate.convertAndSend("/topic/trivia/" + request.getCodigoTrivia(), 
            request.getUsuario() + " ha respondido: " + resultado);
    }

    @MessageMapping("/trivia/guardar-puntaje")
    public void guardarPuntajeTrivia(PuntoRequest request) {
        scoreService.actualizarPuntaje("tokatrivia", request.getUsuario(), request.getPuntos());
    }
}