package com.unisimon.gestor.shared.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

/**
 * Excepción base para todos los errores de negocio del sistema.
 *
 * Cada módulo define sus propias excepciones extendiendo esta clase.
 * El manejador global de excepciones (que crearemos en config/) intercepta
 * cualquier ExcepcionNegocio y la convierte en una respuesta HTTP apropiada
 * usando el httpStatus que cada excepción declara.
 *
 * Ejemplo de uso en un servicio:
 * throw new ExcepcionNoEncontrado("Solicitud no encontrada con id: " + id);
 */
@Getter
public class ExcepcionNegocio extends RuntimeException {

    // El status HTTP que debe retornar esta excepción al cliente
    private final HttpStatus httpStatus;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ExcepcionNegocio(String mensaje, HttpStatus httpStatus) {
        super(mensaje);
        this.httpStatus = httpStatus;
    }

    public ExcepcionNegocio(String mensaje, HttpStatus httpStatus, Throwable causa) {
        super(mensaje, causa);
        this.httpStatus = httpStatus;
    }
}