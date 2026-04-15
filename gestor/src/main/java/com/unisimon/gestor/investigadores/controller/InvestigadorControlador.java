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

    /**
     * GET /api/v1/investigadores/{id}/perfil
     * Retorna el perfil completo del investigador con sus vinculaciones vigentes.
     */
    @GetMapping("/{id}/perfil")
    public ResponseEntity<RespuestaDto<PerfilInvestigadorDto>> obtenerPerfil(
            @PathVariable UUID id) {
        return ResponseEntity.ok(
                RespuestaDto.exito("Perfil del investigador obtenido correctamente",
                        investigadorServicio.obtenerPerfil(id)));
    }

    /**
     * GET /api/v1/investigadores/centros
     * Lista todos los centros de investigación activos.
     */
    @GetMapping("/centros")
    public ResponseEntity<RespuestaDto<List<CentroInvestigacionDto>>> listarCentros() {
        return ResponseEntity.ok(
                RespuestaDto.exito("Centros de investigación obtenidos correctamente",
                        investigadorServicio.listarCentros()));
    }

    /**
     * GET /api/v1/investigadores/grupos
     * Lista todos los grupos de investigación activos.
     */
    @GetMapping("/grupos")
    public ResponseEntity<RespuestaDto<List<GrupoInvestigacionDto>>> listarGrupos() {
        return ResponseEntity.ok(
                RespuestaDto.exito("Grupos de investigación obtenidos correctamente",
                        investigadorServicio.listarGrupos()));
    }
}