package com.bytebalance.toka2play;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping({"/", "/index"})
    public String index() {
        // Esto busca el archivo "index.html" dentro de la carpeta templates
        return "index";
    }
    @GetMapping("/arkanoid")
    public String arkanoid() {
        return "arkanoid";
    }

    @GetMapping("/snake")
    public String snake() {
        return "snake";
    }
}