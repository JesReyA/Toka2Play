package com.bytebalance.toka2play;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Toka2playApplication {

	public static void main(String[] args) {
		// Carga el archivo .env
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

		// Inyecta las variables en el sistema de propiedades de Java
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(Toka2playApplication.class, args);
	}
}