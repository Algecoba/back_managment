package com.unisimon.gestor.usuarios.domain;

import com.unisimon.gestor.shared.domain.EntidadAuditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Tabla: rol
 * Catalogo de roles del sistema.
 * Un rol tiene una categoria: INVESTIGADOR o ADMINISTRADOR.
 * Regla de negocio: un usuario puede tener maximo 1 rol de
 * categoria ADMINISTRADOR.
 */
@Getter
@Setter
@Entity
@Table(name = "rol")
public class Rol extends EntidadAuditable {

    // PK interna — solo para uso de JPA y joins en BD
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    // Identificador externo — usado en endpoints REST y JWT
    @Column(name = "uuid", nullable = false, unique = true, updatable = false, columnDefinition = "UNIQUEIDENTIFIER DEFAULT NEWID()")
    private UUID uuid;

    @Column(name = "codigo", nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    // INVESTIGADOR | ADMINISTRADOR
    @Column(name = "categoria", nullable = false, length = 20)
    private String categoria;

    @PrePersist
    protected void antesDeGuardar() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }
}