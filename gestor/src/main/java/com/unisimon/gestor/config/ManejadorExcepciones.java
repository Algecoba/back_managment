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
 * Intercepta excepciones y las convierte en RespuestaDto uniforme.
 */
@Slf4j
@RestControllerAdvice
public class ManejadorExcepciones {

    @ExceptionHandler(ExcepcionNegocio.class)
    public ResponseEntity<RespuestaDto<Void>> manejarExcepcionNegocio(
            ExcepcionNegocio ex) {
        log.warn("Excepcion de negocio: {} - {}", ex.getClass().getSimpleName(), ex.getMessage());
        return ResponseEntity
            .status(ex.getHttpStatus())
            .body(RespuestaDto.error(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespuestaDto<Map<String, String>>> manejarValidacion(
            MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje);
        });
        log.warn("Error de validacion: {}", errores);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(RespuestaDto.error("Error de validacion en los datos enviados"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespuestaDto<Void>> manejarExcepcionGeneral(Exception ex) {
        log.error("Error inesperado no controlado", ex);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(RespuestaDto.error("Ocurrio un error inesperado. Contacte al administrador."));
    }
}
