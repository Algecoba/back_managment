package com.unisimon.gestor.investigadores.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Tabla: investigador_grupo
 */
@Getter
@Setter
@Entity
@Table(name = "investigador_grupo")
public class InvestigadorGrupo {

    @EmbeddedId
    private InvestigadorGrupoId id;

    @Column(name = "es_principal", nullable = false)
    private boolean esPrincipal = false;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "usuario_creacion", length = 100)
    private String usuarioCreacion;
}
