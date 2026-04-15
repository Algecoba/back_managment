package com.unisimon.gestor.investigadores.repository;

import com.unisimon.gestor.investigadores.domain.PerfilInvestigador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PerfilInvestigadorRepositorio
        extends JpaRepository<PerfilInvestigador, UUID> {
}