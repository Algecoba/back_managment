package com.unisimon.gestor.catalogos.domain;

import com.unisimon.gestor.shared.domain.EntidadAuditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Tabla: sede
 * Sede institucional. Ejemplo: Barranquilla (BAQ).
 */
@Getter
@Setter
@Entity
@Table(name = "sede")
public class Sede extends EntidadAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true,
            updatable = false, columnDefinition = "UNIQUEIDENTIFIER DEFAULT NEWID()")
    private UUID uuid;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "codigo", nullable = false, unique = true, length = 20)
    private String codigo;

    @PrePersist
    protected void antesDeGuardar() {
        if (uuid == null) uuid = UUID.randomUUID();
    }
}
