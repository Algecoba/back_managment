package com.unisimon.gestor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuración de CORS (Cross-Origin Resource Sharing).
 *
 * Durante desarrollo el frontend corre en http://localhost:5173 (Vite)
 * y el backend en http://localhost:8080. Sin esta configuración el
 * navegador bloquea todas las peticiones entre orígenes distintos.
 *
 * En producción el origen permitido viene de una variable de entorno
 * para no hardcodear la URL del servidor institucional.
 */
@Configuration
public class CorsConfig {

    // En dev = http://localhost:5173, en prod viene de application-prod.properties
    @Value("${cors.origen-permitido:http://localhost:5173}")
    private String origenPermitido;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuracion = new CorsConfiguration();

        configuracion.setAllowedOrigins(List.of(origenPermitido));

        // Métodos HTTP que acepta el backend
        configuracion.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // Headers que puede enviar el frontend (Authorization lleva el JWT)
        configuracion.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));

        // Permite que el frontend lea headers de la respuesta (como Authorization)
        configuracion.setAllowCredentials(true);

        // Cuánto tiempo el navegador puede cachear la respuesta del preflight OPTIONS
        configuracion.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuracion);
        return source;
    }
}
