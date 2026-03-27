package com.unisimon.gestor.shared.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Envelope para respuestas paginadas.
 *
 * Se construye a partir de un Page<T> de Spring Data para no
 * exponer la clase Page directamente al frontend. Esto desacopla
 * el contrato de la API de la implementación interna de JPA.
 */
@Getter
public class PaginacionDto<T> {

    private final List<T> contenido;
    private final int paginaActual;
    private final int totalPaginas;
    private final long totalElementos;
    private final boolean esUltimaPagina;

    // Se construye desde un Page de Spring — nunca manualmente
    private PaginacionDto(Page<T> pagina) {
        this.contenido = pagina.getContent();
        this.paginaActual = pagina.getNumber();
        this.totalPaginas = pagina.getTotalPages();
        this.totalElementos = pagina.getTotalElements();
        this.esUltimaPagina = pagina.isLast();
    }

    public static <T> PaginacionDto<T> desde(Page<T> pagina) {
        return new PaginacionDto<>(pagina);
    }
}