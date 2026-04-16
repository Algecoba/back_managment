package com.unisimon.gestor.catalogos.repository;

import com.unisimon.gestor.catalogos.domain.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SedeRepositorio extends JpaRepository<Sede, Long> {

    List<Sede> findByEsActivoTrue();

    Optional<Sede> findByUuid(UUID uuid);

    boolean existsByCodigo(String codigo);
}