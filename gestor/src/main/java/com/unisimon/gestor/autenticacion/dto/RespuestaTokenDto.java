package com.unisimon.gestor.autenticacion.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

/**
 * DTO de salida tras un login exitoso.
 */
@Getter
@Builder
public class RespuestaTokenDto {

    private String token;
    private String tipo;
    private UUID usuarioId;
    private String correo;
    private String roles;
    private long expiracionMs;
}
