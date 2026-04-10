package com.unisimon.gestor.usuarios.mapper;

import com.unisimon.gestor.usuarios.domain.Rol;
import com.unisimon.gestor.usuarios.domain.Usuario;
import com.unisimon.gestor.usuarios.dto.RolDto;
import com.unisimon.gestor.usuarios.dto.UsuarioDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper manual entre entidades y DTOs del módulo usuarios.
 *
 * Usamos mapeo manual en lugar de MapStruct para tener control
 * explícito sobre qué campos se exponen. Cuando el modelo crezca
 * y el mapeo se vuelva repetitivo, se puede migrar a MapStruct
 * sin cambiar los contratos de los servicios.
 */
@Component
public class UsuarioMapper {

    public UsuarioDto toDto(Usuario usuario) {
        List<RolDto> roles = usuario.getUsuarioRoles().stream()
                .map(ur -> toRolDto(ur.getRol()))
                .toList();

        return UsuarioDto.builder()
                .usuarioId(usuario.getUsuarioId())
                .correo(usuario.getCorreo())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .tipoDoc(usuario.getTipoDoc())
                .numDoc(usuario.getNumDoc())
                .activo(usuario.isActivo())
                .roles(roles)
                .build();
    }

    public RolDto toRolDto(Rol rol) {
        return RolDto.builder()
                .rolId(rol.getRolId())
                .codigo(rol.getCodigo())
                .nombre(rol.getNombre())
                .categoria(rol.getCategoria())
                .build();
    }
}