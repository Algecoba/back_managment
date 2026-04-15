package com.unisimon.gestor.investigadores.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Vinculación histórica de un investigador a un centro de investigación.
 * Misma lógica de vigencia que InvestigadorPrograma.
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

    @Column(name = "activo_hasta")
    private LocalDate activoHasta;

    @Column(name = "creado_por")
    private UUID creadoPor;
}