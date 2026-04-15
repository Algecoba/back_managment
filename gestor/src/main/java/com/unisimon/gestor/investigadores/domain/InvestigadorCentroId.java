package com.unisimon.gestor.investigadores.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class InvestigadorCentroId implements Serializable {

    @Column(name = "usuario_id")
    private UUID usuarioId;

    @Column(name = "centro_id")
    private UUID centroId;

    @Column(name = "activo_desde")
    private LocalDate activoDesde;
}