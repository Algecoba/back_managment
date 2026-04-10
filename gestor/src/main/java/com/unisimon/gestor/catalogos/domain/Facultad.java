package com.unisimon.gestor.catalogos.domain;

import com.unisimon.gestor.shared.domain.EntidadAuditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Facultad institucional.
 * Ejemplo: Ingeniería (ING), Ciencias de la Salud (SALUD).
 *
 * Una facultad pertenece a una sede. Esta relación es importante
 * para el módulo de finanzas: el centro de costo se determina
 * según la facultad y el tipo de solicitud.
 */
@Getter
@Setter
@Entity
@Table(name = "facultades")
public class Facultad extends EntidadAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "facultad_id", updatable = false, nullable = false)
    private UUID facultadId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sede_id", nullable = false)
    private Sede sede;

    @Column(name = "codigo", nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;
}