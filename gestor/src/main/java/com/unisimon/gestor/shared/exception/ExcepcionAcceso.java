package com.unisimon.gestor.shared.exception;

import org.springframework.http.HttpStatus;

/**
 * Se lanza cuando un usuario autenticado intenta realizar una acción
 * para la que no tiene permisos según su rol.
 * Produce HTTP 403 Forbidden.
 *
 * Ejemplo: un Investigador intentando aprobar una solicitud,
 * acción reservada al Profesional de Publicaciones.
 */
public class ExcepcionAcceso extends ExcepcionNegocio {

    public ExcepcionAcceso(String mensaje) {
        super(mensaje, HttpStatus.FORBIDDEN);
    }
}