package com.unisimon.gestor.catalogos.service;

import com.unisimon.gestor.catalogos.domain.Sede;
import com.unisimon.gestor.catalogos.dto.SedeDto;
import com.unisimon.gestor.catalogos.repository.SedeRepositorio;
import com.unisimon.gestor.shared.exception.ExcepcionNoEncontrado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SedeServicio {

    private final SedeRepositorio sedeRepositorio;

    @Transactional(readOnly = true)
    public List<SedeDto> listarActivas() {
        return sedeRepositorio.findByEsActivoTrue()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public SedeDto buscarPorUuid(UUID uuid) {
        Sede sede = sedeRepositorio.findByUuid(uuid)
                .orElseThrow(() -> new ExcepcionNoEncontrado(
                        "Sede no encontrada con uuid: " + uuid));
        return toDto(sede);
    }

    private SedeDto toDto(Sede sede) {
        return SedeDto.builder()
                .sedeId(sede.getUuid()) // exponemos UUID, no el id Long
                .codigo(sede.getCodigo())
                .nombre(sede.getNombre())
                .activo(sede.isEsActivo()) // campo renombrado
                .build();
    }
}