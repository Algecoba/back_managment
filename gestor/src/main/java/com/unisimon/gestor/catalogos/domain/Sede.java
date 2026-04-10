package com.unisimon.gestor.catalogos.domain;

import com.unisimon.gestor.shared.domain.EntidadAuditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Sede institucional.
 * Ejemplo: Barranquilla (BAQ), Cúcuta (CUC).
 *
 * Es el nivel más alto de la jerarquía institucional.
 * Las solicitudes siempre están asociadas a una sede.
 */
@Getter
@Setter
@Entity
@Table(name = "sedes")
public class Sede extends EntidadAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "sede_id", updatable = false, nullable = false)
    private UUID sedeId;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "codigo", nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;
}