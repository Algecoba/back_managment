package com.unisimon.gestor.investigadores.service;

import com.unisimon.gestor.investigadores.domain.*;
import com.unisimon.gestor.investigadores.dto.*;
import com.unisimon.gestor.investigadores.repository.*;
import com.unisimon.gestor.shared.exception.ExcepcionNoEncontrado;
import com.unisimon.gestor.usuarios.domain.Usuario;
import com.unisimon.gestor.usuarios.repository.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Servicio del modulo investigadores.
 * Usa UUID para referencias externas, Long internamente.
 */
@Service
@RequiredArgsConstructor
public class InvestigadorServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PerfilInvestigadorRepositorio perfilRepositorio;
    private final CentroInvestigacionRepositorio centroRepositorio;
    private final GrupoInvestigacionRepositorio grupoRepositorio;
    private final InvestigadorProgramaRepositorio invProgramaRepositorio;
    private final InvestigadorCentroRepositorio invCentroRepositorio;
    private final InvestigadorGrupoRepositorio invGrupoRepositorio;

    @Transactional(readOnly = true)
    public PerfilInvestigadorDto obtenerPerfil(UUID usuarioUuid) {
        Usuario usuario = usuarioRepositorio.findByUuid(usuarioUuid)
            .orElseThrow(() -> new ExcepcionNoEncontrado(
                "Investigador no encontrado con uuid: " + usuarioUuid));

        PerfilInvestigador perfil = perfilRepositorio
            .findById(usuario.getId()).orElse(null);

        List<VinculacionDto> programas = invProgramaRepositorio
            .findVigentesByUsuarioId(usuario.getId())
            .stream()
            .map(ip -> VinculacionDto.builder()
                .entidadId(ip.getId().getProgramaId())
                .esPrincipal(ip.isEsPrincipal())
                .activoDesde(ip.getId().getFechaInicio())
                .activoHasta(ip.getFechaFin())
                .vigente(ip.getFechaFin() == null)
                .build())
            .toList();

        List<VinculacionDto> centros = invCentroRepositorio
            .findVigentesByUsuarioId(usuario.getId())
            .stream()
            .map(ic -> VinculacionDto.builder()
                .entidadId(ic.getId().getCentroInvestigacionId())
                .esPrincipal(ic.isEsPrincipal())
                .activoDesde(ic.getId().getFechaInicio())
                .activoHasta(ic.getFechaFin())
                .vigente(ic.getFechaFin() == null)
                .build())
            .toList();

        List<VinculacionDto> grupos = invGrupoRepositorio
            .findVigentesByUsuarioId(usuario.getId())
            .stream()
            .map(ig -> VinculacionDto.builder()
                .entidadId(ig.getId().getGrupoInvestigacionId())
                .esPrincipal(ig.isEsPrincipal())
                .activoDesde(ig.getId().getFechaInicio())
                .activoHasta(ig.getFechaFin())
                .vigente(ig.getFechaFin() == null)
                .build())
            .toList();

        return PerfilInvestigadorDto.builder()
            .usuarioId(usuario.getUuid())
            .nombres(usuario.getNombres())
            .apellidos(usuario.getApellidos())
            .correo(usuario.getCorreo())
            .sedePredeterminadaId(perfil != null ? perfil.getSedePredeterminadaId() : null)
            .programas(programas)
            .centros(centros)
            .grupos(grupos)
            .build();
    }

    @Transactional(readOnly = true)
    public List<CentroInvestigacionDto> listarCentros() {
        return centroRepositorio.findByEsActivoTrue()
            .stream()
            .map(c -> CentroInvestigacionDto.builder()
                .centroId(c.getUuid())
                .nombre(c.getNombre())
                .acronimo(c.getAcronimo())
                .categoria(c.getCategoria())
                .correoContacto(c.getCorreoContacto())
                .activo(c.isEsActivo())
                .build())
            .toList();
    }

    @Transactional(readOnly = true)
    public List<GrupoInvestigacionDto> listarGrupos() {
        return grupoRepositorio.findByEsActivoTrue()
            .stream()
            .map(g -> GrupoInvestigacionDto.builder()
                .grupoId(g.getUuid())
                .nombre(g.getNombre())
                .activo(g.isEsActivo())
                .centroId(g.getCentro().getUuid())
                .nombreCentro(g.getCentro().getNombre())
                .build())
            .toList();
    }
}
