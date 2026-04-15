package com.unisimon.gestor.investigadores.repository;

import com.unisimon.gestor.investigadores.domain.InvestigadorPrograma;
import com.unisimon.gestor.investigadores.domain.InvestigadorProgramaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvestigadorProgramaRepositorio
        extends JpaRepository<InvestigadorPrograma, InvestigadorProgramaId> {

    // Vinculaciones vigentes: activo_hasta es null
    @Query("""
            SELECT ip FROM InvestigadorPrograma ip
            WHERE ip.id.usuarioId = :usuarioId
            AND ip.activoHasta IS NULL
            """)
    List<InvestigadorPrograma> findVigentesByUsuarioId(
            @Param("usuarioId") UUID usuarioId);
}