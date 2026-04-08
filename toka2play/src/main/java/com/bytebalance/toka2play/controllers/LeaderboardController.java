package com.bytebalance.toka2play.controllers;

import com.bytebalance.toka2play.repository.PuntajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

    @Autowired
    private PuntajeRepository puntajeRepository;

    @GetMapping("/{idJuego}")
    public List<Map<String, Object>> getLeaderboard(@PathVariable int idJuego) {
        return puntajeRepository.findTop10ByJuego(idJuego);
    }
}