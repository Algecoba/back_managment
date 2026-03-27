package com.unisimon.gestor.config;

import com.unisimon.gestor.shared.dto.RespuestaDto;
import com.unisimon.gestor.shared.exception.ExcepcionNegocio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones.
 *
 * Intercepta excepciones lanzadas en cualquier controlador y las
 * convierte en respuestas HTTP con formato RespuestaDto uniforme.
 *
 * Jerarquía de manejo (de más específico a más general):
 * 1. ExcepcionNegocio → error de dominio con HTTP status propio
 * 2. MethodArgumentNotValidException → errores de @Valid en DTOs
 * 3. Exception → cualquier error inesperado (HTTP 500)
 */
@Slf4j
@RestControllerAdvice
public class ManejadorExcepciones {

    /**
     * Maneja todas las excepciones de negocio del sistema.
     * Cada ExcepcionNegocio ya trae su propio HttpStatus definido.
     * Ejemplo: ExcepcionNoEncontrado → 404, ExcepcionAcceso → 403
     */
    @ExceptionHandler(ExcepcionNegocio.class)
    public ResponseEntity<RespuestaDto<Void>> manejarExcepcionNegocio(
            ExcepcionNegocio ex) {

        log.warn("Excepción de negocio: {} - {}", ex.getClass().getSimpleName(), ex.getMessage());

        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(RespuestaDto.error(ex.getMessage()));
    }

    /**
     * Maneja errores de validación de DTOs anotados con @Valid.
     * Devuelve un mapa con el nombre del campo y el mensaje de error.
     *
     * Ejemplo de respuesta:
     * {
     * "exitoso": false,
     * "mensaje": "Error de validación en los datos enviados",
     * "data": {
     * "correo": "debe ser una dirección de correo válida",
     * "nombres": "no debe estar vacío"
     * }
     * }
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespuestaDto<Map<String, String>>> manejarValidacion(
            MethodArgumentNotValidException ex) {

        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje);
        });

        log.warn("Error de validación: {}", errores);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(RespuestaDto.error("Error de validación en los datos enviados"));
    }

    /**
     * Captura cualquier excepción no controlada para evitar que
     * Spring devuelva su página de error genérica con stack trace.
     * En producción estos errores deben investigarse inmediatamente.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespuestaDto<Void>> manejarExcepcionGeneral(Exception ex) {

        // Log completo con stack trace — solo visible en servidor, nunca al cliente
        log.error("Error inesperado no controlado", ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(RespuestaDto.error("Ocurrió un error inesperado. Contacte al administrador."));
    }
}