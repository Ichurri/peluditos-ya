package com.peluditosya.peluditos_ya_server;

import com.peluditosya.peluditos_ya_server.config.EnvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.peluditosya.peluditos_ya_server.repository")
public class PeluditosYaServerApplication {

	public static void main(String[] args) {
		// Cargar variables de entorno desde el archivo .env
		EnvLoader.loadEnv();

		SpringApplication.run(PeluditosYaServerApplication.class, args);
	}
}
