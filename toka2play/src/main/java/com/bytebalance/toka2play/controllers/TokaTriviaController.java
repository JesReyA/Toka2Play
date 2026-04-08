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

    // Herramienta crucial: Nos permite enviar mensajes DE VUELTA a los jugadores
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // 1. Cuando un usuario ingresa el código para unirse
    @MessageMapping("/trivia/unirse")
    public void unirseTrivia(TriviaAccionRequest request) {
        System.out.println(request.getUsuario() + " se unió a la sala: " + request.getCodigoTrivia());
        
        // Avisamos a todos los que estén suscritos a esta sala que alguien nuevo entró
        String mensajeAviso = "El jugador " + request.getUsuario() + " ha entrado a la arena.";
        messagingTemplate.convertAndSend("/topic/trivia/" + request.getCodigoTrivia(), mensajeAviso);
    }

    // 2. Cuando el usuario presiona una respuesta
    @MessageMapping("/trivia/responder")
    public void procesarRespuesta(TriviaAccionRequest request) {
        System.out.println(request.getUsuario() + " respondió: " + request.getRespuesta());
        
        // Lógica de validación (AQUÍ validas si la respuesta es correcta)
        boolean esCorrecta = request.getRespuesta().equals("PAC-MAN"); // Ejemplo
        
        String resultado = esCorrecta ? "¡CORRECTO!" : "INCORRECTO";

        // Le respondemos a la sala cómo le fue a ese usuario (opcional)
        // O podrías enviar un mensaje directo solo a ese usuario
        messagingTemplate.convertAndSend("/topic/trivia/" + request.getCodigoTrivia(), 
            request.getUsuario() + " ha respondido: " + resultado);
    }

    // 3. Cuando termina la trivia y hay que guardar el puntaje en la Base de Datos
    @MessageMapping("/trivia/guardar-puntaje")
    public void guardarPuntajeTrivia(PuntoRequest request) {
        // Reutilizamos tu ScoreService
        scoreService.actualizarPuntaje("tokatrivia", request.getUsuario(), request.getPuntos());
    }
}