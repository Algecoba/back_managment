package com.unisimon.gestor.catalogos.repository;

import com.unisimon.gestor.catalogos.domain.Facultad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FacultadRepositorio extends JpaRepository<Facultad, UUID> {

    List<Facultad> findByActivoTrue();

    List<Facultad> findBySedeSedeIdAndActivoTrue(UUID sedeId);

    boolean existsByCodigo(String codigo);
}