package com.unisimon.gestor.investigadores.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class PerfilInvestigadorDto {
    private UUID usuarioId;
    private String nombres;
    private String apellidos;
    private String correo;
    private Long sedePredeterminadaId;
    private List<VinculacionDto> programas;
    private List<VinculacionDto> centros;
    private List<VinculacionDto> grupos;
}
