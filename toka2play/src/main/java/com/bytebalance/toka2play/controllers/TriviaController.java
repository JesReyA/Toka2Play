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
        String codigoFrontend = payload.get("codigo");
        int cantidad;
        try {
            cantidad = Integer.parseInt(payload.getOrDefault("cantidad", "10"));
        } catch (NumberFormatException e) {
            cantidad = 10;
        }

        try {
            Map<String, Object> result = triviaAiService.generarTriviaSolo(nombre, categoria, cantidad, dificultad, codigoFrontend);
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

    @GetMapping("/{codigo}")
    public ResponseEntity<?> obtenerTrivia(@PathVariable String codigo) {
        try {
            Map<String, Object> triviaData = triviaAiService.obtenerTrivia(codigo);
            if (triviaData != null) {
                return ResponseEntity.ok(triviaData);
            } else {
                return ResponseEntity.status(404).body(Map.of("error", "La trivia con código " + codigo + " no existe."));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    @Autowired
    private com.bytebalance.toka2play.repository.PuntajeTriviaRepository puntajeTriviaRepository;

    public static class SavePuntajePayload {
        public String codigo;
        public int idUsuario;
        public int puntaje;
    }

    @PostMapping("/puntaje")
    public ResponseEntity<?> guardarPuntaje(@RequestBody SavePuntajePayload payload) {
        try {
            com.bytebalance.toka2play.models.PuntajeTriviaId id = new com.bytebalance.toka2play.models.PuntajeTriviaId(payload.codigo, payload.idUsuario);
            
            java.util.Optional<com.bytebalance.toka2play.models.PuntajeTrivia> existingScore = puntajeTriviaRepository.findById(id);
            if (existingScore.isPresent()) {
                com.bytebalance.toka2play.models.PuntajeTrivia pt = existingScore.get();
                if (payload.puntaje > pt.getPuntaje()) {
                    pt.setPuntaje(payload.puntaje);
                    puntajeTriviaRepository.save(pt);
                }
            } else {
                com.bytebalance.toka2play.models.PuntajeTrivia pt = new com.bytebalance.toka2play.models.PuntajeTrivia(id, payload.puntaje);
                puntajeTriviaRepository.save(pt);
            }
            
            return ResponseEntity.ok(Map.of("message", "Puntaje procesado exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }
}