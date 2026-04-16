package com.unisimon.gestor.catalogos.repository;

import com.unisimon.gestor.catalogos.domain.Programa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProgramaRepositorio extends JpaRepository<Programa, Long> {

    List<Programa> findByEsActivoTrue();

    Optional<Programa> findByUuid(UUID uuid);

    List<Programa> findByFacultadUuidAndEsActivoTrue(UUID facultadUuid);

    boolean existsByCodigo(String codigo);
}