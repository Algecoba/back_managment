package com.unisimon.gestor.usuarios.mapper;

import com.unisimon.gestor.usuarios.domain.Rol;
import com.unisimon.gestor.usuarios.domain.Usuario;
import com.unisimon.gestor.usuarios.dto.RolDto;
import com.unisimon.gestor.usuarios.dto.UsuarioDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper manual entre entidades y DTOs del modulo usuarios.
 * Expone siempre el UUID al frontend, nunca el id interno numerico.
 */
@Component
public class UsuarioMapper {

    public UsuarioDto toDto(Usuario usuario) {
        List<RolDto> roles = usuario.getUsuarioRoles().stream()
                .map(ur -> toRolDto(ur.getRol()))
                .toList();

        return UsuarioDto.builder()
                .usuarioId(usuario.getUuid()) // UUID externo, no el id Long
                .correo(usuario.getCorreo())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .tipoDoc(usuario.getTipoDocumento()) // campo renombrado
                .numDoc(usuario.getNumeroDocumento()) // campo renombrado
                .activo(usuario.isEsActivo()) // campo renombrado
                .roles(roles)
                .build();
    }

    public RolDto toRolDto(Rol rol) {
        return RolDto.builder()
                .rolId(rol.getUuid()) // UUID externo
                .codigo(rol.getCodigo())
                .nombre(rol.getNombre())
                .categoria(rol.getCategoria())
                .build();
    }
}