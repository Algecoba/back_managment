package com.unisimon.gestor.investigadores.domain;

import com.unisimon.gestor.shared.domain.EntidadAuditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Tabla: grupo_investigacion
 */
@Getter
@Setter
@Entity
@Table(name = "grupo_investigacion")
public class GrupoInvestigacion extends EntidadAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true,
            updatable = false, columnDefinition = "UNIQUEIDENTIFIER DEFAULT NEWID()")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "centro_investigacion_id", nullable = false)
    private CentroInvestigacion centro;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "correo_lider", length = 255)
    private String correoLider;

    @PrePersist
    protected void antesDeGuardar() {
        if (uuid == null) uuid = UUID.randomUUID();
    }
}
