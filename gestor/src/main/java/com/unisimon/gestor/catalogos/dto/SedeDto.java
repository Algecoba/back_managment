package com.unisimon.gestor.catalogos.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class SedeDto {
    private UUID sedeId;
    private String codigo;
    private String nombre;
    private boolean activo;
}
