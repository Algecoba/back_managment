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
 * Cumple estandar institucional de Gestion y Analitica de Datos v1.1.
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

    @CreatedBy
    @Column(name = "usuario_creacion", updatable = false, length = 100)
    private String usuarioCreacion;

    @LastModifiedBy
    @Column(name = "usuario_actualizacion", length = 100)
    private String usuarioActualizacion;

    @Column(name = "es_activo", nullable = false)
    private boolean esActivo = true;
}
