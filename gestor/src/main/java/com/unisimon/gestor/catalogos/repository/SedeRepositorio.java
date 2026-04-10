package com.unisimon.gestor.catalogos.repository;

import com.unisimon.gestor.catalogos.domain.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SedeRepositorio extends JpaRepository<Sede, UUID> {

    List<Sede> findByActivoTrue();

    boolean existsByCodigo(String codigo);
}