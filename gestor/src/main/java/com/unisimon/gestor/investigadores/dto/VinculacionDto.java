package com.unisimon.gestor.investigadores.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

/**
 * DTO generico para vinculaciones historicas.
 * entidadId es el id interno Long.
 */
@Getter
@Builder
public class VinculacionDto {
    private Long entidadId;
    private boolean esPrincipal;
    private LocalDate activoDesde;
    private LocalDate activoHasta;
    private boolean vigente;
}
