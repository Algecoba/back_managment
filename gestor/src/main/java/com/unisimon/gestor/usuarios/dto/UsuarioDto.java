package com.unisimon.gestor.usuarios.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

/**
 * DTO de salida para el perfil de un usuario.
 * Nunca exponemos la entidad Usuario directamente — este DTO
 * controla exactamente qué campos llegan al frontend.
 */
@Getter
@Builder
public class UsuarioDto {

    private UUID usuarioId;
    private String correo;
    private String nombres;
    private String apellidos;
    private String tipoDoc;
    private String numDoc;
    private boolean activo;
    private List<RolDto> roles;
}