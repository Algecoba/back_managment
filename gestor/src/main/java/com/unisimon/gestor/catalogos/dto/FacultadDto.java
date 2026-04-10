package com.unisimon.gestor.catalogos.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class FacultadDto {
    private UUID facultadId;
    private String codigo;
    private String nombre;
    private boolean activo;
    private UUID sedeId;
    private String nombreSede;
}