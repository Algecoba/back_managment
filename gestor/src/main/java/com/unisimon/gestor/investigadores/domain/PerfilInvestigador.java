package com.unisimon.gestor.investigadores.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Tabla: perfil_investigador
 * Perfil extendido del investigador — relacion 1:1 con usuario.
 */
@Getter
@Setter
@Entity
@Table(name = "perfil_investigador")
@EntityListeners(AuditingEntityListener.class)
public class PerfilInvestigador {

    @Id
    @Column(name = "usuario_id", updatable = false, nullable = false)
    private Long usuarioId;

    @Column(name = "sede_predeterminada_id")
    private Long sedePredeterminadaId;

    @LastModifiedDate
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @LastModifiedBy
    @Column(name = "usuario_actualizacion", length = 100)
    private String usuarioActualizacion;
}
