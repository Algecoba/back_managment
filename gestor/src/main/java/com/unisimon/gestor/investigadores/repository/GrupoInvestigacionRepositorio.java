package com.unisimon.gestor.investigadores.repository;

import com.unisimon.gestor.investigadores.domain.GrupoInvestigacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoInvestigacionRepositorio
        extends JpaRepository<GrupoInvestigacion, Long> {

    List<GrupoInvestigacion> findByEsActivoTrue();

    List<GrupoInvestigacion> findByCentroCentroInvestigacionIdAndEsActivoTrue(
            Long centroId);
}