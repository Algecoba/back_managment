package com.unisimon.gestor.shared.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Clase base de auditoria para todas las entidades del sistema.
 *
 * Cumple el estandar institucional de Gestion y Analitica de Datos v1.1:
 * - fecha_creacion: timestamp de creacion del registro
 * - usuario_creacion: usuario que creo el registro (correo institucional)
 * - fecha_actualizacion: timestamp de ultima modificacion
 * - usuario_actualizacion: usuario de ultima modificacion
 * - es_activo: indicador de registro activo (soft delete)
 *
 * @MappedSuperclass indica a JPA que esta clase no tiene tabla propia
 *                   pero sus campos se mapean a la tabla de cada subclase.
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class EntidadAuditable {

    @CreatedDate
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @LastModifiedDate
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    // Correo institucional del usuario que creo el registro.
    // updatable = false: no puede cambiar quien creo el registro.
    @CreatedBy
    @Column(name = "usuario_creacion", updatable = false, length = 100)
    private String usuarioCreacion;

    @LastModifiedBy
    @Column(name = "usuario_actualizacion", length = 100)
    private String usuarioActualizacion;

    // Soft delete — nunca eliminamos registros fisicamente
    @Column(name = "es_activo", nullable = false)
    private boolean esActivo = true;
}