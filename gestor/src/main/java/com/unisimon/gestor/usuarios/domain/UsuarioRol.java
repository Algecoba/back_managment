package com.unisimon.gestor.usuarios.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Tabla puente N:M entre usuarios y roles.
 *
 * Registra además quién realizó la asignación del rol y cuándo.
 * No extiende EntidadAuditable porque su estructura de auditoría
 * es distinta: solo tiene asignado_en y asignado_por, sin
 * actualizado_en ni actualizado_por (una asignación no se edita,
 * se elimina y se crea una nueva).
 */
@Getter
@Setter
@Entity
@Table(name = "usuario_rol")
public class UsuarioRol {

    @EmbeddedId
    private UsuarioRolId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("rolId")
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @Column(name = "asignado_en", nullable = false)
    private LocalDateTime asignadoEn;

    // UUID del usuario administrador que realizó la asignación
    @Column(name = "asignado_por", nullable = false)
    private UUID asignadoPor;

    @PrePersist
    protected void antesDeGuardar() {
        if (asignadoEn == null) {
            asignadoEn = LocalDateTime.now();
        }
    }
}