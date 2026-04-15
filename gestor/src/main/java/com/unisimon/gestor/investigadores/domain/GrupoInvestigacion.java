package com.unisimon.gestor.investigadores.domain;

import com.unisimon.gestor.shared.domain.EntidadAuditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Grupo de investigación.
 *
 * Siempre pertenece a un centro de investigación.
 * El líder del grupo es un usuario del sistema (nullable).
 */
@Getter
@Setter
@Entity
@Table(name = "grupos_investigacion")
public class GrupoInvestigacion extends EntidadAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "grupo_id", updatable = false, nullable = false)
    private UUID grupoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "centro_id", nullable = false)
    private CentroInvestigacion centro;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "lider_usuario_id")
    private UUID liderUsuarioId;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;
}