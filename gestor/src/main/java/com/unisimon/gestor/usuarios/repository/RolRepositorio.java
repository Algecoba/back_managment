package com.unisimon.gestor.usuarios.repository;

import com.unisimon.gestor.usuarios.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RolRepositorio extends JpaRepository<Rol, Long> {

    Optional<Rol> findByCodigo(String codigo);

    Optional<Rol> findByUuid(UUID uuid);

    boolean existsByCodigo(String codigo);
}
