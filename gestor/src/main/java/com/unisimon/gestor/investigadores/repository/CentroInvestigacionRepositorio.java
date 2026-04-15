package com.unisimon.gestor.investigadores.repository;

import com.unisimon.gestor.investigadores.domain.CentroInvestigacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CentroInvestigacionRepositorio
        extends JpaRepository<CentroInvestigacion, UUID> {

    List<CentroInvestigacion> findByActivoTrue();
}