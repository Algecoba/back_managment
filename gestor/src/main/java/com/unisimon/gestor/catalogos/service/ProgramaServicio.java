package com.unisimon.gestor.catalogos.service;

import com.unisimon.gestor.catalogos.domain.Programa;
import com.unisimon.gestor.catalogos.dto.ProgramaDto;
import com.unisimon.gestor.catalogos.repository.ProgramaRepositorio;
import com.unisimon.gestor.shared.exception.ExcepcionNoEncontrado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProgramaServicio {

    private final ProgramaRepositorio programaRepositorio;

    @Transactional(readOnly = true)
    public List<ProgramaDto> listarActivos() {
        return programaRepositorio.findByActivoTrue()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProgramaDto> listarPorFacultad(UUID facultadId) {
        return programaRepositorio.findByFacultadFacultadIdAndActivoTrue(facultadId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProgramaDto buscarPorId(UUID programaId) {
        Programa programa = programaRepositorio.findById(programaId)
                .orElseThrow(() -> new ExcepcionNoEncontrado(
                        "Programa no encontrado con id: " + programaId));
        return toDto(programa);
    }

    private ProgramaDto toDto(Programa programa) {
        return ProgramaDto.builder()
                .programaId(programa.getProgramaId())
                .codigo(programa.getCodigo())
                .nombre(programa.getNombre())
                .activo(programa.isActivo())
                .facultadId(programa.getFacultad().getFacultadId())
                .nombreFacultad(programa.getFacultad().getNombre())
                .build();
    }
}