package com.proyecto_backend.demoAPI.persistenceLayer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto_backend.demoAPI.persistenceLayer.entities.RequerimientoPeticionEntity;

import java.util.List;
import java.util.Optional;

public interface IRequerimientoPeticionRepository extends JpaRepository<RequerimientoPeticionEntity, Long> {

    List<RequerimientoPeticionEntity> findByNombreIn(List<String> nombres);

    Optional<RequerimientoPeticionEntity> findByNombre(String nombre);

    boolean existsByNombreIgnoreCase(String nombre);
}
