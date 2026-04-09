package com.bytebalance.toka2play;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Toka2playApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		dotenv.entries().forEach(entry -> {
			System.setProperty(entry.getKey(), entry.getValue());
		});

		// Línea de depuración:
		System.out.println("DEBUG: Llave cargada -> " + System.getProperty("GROQ_API_KEY"));

		SpringApplication.run(Toka2playApplication.class, args);
	}
}