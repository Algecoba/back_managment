package com.unisimon.gestor.catalogos.domain;

import com.unisimon.gestor.shared.domain.EntidadAuditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Tabla: facultad
 * Facultad institucional. Ejemplo: Ingenieria (ING).
 * Pertenece a una sede.
 */
@Getter
@Setter
@Entity
@Table(name = "facultad")
public class Facultad extends EntidadAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true, updatable = false, columnDefinition = "UNIQUEIDENTIFIER DEFAULT NEWID()")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sede_id", nullable = false)
    private Sede sede;

    @Column(name = "codigo", nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @PrePersist
    protected void antesDeGuardar() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }
}