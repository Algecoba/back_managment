package com.unisimon.gestor.usuarios.controller;

import com.unisimon.gestor.shared.dto.RespuestaDto;
import com.unisimon.gestor.usuarios.dto.UsuarioDto;
import com.unisimon.gestor.usuarios.service.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controlador de usuarios.
 * Delgado por diseño: recibe, delega al servicio y retorna.
 */
@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    /**
     * GET /api/v1/usuarios/me
     *
     * Retorna el perfil del usuario autenticado.
     * Cualquier usuario autenticado puede acceder a su propio perfil.
     */
    @GetMapping("/me")
    public ResponseEntity<RespuestaDto<UsuarioDto>> obtenerPerfilActual() {
        return ResponseEntity.ok(
                RespuestaDto.exito("Perfil obtenido correctamente",
                        usuarioServicio.obtenerPerfilActual()));
    }

    /**
     * GET /api/v1/usuarios/{id}
     *
     * Busca un usuario por UUID.
     * Restringido a administradores con @PreAuthorize.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'PROF_PUBLICACIONES')")
    public ResponseEntity<RespuestaDto<UsuarioDto>> buscarPorId(
            @PathVariable UUID id) {
        return ResponseEntity.ok(
                RespuestaDto.exito("Usuario encontrado",
                        usuarioServicio.buscarPorId(id)));
    }
}