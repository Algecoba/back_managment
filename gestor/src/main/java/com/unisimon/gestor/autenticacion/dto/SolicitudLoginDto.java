package com.unisimon.gestor.autenticacion.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * DTO de entrada para el endpoint de login.
 * Las validaciones rechazan requests malformados antes de llegar al servicio.
 */
@Getter
public class SolicitudLoginDto {

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo electrónico válido")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;
}