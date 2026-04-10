package com.unisimon.gestor.usuarios.domain;

import com.unisimon.gestor.shared.domain.EntidadAuditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Catálogo de roles del sistema.
 *
 * Un rol tiene una categoría: INVESTIGADOR o ADMINISTRADOR.
 * Regla de negocio: un usuario puede tener máximo 1 rol de
 * categoría ADMINISTRADOR. Esta restricción se valida en
 * UsuarioServicio, no en base de datos, para dar mensajes claros.
 */
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Rol extends EntidadAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "rol_id", updatable = false, nullable = false)
    private UUID rolId;

    // Código único legible: INVESTIGADOR, PROF_PUBLICACIONES, etc.
    @Column(name = "codigo", nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    // INVESTIGADOR | ADMINISTRADOR
    @Column(name = "categoria", nullable = false, length = 20)
    private String categoria;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;
}