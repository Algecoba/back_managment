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
 * Controlador de usuarios. Todos los IDs expuestos son UUID.
 */
@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    @GetMapping("/me")
    public ResponseEntity<RespuestaDto<UsuarioDto>> obtenerPerfilActual() {
        return ResponseEntity.ok(
            RespuestaDto.exito("Perfil obtenido correctamente",
                usuarioServicio.obtenerPerfilActual()));
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'PROF_PUBLICACIONES')")
    public ResponseEntity<RespuestaDto<UsuarioDto>> buscarPorUuid(
            @PathVariable UUID uuid) {
        return ResponseEntity.ok(
            RespuestaDto.exito("Usuario encontrado",
                usuarioServicio.buscarPorUuid(uuid)));
    }
}
