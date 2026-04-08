package com.bytebalance.toka2play.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "redirect:/index.html";
    }

    @GetMapping("/arkanoid")
    public String arkanoid() {
        return "redirect:/arkanoid.html";
    }

    @GetMapping("/snake")
    public String snake() {
        return "redirect:/snake.html";
    }
}