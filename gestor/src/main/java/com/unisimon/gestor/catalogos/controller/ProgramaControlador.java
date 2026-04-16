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
        return ResponseEntity.ok(RespuestaDto.exito(
            "Programas obtenidos correctamente", programaServicio.listarActivos()));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<RespuestaDto<ProgramaDto>> buscarPorUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(RespuestaDto.exito(
            "Programa encontrado", programaServicio.buscarPorUuid(uuid)));
    }

    @GetMapping("/por-facultad/{facultadUuid}")
    public ResponseEntity<RespuestaDto<List<ProgramaDto>>> listarPorFacultad(
            @PathVariable UUID facultadUuid) {
        return ResponseEntity.ok(RespuestaDto.exito(
            "Programas de la facultad obtenidos correctamente",
            programaServicio.listarPorFacultad(facultadUuid)));
    }
}
