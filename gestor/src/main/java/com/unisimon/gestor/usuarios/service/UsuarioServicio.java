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
 * Recibe y expone UUID al exterior.
 * Internamente JPA usa Long como PK.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioMapper usuarioMapper;

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

    @Transactional(readOnly = true)
    public UsuarioDto buscarPorUuid(UUID uuid) {
        Usuario usuario = usuarioRepositorio.findByUuid(uuid)
            .orElseThrow(() -> new ExcepcionNoEncontrado(
                "Usuario no encontrado con uuid: " + uuid));

        return usuarioMapper.toDto(usuario);
    }
}
