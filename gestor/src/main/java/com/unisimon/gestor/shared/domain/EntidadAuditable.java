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
import java.util.UUID;

/**
 * Clase base de auditoría para todas las entidades del sistema.
 *
 * Cualquier entidad que extienda esta clase hereda automáticamente
 * los cuatro campos de auditoría. Spring Data JPA los rellena solo
 * usando el AuditorAware configurado en AuditoriaConfig.
 *
 * Campos heredados:
 * - creadoEn: timestamp de creación del registro
 * - actualizadoEn: timestamp de última modificación
 * - creadoPor: UUID del usuario que creó el registro
 * - actualizadoPor: UUID del usuario que hizo la última modificación
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
    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn;

    @LastModifiedDate
    @Column(name = "actualizado_en", nullable = false)
    private LocalDateTime actualizadoEn;

    // UUID del usuario que creó el registro.
    // updatable = false: una vez creado, no se puede cambiar quién lo creó.
    @CreatedBy
    @Column(name = "creado_por", updatable = false)
    private UUID creadoPor;

    @LastModifiedBy
    @Column(name = "actualizado_por")
    private UUID actualizadoPor;
}