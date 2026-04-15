package com.unisimon.gestor.investigadores.repository;

import com.unisimon.gestor.investigadores.domain.GrupoInvestigacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GrupoInvestigacionRepositorio
        extends JpaRepository<GrupoInvestigacion, UUID> {

    List<GrupoInvestigacion> findByActivoTrue();

    List<GrupoInvestigacion> findByCentroCentroIdAndActivoTrue(UUID centroId);
}