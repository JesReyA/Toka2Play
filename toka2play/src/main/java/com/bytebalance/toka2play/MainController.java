package com.bytebalance.toka2play;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bytebalance.toka2play.models.Usuario;
import com.bytebalance.toka2play.repository.UsuarioRepository;

@Controller
public class MainController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        Usuario usuario = usuarioRepository.findById(1).orElse(null);
        int nivel = usuarioRepository.calcularNivel(1);
        
        model.addAttribute("usuario", usuario);
        model.addAttribute("nivel", nivel); 
        return "index";
    }

    @GetMapping("/minijuegos/snake")
    public String snake() { return "/minijuegos/snake"; }

    @GetMapping("/minijuegos/arkanoid")
    public String arkanoid() { return "/minijuegos/arkanoid"; }

    @GetMapping("/minijuegos/doodle")
    public String doodle() { return "/minijuegos/paginalnicioMiniApp"; }

    @GetMapping("/minijuegos/tokoala")
    public String tokoala() { return "/minijuegos/tokoala"; }

    @GetMapping("/minijuegos/carros")
    public String carros() { return "/minijuegos/carros"; }

    @GetMapping("/trivias/creacion")
    public String creacion() { return "/trivias/creacion"; }

    @GetMapping("/trivias/unirse")
    public String unirse() { return "/trivias/unirse"; }

    @GetMapping("/trivias/contestando")
    public String contestando() { return "/trivias/triviaAContestar"; }
}