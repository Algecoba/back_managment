package com.unisimon.gestor.investigadores.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class GrupoInvestigacionDto {
    private UUID grupoId;
    private String nombre;
    private boolean activo;
    private UUID centroId;
    private String nombreCentro;
}