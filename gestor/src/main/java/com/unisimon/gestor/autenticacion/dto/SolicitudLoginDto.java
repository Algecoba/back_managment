package com.unisimon.gestor.autenticacion.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * DTO de entrada para el endpoint de login.
 */
@Getter
public class SolicitudLoginDto {

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo electronico valido")
    private String correo;

    @NotBlank(message = "La contrasena es obligatoria")
    private String contrasena;
}
