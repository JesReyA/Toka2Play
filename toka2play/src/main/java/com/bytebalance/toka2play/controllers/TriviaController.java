// ruta: src/main/java/com/bytebalance/toka2play/controllers/TriviaController.java
package com.bytebalance.toka2play.controllers;

import com.bytebalance.toka2play.services.TriviaAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;
import com.bytebalance.toka2play.models.TriviaResponse;

@RestController
@RequestMapping("/api/trivia")
public class TriviaController {

    @Autowired
    private TriviaAiService triviaAiService;

    @PostMapping("/generate")
    public ResponseEntity<?> crearTriviaIA(@RequestBody Map<String, String> payload) {
        String nombre = payload.getOrDefault("nombre", "Trivia");
        String categoria = payload.getOrDefault("categoria", "cultura general");
        String dificultad = payload.getOrDefault("dificultad", "Fácil");
        int cantidad;
        try {
            cantidad = Integer.parseInt(payload.getOrDefault("cantidad", "10"));
        } catch (NumberFormatException e) {
            cantidad = 10;
        }

        try {
            Map<String, Object> result = triviaAiService.generarTriviaSolo(nombre, categoria, cantidad, dificultad);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    public static class SaveTriviaPayload {
        public String codigo;
        public String nombre;
        public List<TriviaResponse.Question> questions;
    }

    @PostMapping("/save")
    public ResponseEntity<?> guardarTriviaFinalizada(@RequestBody SaveTriviaPayload payload) {
        try {
            triviaAiService.guardarTrivia(payload.codigo, payload.nombre, payload.questions);
            return ResponseEntity.ok(Map.of("message", "Trivia guardada exitosamente en la base de datos"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }
}