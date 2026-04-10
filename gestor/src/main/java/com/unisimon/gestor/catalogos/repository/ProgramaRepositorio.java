package com.unisimon.gestor.catalogos.repository;

import com.unisimon.gestor.catalogos.domain.Programa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProgramaRepositorio extends JpaRepository<Programa, UUID> {

    List<Programa> findByActivoTrue();

    List<Programa> findByFacultadFacultadIdAndActivoTrue(UUID facultadId);

    boolean existsByCodigo(String codigo);
}