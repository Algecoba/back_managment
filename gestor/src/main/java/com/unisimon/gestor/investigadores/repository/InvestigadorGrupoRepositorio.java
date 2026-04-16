package com.unisimon.gestor.investigadores.repository;

import com.unisimon.gestor.investigadores.domain.InvestigadorGrupo;
import com.unisimon.gestor.investigadores.domain.InvestigadorGrupoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestigadorGrupoRepositorio
        extends JpaRepository<InvestigadorGrupo, InvestigadorGrupoId> {

    @Query("SELECT ig FROM InvestigadorGrupo ig WHERE ig.id.usuarioId = :usuarioId AND ig.fechaFin IS NULL")
    List<InvestigadorGrupo> findVigentesByUsuarioId(@Param("usuarioId") Long usuarioId);
}
