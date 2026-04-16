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
        return ResponseEntity.ok(RespuestaDto.exito(
            "Facultades obtenidas correctamente", facultadServicio.listarActivas()));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<RespuestaDto<FacultadDto>> buscarPorUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(RespuestaDto.exito(
            "Facultad encontrada", facultadServicio.buscarPorUuid(uuid)));
    }

    @GetMapping("/por-sede/{sedeUuid}")
    public ResponseEntity<RespuestaDto<List<FacultadDto>>> listarPorSede(
            @PathVariable UUID sedeUuid) {
        return ResponseEntity.ok(RespuestaDto.exito(
            "Facultades de la sede obtenidas correctamente",
            facultadServicio.listarPorSede(sedeUuid)));
    }
}
