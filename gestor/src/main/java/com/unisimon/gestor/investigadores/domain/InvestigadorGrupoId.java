package com.unisimon.gestor.investigadores.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class InvestigadorGrupoId implements Serializable {

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "grupo_investigacion_id")
    private Long grupoInvestigacionId;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
}
