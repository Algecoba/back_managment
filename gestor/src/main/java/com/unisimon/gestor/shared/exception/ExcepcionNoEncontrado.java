package com.unisimon.gestor.shared.exception;

import org.springframework.http.HttpStatus;

/**
 * Se lanza cuando un recurso buscado por ID no existe en la base de datos.
 * Produce HTTP 404 Not Found.
 *
 * Ejemplo: buscar una solicitud con un UUID que no existe.
 */
public class ExcepcionNoEncontrado extends ExcepcionNegocio {

    public ExcepcionNoEncontrado(String mensaje) {
        super(mensaje, HttpStatus.NOT_FOUND);
    }
}