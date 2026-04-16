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
public class InvestigadorCentroId implements Serializable {

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "centro_investigacion_id")
    private Long centroInvestigacionId;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
}
