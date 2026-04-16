package com.unisimon.gestor.investigadores.controller;

import com.unisimon.gestor.investigadores.dto.*;
import com.unisimon.gestor.investigadores.service.InvestigadorServicio;
import com.unisimon.gestor.shared.dto.RespuestaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/investigadores")
@RequiredArgsConstructor
public class InvestigadorControlador {

    private final InvestigadorServicio investigadorServicio;

    @GetMapping("/{uuid}/perfil")
    public ResponseEntity<RespuestaDto<PerfilInvestigadorDto>> obtenerPerfil(
            @PathVariable UUID uuid) {
        return ResponseEntity.ok(RespuestaDto.exito(
            "Perfil del investigador obtenido correctamente",
            investigadorServicio.obtenerPerfil(uuid)));
    }

    @GetMapping("/centros")
    public ResponseEntity<RespuestaDto<List<CentroInvestigacionDto>>> listarCentros() {
        return ResponseEntity.ok(RespuestaDto.exito(
            "Centros de investigacion obtenidos correctamente",
            investigadorServicio.listarCentros()));
    }

    @GetMapping("/grupos")
    public ResponseEntity<RespuestaDto<List<GrupoInvestigacionDto>>> listarGrupos() {
        return ResponseEntity.ok(RespuestaDto.exito(
            "Grupos de investigacion obtenidos correctamente",
            investigadorServicio.listarGrupos()));
    }
}
