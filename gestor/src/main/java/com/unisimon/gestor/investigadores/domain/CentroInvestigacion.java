package com.unisimon.gestor.investigadores.domain;

import com.unisimon.gestor.shared.domain.EntidadAuditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Tabla: centro_investigacion
 * Centro de investigacion institucional.
 * Agrupa grupos de investigacion.
 */
@Getter
@Setter
@Entity
@Table(name = "centro_investigacion")
public class CentroInvestigacion extends EntidadAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true, updatable = false, columnDefinition = "UNIQUEIDENTIFIER DEFAULT NEWID()")
    private UUID uuid;

    @Column(name = "nombre", nullable = false, unique = true, length = 200)
    private String nombre;

    @Column(name = "acronimo", unique = true, length = 50)
    private String acronimo;

    @Column(name = "categoria", length = 50)
    private String categoria;

    // Correo del lider actual del centro (nullable)
    @Column(name = "correo_lider", length = 255)
    private String correoLider;

    @Column(name = "correo_contacto", length = 255)
    private String correoContacto;

    @PrePersist
    protected void antesDeGuardar() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }
}