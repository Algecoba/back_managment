package com.unisimon.gestor.catalogos.controller;

import com.unisimon.gestor.catalogos.dto.SedeDto;
import com.unisimon.gestor.catalogos.service.SedeServicio;
import com.unisimon.gestor.shared.dto.RespuestaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sedes")
@RequiredArgsConstructor
public class SedeControlador {

    private final SedeServicio sedeServicio;

    @GetMapping
    public ResponseEntity<RespuestaDto<List<SedeDto>>> listar() {
        return ResponseEntity.ok(RespuestaDto.exito(
            "Sedes obtenidas correctamente", sedeServicio.listarActivas()));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<RespuestaDto<SedeDto>> buscarPorUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(RespuestaDto.exito(
            "Sede encontrada", sedeServicio.buscarPorUuid(uuid)));
    }
}
