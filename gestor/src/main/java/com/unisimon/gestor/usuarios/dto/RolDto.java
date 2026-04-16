package com.unisimon.gestor.usuarios.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class RolDto {

    private UUID rolId;
    private String codigo;
    private String nombre;
    private String categoria;
}
