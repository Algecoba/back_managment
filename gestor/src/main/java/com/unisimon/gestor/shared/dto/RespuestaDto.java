package com.unisimon.gestor.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Envelope estándar para todas las respuestas de la API.
 *
 * Estructura uniforme que recibe el frontend en cada llamada:
 * {
 * "exitoso": true,
 * "mensaje": "Solicitud creada correctamente",
 * "data": { ... },
 * "timestamp": "2025-03-25T18:00:00"
 * }
 *
 * El campo "data" es null cuando la operación no retorna contenido
 * (por ejemplo, un DELETE exitoso). @JsonInclude lo omite del JSON
 * para no enviar "data: null" innecesariamente.
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespuestaDto<T> {

    private final boolean exitoso;
    private final String mensaje;
    private final T data;
    private final LocalDateTime timestamp;

    // Constructor privado — se obliga a usar los métodos estáticos de fábrica.
    // Esto hace el código más legible: RespuestaDto.exito(...) vs new
    // RespuestaDto(true, ...)
    private RespuestaDto(boolean exitoso, String mensaje, T data) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    // ── Métodos de fábrica ────────────────────────────────────────

    /** Respuesta exitosa con datos y mensaje personalizado */
    public static <T> RespuestaDto<T> exito(String mensaje, T data) {
        return new RespuestaDto<>(true, mensaje, data);
    }

    /**
     * Respuesta exitosa con solo mensaje (sin datos — útil para DELETE o acciones)
     */
    public static <T> RespuestaDto<T> exito(String mensaje) {
        return new RespuestaDto<>(true, mensaje, null);
    }

    /** Respuesta de error con mensaje descriptivo */
    public static <T> RespuestaDto<T> error(String mensaje) {
        return new RespuestaDto<>(false, mensaje, null);
    }
}