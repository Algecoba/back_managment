package com.unisimon.gestor.autenticacion.controller;

import com.unisimon.gestor.autenticacion.dto.RespuestaTokenDto;
import com.unisimon.gestor.autenticacion.dto.SolicitudLoginDto;
import com.unisimon.gestor.autenticacion.service.AutenticacionServicio;
import com.unisimon.gestor.shared.dto.RespuestaDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de autenticación.
 * Expone el endpoint de login público del sistema.
 *
 * Controlador deliberadamente delgado: recibe, valida con @Valid
 * y delega al servicio. Sin lógica de negocio aquí.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AutenticacionControlador {

    private final AutenticacionServicio autenticacionServicio;

    /**
     * POST /api/v1/auth/login
     *
     * Cuerpo esperado:
     * {
     * "correo": "admin@unisimon.edu.co",
     * "contrasena": "Admin2025!"
     * }
     *
     * Respuesta exitosa (200):
     * {
     * "exitoso": true,
     * "mensaje": "Login exitoso",
     * "data": {
     * "token": "eyJ...",
     * "tipo": "Bearer",
     * "correo": "admin@unisimon.edu.co",
     * "roles": "ROLE_ADMINISTRADOR"
     * }
     * }
     */
    @PostMapping("/login")
    public ResponseEntity<RespuestaDto<RespuestaTokenDto>> login(
            @Valid @RequestBody SolicitudLoginDto solicitud) {

        RespuestaTokenDto respuesta = autenticacionServicio.login(solicitud);

        return ResponseEntity.ok(
                RespuestaDto.exito("Login exitoso", respuesta));
    }
}