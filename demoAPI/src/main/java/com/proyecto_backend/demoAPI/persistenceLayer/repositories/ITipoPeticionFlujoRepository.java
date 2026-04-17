package com.proyecto_backend.demoAPI.persistenceLayer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoPeticionFlujoEntity;

import java.util.Optional;

public interface ITipoPeticionFlujoRepository extends JpaRepository<TipoPeticionFlujoEntity, Long> {

    Optional<TipoPeticionFlujoEntity> findByNombre(String nombre);

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);

}
