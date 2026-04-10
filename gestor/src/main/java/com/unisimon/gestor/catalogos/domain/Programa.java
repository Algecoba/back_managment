package com.unisimon.gestor.catalogos.domain;

import com.unisimon.gestor.shared.domain.EntidadAuditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Programa académico institucional.
 * Ejemplo: Ingeniería de Sistemas, Medicina, Derecho.
 *
 * Un programa pertenece a una facultad. Los investigadores
 * se adscriben a programas con fechas de vigencia en la
 * tabla investigador_programa (módulo investigadores).
 */
@Getter
@Setter
@Entity
@Table(name = "programas")
public class Programa extends EntidadAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "programa_id", updatable = false, nullable = false)
    private UUID programaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facultad_id", nullable = false)
    private Facultad facultad;

    @Column(name = "codigo", nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;
}