package com.unisimon.gestor.usuarios.repository;

import com.unisimon.gestor.usuarios.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByUuid(UUID uuid);

    Optional<Usuario> findByMsSubjectId(String msSubjectId);

    boolean existsByCorreo(String correo);

    boolean existsByNumeroDocumento(String numeroDocumento);

    @Query("""
        SELECT u FROM Usuario u
        LEFT JOIN FETCH u.usuarioRoles ur
        LEFT JOIN FETCH ur.rol
        WHERE u.correo = :correo
        AND u.esActivo = true
        """)
    Optional<Usuario> findByCorreoConRoles(@Param("correo") String correo);
}
