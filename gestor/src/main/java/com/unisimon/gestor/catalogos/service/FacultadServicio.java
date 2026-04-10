package com.unisimon.gestor.catalogos.service;

import com.unisimon.gestor.catalogos.domain.Facultad;
import com.unisimon.gestor.catalogos.dto.FacultadDto;
import com.unisimon.gestor.catalogos.repository.FacultadRepositorio;
import com.unisimon.gestor.shared.exception.ExcepcionNoEncontrado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FacultadServicio {

    private final FacultadRepositorio facultadRepositorio;

    @Transactional(readOnly = true)
    public List<FacultadDto> listarActivas() {
        return facultadRepositorio.findByActivoTrue()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FacultadDto> listarPorSede(UUID sedeId) {
        return facultadRepositorio.findBySedeSedeIdAndActivoTrue(sedeId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public FacultadDto buscarPorId(UUID facultadId) {
        Facultad facultad = facultadRepositorio.findById(facultadId)
                .orElseThrow(() -> new ExcepcionNoEncontrado(
                        "Facultad no encontrada con id: " + facultadId));
        return toDto(facultad);
    }

    private FacultadDto toDto(Facultad facultad) {
        return FacultadDto.builder()
                .facultadId(facultad.getFacultadId())
                .codigo(facultad.getCodigo())
                .nombre(facultad.getNombre())
                .activo(facultad.isActivo())
                .sedeId(facultad.getSede().getSedeId())
                .nombreSede(facultad.getSede().getNombre())
                .build();
    }
}