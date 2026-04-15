package com.unisimon.gestor.investigadores.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO genérico para representar una vinculación histórica.
 * Usado para programa, centro y grupo — todos comparten
 * la misma estructura de vigencia temporal.
 */
@Getter
@Builder
public class VinculacionDto {
    private UUID entidadId;
    private String nombre;
    private boolean esPrincipal;
    private LocalDate activoDesde;
    private LocalDate activoHasta;
    private boolean vigente;
}