package com.unisimon.gestor.usuarios.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * Clave primaria compuesta para la tabla usuario_rol.
 *
 * JPA exige que las claves compuestas sean Serializable e
 * implementen equals() y hashCode() correctamente.
 * Lombok @EqualsAndHashCode se encarga de eso automáticamente.
 */
@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class UsuarioRolId implements Serializable {

    @Column(name = "usuario_id")
    private UUID usuarioId;

    @Column(name = "rol_id")
    private UUID rolId;
}