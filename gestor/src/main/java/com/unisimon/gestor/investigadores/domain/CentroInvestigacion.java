package com.unisimon.gestor.investigadores.domain;

import com.unisimon.gestor.shared.domain.EntidadAuditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Centro de investigación institucional.
 *
 * Un centro agrupa varios grupos de investigación.
 * El líder del centro es un usuario del sistema (nullable —
 * puede no tener líder asignado en un momento dado).
 */
@Getter
@Setter
@Entity
@Table(name = "centros_investigacion")
public class CentroInvestigacion extends EntidadAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "centro_id", updatable = false, nullable = false)
    private UUID centroId;

    @Column(name = "nombre", nullable = false, unique = true, length = 200)
    private String nombre;

    @Column(name = "acronimo", unique = true, length = 50)
    private String acronimo;

    @Column(name = "categoria", length = 50)
    private String categoria;

    // UUID del líder actual — referencia simple para evitar
    // dependencia circular con el módulo usuarios
    @Column(name = "lider_usuario_id")
    private UUID liderUsuarioId;

    @Column(name = "correo_contacto", length = 255)
    private String correoContacto;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;
}