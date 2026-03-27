package com.unisimon.gestor.shared.util;

/**
 * Constantes generales del sistema.
 * Valores fijos que no cambian entre ambientes.
 */
public final class Constantes {

    private Constantes() {
    }

    // ── API ──────────────────────────────────────────────────────
    public static final String API_BASE = "/api/v1";
    public static final String API_AUTH = API_BASE + "/auth";
    public static final String API_USUARIOS = API_BASE + "/usuarios";
    public static final String API_SOLICITUDES = API_BASE + "/solicitudes";

    // ── Paginación por defecto ───────────────────────────────────
    public static final int PAGINA_DEFAULT = 0;
    public static final int TAMANO_DEFAULT = 20;
    public static final int TAMANO_MAXIMO = 100;
}