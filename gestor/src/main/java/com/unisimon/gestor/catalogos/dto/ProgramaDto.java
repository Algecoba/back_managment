package com.unisimon.gestor.catalogos.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ProgramaDto {
    private UUID programaId;
    private String codigo;
    private String nombre;
    private boolean activo;
    private UUID facultadId;
    private String nombreFacultad;
}