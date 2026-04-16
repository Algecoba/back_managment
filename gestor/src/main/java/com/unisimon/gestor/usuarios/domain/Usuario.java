package com.unisimon.gestor.usuarios.domain;

import com.unisimon.gestor.shared.domain.EntidadAuditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Tabla: usuario
 * Repositorio central de usuarios del sistema.
 */
@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario extends EntidadAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true,
            updatable = false, columnDefinition = "UNIQUEIDENTIFIER DEFAULT NEWID()")
    private UUID uuid;

    @Column(name = "ms_tenant_id", length = 100)
    private String msTenantId;

    @Column(name = "ms_subject_id", unique = true, length = 100)
    private String msSubjectId;

    @Column(name = "correo", nullable = false, unique = true, length = 255)
    private String correo;

    @Column(name = "tipo_documento", nullable = false, length = 20)
    private String tipoDocumento;

    @Column(name = "numero_documento", nullable = false, unique = true, length = 50)
    private String numeroDocumento;

    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER,
               cascade = CascadeType.PERSIST)
    private List<UsuarioRol> usuarioRoles = new ArrayList<>();

    @PrePersist
    protected void antesDeGuardar() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }
}
