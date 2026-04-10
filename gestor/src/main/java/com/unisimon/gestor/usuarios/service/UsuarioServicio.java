package com.unisimon.gestor.usuarios.service;

import com.unisimon.gestor.shared.exception.ExcepcionNoEncontrado;
import com.unisimon.gestor.usuarios.domain.Usuario;
import com.unisimon.gestor.usuarios.dto.UsuarioDto;
import com.unisimon.gestor.usuarios.mapper.UsuarioMapper;
import com.unisimon.gestor.usuarios.repository.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Servicio de usuarios.
 *
 * Contiene la lógica de negocio del módulo. Los controladores
 * son deliberadamente delgados — toda decisión vive aquí.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioMapper usuarioMapper;

    /**
     * Retorna el perfil del usuario actualmente autenticado.
     *
     * Extrae el correo del SecurityContext — Spring Security lo
     * colocó ahí cuando FiltroJwt validó el JWT del request.
     * Así nunca exponemos datos de otro usuario por error.
     */
    @Transactional(readOnly = true)
    public UsuarioDto obtenerPerfilActual() {
        String correo = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Usuario usuario = usuarioRepositorio
                .findByCorreoConRoles(correo)
                .orElseThrow(() -> new ExcepcionNoEncontrado(
                        "Usuario autenticado no encontrado: " + correo));

        return usuarioMapper.toDto(usuario);
    }

    /**
     * Busca un usuario por su UUID.
     * Solo accesible por administradores — la restricción
     * se aplica con @PreAuthorize en el controlador.
     */
    @Transactional(readOnly = true)
    public UsuarioDto buscarPorId(UUID usuarioId) {
        Usuario usuario = usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() -> new ExcepcionNoEncontrado(
                        "Usuario no encontrado con id: " + usuarioId));

        return usuarioMapper.toDto(usuario);
    }
}