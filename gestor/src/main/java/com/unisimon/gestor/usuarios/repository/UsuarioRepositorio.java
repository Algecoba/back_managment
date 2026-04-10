package com.unisimon.gestor.usuarios.repository;

import com.unisimon.gestor.usuarios.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio de usuarios.
 *
 * Spring Data JPA genera la implementación automáticamente.
 * Solo declaramos los métodos que el nombre no puede expresar
 * o que requieren queries optimizadas con JOIN FETCH.
 */
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, UUID> {

    // Spring Data deriva el SQL del nombre del método
    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByMsSubjectId(String msSubjectId);

    boolean existsByCorreo(String correo);

    boolean existsByNumDoc(String numDoc);

    /**
     * Busca usuario por correo cargando sus roles en la misma query.
     *
     * Sin JOIN FETCH, Hibernate haría N+1 queries: una para el usuario
     * y una por cada rol. Con JOIN FETCH todo llega en una sola query.
     * Crítico para el flujo de autenticación que ocurre en cada request.
     */
    @Query("""
            SELECT u FROM Usuario u
            LEFT JOIN FETCH u.usuarioRoles ur
            LEFT JOIN FETCH ur.rol
            WHERE u.correo = :correo
            AND u.activo = true
            """)
    Optional<Usuario> findByCorreoConRoles(@Param("correo") String correo);
}