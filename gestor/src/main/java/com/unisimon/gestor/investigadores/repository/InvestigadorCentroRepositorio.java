package com.unisimon.gestor.investigadores.repository;

import com.unisimon.gestor.investigadores.domain.InvestigadorCentro;
import com.unisimon.gestor.investigadores.domain.InvestigadorCentroId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestigadorCentroRepositorio
                extends JpaRepository<InvestigadorCentro, InvestigadorCentroId> {

        @Query("""
                        SELECT ic FROM InvestigadorCentro ic
                        WHERE ic.id.usuarioId = :usuarioId
                        AND ic.fechaFin IS NULL
                        """)
        List<InvestigadorCentro> findVigentesByUsuarioId(
                        @Param("usuarioId") Long usuarioId);
}