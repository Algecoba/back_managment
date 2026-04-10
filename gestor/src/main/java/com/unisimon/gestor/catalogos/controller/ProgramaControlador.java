package com.unisimon.gestor.catalogos.controller;

import com.unisimon.gestor.catalogos.dto.ProgramaDto;
import com.unisimon.gestor.catalogos.service.ProgramaServicio;
import com.unisimon.gestor.shared.dto.RespuestaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/programas")
@RequiredArgsConstructor
public class ProgramaControlador {

    private final ProgramaServicio programaServicio;

    @GetMapping
    public ResponseEntity<RespuestaDto<List<ProgramaDto>>> listar() {
        return ResponseEntity.ok(
                RespuestaDto.exito("Programas obtenidos correctamente",
                        programaServicio.listarActivos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaDto<ProgramaDto>> buscarPorId(
            @PathVariable UUID id) {
        return ResponseEntity.ok(
                RespuestaDto.exito("Programa encontrado",
                        programaServicio.buscarPorId(id)));
    }

    @GetMapping("/por-facultad/{facultadId}")
    public ResponseEntity<RespuestaDto<List<ProgramaDto>>> listarPorFacultad(
            @PathVariable UUID facultadId) {
        return ResponseEntity.ok(
                RespuestaDto.exito("Programas de la facultad obtenidos correctamente",
                        programaServicio.listarPorFacultad(facultadId)));
    }
}