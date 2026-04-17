package com.proyecto_backend.demoAPI.persistenceLayer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto_backend.demoAPI.persistenceLayer.entities.EstadoPeticionFlujoEntity;

import java.util.Optional;

public interface IEstadoPeticionFlujoRepository extends JpaRepository<EstadoPeticionFlujoEntity, Long> {

    Optional<EstadoPeticionFlujoEntity> findByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);

}
