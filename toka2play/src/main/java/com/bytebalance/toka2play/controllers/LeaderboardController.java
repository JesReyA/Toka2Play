package com.bytebalance.toka2play.controllers;

import com.bytebalance.toka2play.models.Puntaje;
import com.bytebalance.toka2play.models.PuntajeId;
import com.bytebalance.toka2play.repository.PuntajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

    @Autowired
    private PuntajeRepository puntajeRepository;

    @GetMapping("/{idJuego}")
    public List<Map<String, Object>> getLeaderboard(@PathVariable int idJuego) {
        return puntajeRepository.findTop10ByJuego(idJuego);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveScore(@RequestBody Map<String, Integer> payload) {
        Integer idJuego = payload.get("idJuego");
        Integer idUsuario = payload.get("idUsuario");
        Integer score = payload.get("puntaje");

        if (idJuego == null || idUsuario == null || score == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Faltan datos obligatorios"));
        }

        PuntajeId id = new PuntajeId(idJuego, idUsuario);
        Optional<Puntaje> optionalPuntaje = puntajeRepository.findById(id);

        if (optionalPuntaje.isPresent()) {
            Puntaje p = optionalPuntaje.get();
            if (score > p.getPuntaje()) {
                p.setPuntaje(score);
                puntajeRepository.save(p);
            }
        } else {
            Puntaje p = new Puntaje(id, score);
            puntajeRepository.save(p);
        }

        return ResponseEntity.ok(Map.of("message", "Puntaje guardado correctamente"));
    }
}