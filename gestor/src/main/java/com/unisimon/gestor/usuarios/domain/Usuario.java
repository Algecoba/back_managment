package com.unisimon.gestor.usuarios.domain;

import com.unisimon.gestor.shared.domain.EntidadAuditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entidad central de usuarios del sistema.
 *
 * Integra con Microsoft Identity via ms_subject_id (sub claim del token
 * OAuth2/OIDC). El correo institucional es el identificador de login.
 *
 * La relación con roles es N:M a través de UsuarioRol, que además
 * registra quién hizo la asignación y cuándo.
 */
@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario extends EntidadAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "usuario_id", updatable = false, nullable = false)
    private UUID usuarioId;

    @Column(name = "ms_tenant_id", length = 100)
    private String msTenantId;

    @Column(name = "ms_subject_id", unique = true, length = 100)
    private String msSubjectId;

    // Correo institucional — usado como username en Spring Security
    @Column(name = "correo", nullable = false, unique = true, length = 255)
    private String correo;

    @Column(name = "tipo_doc", nullable = false, length = 20)
    private String tipoDoc;

    @Column(name = "num_doc", nullable = false, unique = true, length = 50)
    private String numDoc;

    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    /**
     * Relación N:M con roles a través de la tabla puente usuario_rol.
     * FetchType.EAGER porque Spring Security necesita los roles
     * en el momento de autenticación, dentro del mismo request.
     *
     * CascadeType.ALL no aplica aquí: los roles son catálogos
     * independientes, no se crean ni eliminan desde el usuario.
     */
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<UsuarioRol> usuarioRoles = new ArrayList<>();
}