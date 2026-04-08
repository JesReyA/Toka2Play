package com.bytebalance.toka2play;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping({"/", "/index"})
    public String index() {
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
}