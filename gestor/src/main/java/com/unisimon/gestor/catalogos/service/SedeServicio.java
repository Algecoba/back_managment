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
        return sedeRepositorio.findByActivoTrue()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public SedeDto buscarPorId(UUID sedeId) {
        Sede sede = sedeRepositorio.findById(sedeId)
                .orElseThrow(() -> new ExcepcionNoEncontrado(
                        "Sede no encontrada con id: " + sedeId));
        return toDto(sede);
    }

    private SedeDto toDto(Sede sede) {
        return SedeDto.builder()
                .sedeId(sede.getSedeId())
                .codigo(sede.getCodigo())
                .nombre(sede.getNombre())
                .activo(sede.isActivo())
                .build();
    }
}