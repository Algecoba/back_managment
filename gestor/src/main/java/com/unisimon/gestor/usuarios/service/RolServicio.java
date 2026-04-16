package com.unisimon.gestor.usuarios.service;

import com.unisimon.gestor.shared.exception.ExcepcionNoEncontrado;
import com.unisimon.gestor.usuarios.domain.Rol;
import com.unisimon.gestor.usuarios.dto.RolDto;
import com.unisimon.gestor.usuarios.repository.RolRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Servicio de roles. Gestiona el catalogo de roles del sistema.
 */
@Service
@RequiredArgsConstructor
public class RolServicio {

    private final RolRepositorio rolRepositorio;

    @Transactional(readOnly = true)
    public List<RolDto> listarTodos() {
        return rolRepositorio.findAll()
            .stream()
            .map(this::toDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public RolDto buscarPorUuid(UUID uuid) {
        Rol rol = rolRepositorio.findByUuid(uuid)
            .orElseThrow(() -> new ExcepcionNoEncontrado(
                "Rol no encontrado con uuid: " + uuid));
        return toDto(rol);
    }

    private RolDto toDto(Rol rol) {
        return RolDto.builder()
            .rolId(rol.getUuid())
            .codigo(rol.getCodigo())
            .nombre(rol.getNombre())
            .categoria(rol.getCategoria())
            .build();
    }
}
