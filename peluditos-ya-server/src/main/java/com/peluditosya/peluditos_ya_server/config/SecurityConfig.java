package com.peluditosya.peluditos_ya_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS activo
                .csrf(csrf -> csrf.disable()) // CSRF desactivado (usualmente necesario para APIs)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll() // Permitir todos los endpoints públicos
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:4200",
                "https://www.peluditosya.publicvm.com",
                "https://peluditosya.publicvm.com",
                "https://lemon-pebble-06ee7b410.6.azurestaticapps.net"
        ));

        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization", "*"));
        config.setExposedHeaders(Arrays.asList("Authorization", "Content-Disposition")); // Por si necesitas exponer headers
        config.setAllowCredentials(true); // Para permitir cookies o Authorization

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Se aplica a toda la API

        return source;
    }
}
