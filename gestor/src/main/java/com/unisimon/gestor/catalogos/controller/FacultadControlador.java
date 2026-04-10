package com.unisimon.gestor.catalogos.controller;

import com.unisimon.gestor.catalogos.dto.FacultadDto;
import com.unisimon.gestor.catalogos.service.FacultadServicio;
import com.unisimon.gestor.shared.dto.RespuestaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/facultades")
@RequiredArgsConstructor
public class FacultadControlador {

    private final FacultadServicio facultadServicio;

    @GetMapping
    public ResponseEntity<RespuestaDto<List<FacultadDto>>> listar() {
        return ResponseEntity.ok(
                RespuestaDto.exito("Facultades obtenidas correctamente",
                        facultadServicio.listarActivas()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaDto<FacultadDto>> buscarPorId(
            @PathVariable UUID id) {
        return ResponseEntity.ok(
                RespuestaDto.exito("Facultad encontrada",
                        facultadServicio.buscarPorId(id)));
    }

    @GetMapping("/por-sede/{sedeId}")
    public ResponseEntity<RespuestaDto<List<FacultadDto>>> listarPorSede(
            @PathVariable UUID sedeId) {
        return ResponseEntity.ok(
                RespuestaDto.exito("Facultades de la sede obtenidas correctamente",
                        facultadServicio.listarPorSede(sedeId)));
    }
}