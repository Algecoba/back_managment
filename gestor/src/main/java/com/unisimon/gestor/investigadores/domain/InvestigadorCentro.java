package com.unisimon.gestor.investigadores.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Tabla: investigador_centro
 */
@Getter
@Setter
@Entity
@Table(name = "investigador_centro")
public class InvestigadorCentro {

    @EmbeddedId
    private InvestigadorCentroId id;

    @Column(name = "es_principal", nullable = false)
    private boolean esPrincipal = false;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "usuario_creacion", length = 100)
    private String usuarioCreacion;
}
