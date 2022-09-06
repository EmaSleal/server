package com.backend.server;

import com.backend.server.enumeration.Status;
import com.backend.server.model.Server;
import com.backend.server.repository.ServerRepository;
import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ServerRepository repository) {
		return args -> {

			repository.save(new Server(null, "192.168.100.111", "windows 10", "16 GB", "Personal",
					"http://localhost:8080/server/image/server1.png", Status.SERVER_UP));
			repository.save(new Server(null, "192.168.1.161", "windows 8", "16 GB", "Personal",
					"http://localhost:8080/server/image/server1.png", Status.SERVER_DOWN));
			repository.save(new Server(null, "192.168.100.83", "windows 7", "16 GB", "Personal",
					"http://localhost:8080/server/image/server1.png", Status.SERVER_UP));
		};
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource basedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200"));
		configuration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type", "Accept",
				"Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With", "Access-Control-Request-Method",
				"Access-Control-Request-Headers"));
		configuration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Filename"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		basedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
		return new CorsFilter(basedCorsConfigurationSource);
	}

}
