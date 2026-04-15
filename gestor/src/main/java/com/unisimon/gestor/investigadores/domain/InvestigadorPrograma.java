package com.unisimon.gestor.investigadores.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Vinculación histórica de un investigador a un programa académico.
 *
 * activo_hasta = null significa que la vinculación está vigente.
 * es_principal indica si es la adscripción primaria del investigador.
 *
 * PK compuesta: (usuario_id, programa_id, activo_desde) —
 * permite múltiples vigencias del mismo investigador en el mismo programa.
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

    @Column(name = "activo_hasta")
    private LocalDate activoHasta;

    @Column(name = "creado_por")
    private UUID creadoPor;
}