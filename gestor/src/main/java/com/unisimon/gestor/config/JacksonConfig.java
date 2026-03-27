package com.unisimon.gestor.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Jackson (serialización/deserialización JSON).
 *
 * Problema que resuelve: por defecto Jackson serializa LocalDateTime
 * como un array numérico [2025, 3, 25, 18, 0, 0] en lugar del
 * string ISO-8601 "2025-03-25T18:00:00" que espera el frontend.
 *
 * JavaTimeModule enseña a Jackson a manejar las clases de fecha
 * de Java 8+ (LocalDate, LocalDateTime, ZonedDateTime, etc.)
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Registrar soporte para java.time.*
        mapper.registerModule(new JavaTimeModule());

        // Serializar fechas como strings ISO-8601, no como timestamps numéricos
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }
}