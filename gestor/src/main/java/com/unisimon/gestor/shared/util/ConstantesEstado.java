package com.unisimon.gestor.shared.util;

/**
 * Códigos de estado de las solicitudes.
 *
 * Estas constantes corresponden exactamente con los valores
 * almacenados en la tabla estados_solicitud (columna 'codigo').
 * Usar siempre estas constantes en lugar de strings literales
 * para evitar errores de tipeo y facilitar refactorizaciones.
 */
public final class ConstantesEstado {

    // Constructor privado — clase de utilidad, no se instancia
    private ConstantesEstado() {
    }

    // ── Estados generales ────────────────────────────────────────
    public static final String BORRADOR = "BORRADOR";
    public static final String ENVIADA = "ENVIADA";
    public static final String EN_REVISION = "EN_REVISION";
    public static final String DEVUELTA = "DEVUELTA";
    public static final String APROBADA = "APROBADA";
    public static final String RECHAZADA = "RECHAZADA";
    public static final String CERRADA = "CERRADA";

    // ── Estados específicos APC ──────────────────────────────────
    public static final String APROBADA_PROFESIONAL = "APROBADA_PROFESIONAL";
    public static final String APROBADA_GESTOR = "APROBADA_GESTOR";
    public static final String APROBADA_DIRECTOR = "APROBADA_DIRECTOR";
    public static final String APROBADA_VICERRECTOR = "APROBADA_VICERRECTOR";
    public static final String PENDIENTE_PAGO = "PENDIENTE_PAGO";
    public static final String PAGADA = "PAGADA";

    // ── Estados específicos Reporte ──────────────────────────────
    public static final String RECIBIDA = "RECIBIDA";
    public static final String VALIDADA = "VALIDADA";
    public static final String REPORTADA = "REPORTADA";
}