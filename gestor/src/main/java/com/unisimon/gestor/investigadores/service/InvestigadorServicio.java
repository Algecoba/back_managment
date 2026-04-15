package com.unisimon.gestor.investigadores.service;

import com.unisimon.gestor.investigadores.domain.*;
import com.unisimon.gestor.investigadores.dto.*;
import com.unisimon.gestor.investigadores.repository.*;
import com.unisimon.gestor.shared.exception.ExcepcionNoEncontrado;
import com.unisimon.gestor.usuarios.repository.UsuarioRepositorio;
import com.unisimon.gestor.usuarios.domain.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Servicio del módulo investigadores.
 *
 * Consulta datos de usuarios desde UsuarioRepositorio — esto es
 * una dependencia válida hacia el módulo usuarios porque investigadores
 * depende de usuarios en el grafo de dependencias del sistema.
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
    public PerfilInvestigadorDto obtenerPerfil(UUID usuarioId) {
        Usuario usuario = usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() -> new ExcepcionNoEncontrado(
                        "Investigador no encontrado con id: " + usuarioId));

        PerfilInvestigador perfil = perfilRepositorio
                .findById(usuarioId)
                .orElse(null);

        List<VinculacionDto> programas = invProgramaRepositorio
                .findVigentesByUsuarioId(usuarioId)
                .stream()
                .map(ip -> VinculacionDto.builder()
                        .entidadId(ip.getId().getProgramaId())
                        .esPrincipal(ip.isEsPrincipal())
                        .activoDesde(ip.getId().getActivoDesde())
                        .activoHasta(ip.getActivoHasta())
                        .vigente(ip.getActivoHasta() == null)
                        .build())
                .toList();

        List<VinculacionDto> centros = invCentroRepositorio
                .findVigentesByUsuarioId(usuarioId)
                .stream()
                .map(ic -> VinculacionDto.builder()
                        .entidadId(ic.getId().getCentroId())
                        .esPrincipal(ic.isEsPrincipal())
                        .activoDesde(ic.getId().getActivoDesde())
                        .activoHasta(ic.getActivoHasta())
                        .vigente(ic.getActivoHasta() == null)
                        .build())
                .toList();

        List<VinculacionDto> grupos = invGrupoRepositorio
                .findVigentesByUsuarioId(usuarioId)
                .stream()
                .map(ig -> VinculacionDto.builder()
                        .entidadId(ig.getId().getGrupoId())
                        .esPrincipal(ig.isEsPrincipal())
                        .activoDesde(ig.getId().getActivoDesde())
                        .activoHasta(ig.getActivoHasta())
                        .vigente(ig.getActivoHasta() == null)
                        .build())
                .toList();

        return PerfilInvestigadorDto.builder()
                .usuarioId(usuario.getUsuarioId())
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
        return centroRepositorio.findByActivoTrue()
                .stream()
                .map(c -> CentroInvestigacionDto.builder()
                        .centroId(c.getCentroId())
                        .nombre(c.getNombre())
                        .acronimo(c.getAcronimo())
                        .categoria(c.getCategoria())
                        .correoContacto(c.getCorreoContacto())
                        .activo(c.isActivo())
                        .build())
                .toList();
    }

    @Transactional(readOnly = true)
    public List<GrupoInvestigacionDto> listarGrupos() {
        return grupoRepositorio.findByActivoTrue()
                .stream()
                .map(g -> GrupoInvestigacionDto.builder()
                        .grupoId(g.getGrupoId())
                        .nombre(g.getNombre())
                        .activo(g.isActivo())
                        .centroId(g.getCentro().getCentroId())
                        .nombreCentro(g.getCentro().getNombre())
                        .build())
                .toList();
    }
}