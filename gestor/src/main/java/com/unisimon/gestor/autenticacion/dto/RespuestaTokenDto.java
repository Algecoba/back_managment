package com.unisimon.gestor.autenticacion.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

/**
 * DTO de salida tras un login exitoso.
 * El frontend almacena el token y lo envía en cada request
 * como header: Authorization: Bearer <token>
 */
@Getter
@Builder
public class RespuestaTokenDto {

    private String token;
    private String tipo; // Siempre "Bearer"
    private UUID usuarioId;
    private String correo;
    private String roles;
    private long expiracionMs; // Para que el frontend sepa cuándo vence
}