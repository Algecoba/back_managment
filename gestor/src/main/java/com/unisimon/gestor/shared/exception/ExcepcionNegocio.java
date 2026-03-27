package com.unisimon.gestor.shared.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción base para todos los errores de negocio del sistema.
 *
 * Cada módulo define sus propias excepciones extendiendo esta clase.
 * El manejador global en config/ManejadorExcepciones intercepta
 * cualquier ExcepcionNegocio y la convierte en respuesta HTTP
 * usando el httpStatus que cada subclase declara.
 */
public class ExcepcionNegocio extends RuntimeException {

    private final HttpStatus httpStatus;

    public ExcepcionNegocio(String mensaje, HttpStatus httpStatus) {
        super(mensaje);
        this.httpStatus = httpStatus;
    }

    public ExcepcionNegocio(String mensaje, HttpStatus httpStatus, Throwable causa) {
        super(mensaje, causa);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}