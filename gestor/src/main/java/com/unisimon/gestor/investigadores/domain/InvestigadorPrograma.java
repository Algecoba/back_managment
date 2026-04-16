package com.unisimon.gestor.investigadores.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Tabla: investigador_programa
 * Vinculacion historica de un investigador a un programa academico.
 * fecha_fin null = vinculacion vigente actualmente.
 */
@Getter
@Setter
@Entity
@Table(name = "investigador_programa")
public class InvestigadorPrograma {

    @EmbeddedId
    private InvestigadorProgramaId id;

    @Column(name = "es_principal", nullable = false)
    private boolean esPrincipal = false;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "usuario_creacion", length = 100)
    private String usuarioCreacion;
}