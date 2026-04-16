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
        return programaRepositorio.findByEsActivoTrue()
            .stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<ProgramaDto> listarPorFacultad(UUID facultadUuid) {
        return programaRepositorio.findByFacultadUuidAndEsActivoTrue(facultadUuid)
            .stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public ProgramaDto buscarPorUuid(UUID uuid) {
        Programa programa = programaRepositorio.findByUuid(uuid)
            .orElseThrow(() -> new ExcepcionNoEncontrado(
                "Programa no encontrado con uuid: " + uuid));
        return toDto(programa);
    }

    private ProgramaDto toDto(Programa programa) {
        return ProgramaDto.builder()
            .programaId(programa.getUuid())
            .codigo(programa.getCodigo())
            .nombre(programa.getNombre())
            .activo(programa.isEsActivo())
            .facultadId(programa.getFacultad().getUuid())
            .nombreFacultad(programa.getFacultad().getNombre())
            .build();
    }
}
