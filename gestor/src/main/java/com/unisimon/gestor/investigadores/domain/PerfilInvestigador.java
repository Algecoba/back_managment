package com.unisimon.gestor.investigadores.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Perfil extendido del investigador — relación 1:1 con usuarios.
 *
 * Contiene preferencias y configuraciones específicas del investigador.
 * Usa el mismo usuario_id como PK (clave compartida con usuarios).
 *
 * No extiende EntidadAuditable porque su auditoría es parcial:
 * solo tiene actualizado_en y actualizado_por, sin creado_en ni creado_por
 * (el perfil se crea automáticamente junto con el usuario).
 */
@Getter
@Setter
@Entity
@Table(name = "perfil_investigador")
@EntityListeners(AuditingEntityListener.class)
public class PerfilInvestigador {

    // PK compartida con la tabla usuarios — mismo UUID
    @Id
    @Column(name = "usuario_id", updatable = false, nullable = false)
    private UUID usuarioId;

    // Sede de trabajo habitual del investigador (nullable)
    @Column(name = "sede_predeterminada_id")
    private UUID sedePredeterminadaId;

    @LastModifiedDate
    @Column(name = "actualizado_en", nullable = false)
    private LocalDateTime actualizadoEn;

    @LastModifiedBy
    @Column(name = "actualizado_por")
    private UUID actualizadoPor;
}