package com.unisimon.gestor.investigadores.repository;

import com.unisimon.gestor.investigadores.domain.InvestigadorPrograma;
import com.unisimon.gestor.investigadores.domain.InvestigadorProgramaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestigadorProgramaRepositorio
                extends JpaRepository<InvestigadorPrograma, InvestigadorProgramaId> {

        // fecha_fin null = vinculacion vigente actualmente
        @Query("""
                        SELECT ip FROM InvestigadorPrograma ip
                        WHERE ip.id.usuarioId = :usuarioId
                        AND ip.fechaFin IS NULL
                        """)
        List<InvestigadorPrograma> findVigentesByUsuarioId(
                        @Param("usuarioId") Long usuarioId);
}