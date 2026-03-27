package com.unisimon.gestor.autenticacion.exception;

import com.unisimon.gestor.shared.exception.ExcepcionNegocio;
import org.springframework.http.HttpStatus;

/**
 * Se lanza cuando las credenciales de login son incorrectas.
 * Produce HTTP 401 Unauthorized.
 *
 * Intencionalmente el mensaje es genérico: no indicamos si el correo
 * no existe o si la contraseña es incorrecta, para no dar pistas
 * a posibles atacantes sobre qué usuarios existen en el sistema.
 */
public class ExcepcionAutenticacion extends ExcepcionNegocio {

    public ExcepcionAutenticacion() {
        super("Credenciales incorrectas", HttpStatus.UNAUTHORIZED);
    }
}