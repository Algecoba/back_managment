package com.unisimon.gestor.catalogos.repository;

import com.unisimon.gestor.catalogos.domain.Facultad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FacultadRepositorio extends JpaRepository<Facultad, Long> {

    List<Facultad> findByEsActivoTrue();

    Optional<Facultad> findByUuid(UUID uuid);

    // Busca por UUID de la sede padre — Spring Data navega la relacion
    // automaticamente
    List<Facultad> findBySedeUuidAndEsActivoTrue(UUID sedeUuid);

    boolean existsByCodigo(String codigo);
}