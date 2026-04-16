package com.unisimon.gestor.investigadores.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class CentroInvestigacionDto {
    private UUID centroId;
    private String nombre;
    private String acronimo;
    private String categoria;
    private String correoContacto;
    private boolean activo;
}
