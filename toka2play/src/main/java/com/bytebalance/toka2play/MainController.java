package com.bytebalance.toka2play;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping({"/", "/index"})
    public String index() {
<<<<<<< HEAD:toka2play/src/main/java/com/bytebalance/toka2play/controllers/MainController.java
        return "redirect:/index.html";
=======
        // Esto busca el archivo "index.html" dentro de la carpeta templates
        return "index";
>>>>>>> 32efe7030e577409ca8b4c2030b07f762f7c0bce:toka2play/src/main/java/com/bytebalance/toka2play/MainController.java
    }
    @GetMapping("/arkanoid")
    public String arkanoid() {
<<<<<<< HEAD:toka2play/src/main/java/com/bytebalance/toka2play/controllers/MainController.java
        return "redirect:/arkanoid.html";
=======
        return "arkanoid";
>>>>>>> 32efe7030e577409ca8b4c2030b07f762f7c0bce:toka2play/src/main/java/com/bytebalance/toka2play/MainController.java
    }

    @GetMapping("/snake")
    public String snake() {
<<<<<<< HEAD:toka2play/src/main/java/com/bytebalance/toka2play/controllers/MainController.java
        return "redirect:/snake.html";
=======
        return "snake";
>>>>>>> 32efe7030e577409ca8b4c2030b07f762f7c0bce:toka2play/src/main/java/com/bytebalance/toka2play/MainController.java
    }
}