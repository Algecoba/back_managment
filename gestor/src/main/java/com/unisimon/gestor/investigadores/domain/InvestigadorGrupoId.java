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
public class InvestigadorGrupoId implements Serializable {

    @Column(name = "usuario_id")
    private UUID usuarioId;

    @Column(name = "grupo_id")
    private UUID grupoId;

    @Column(name = "activo_desde")
    private LocalDate activoDesde;
}