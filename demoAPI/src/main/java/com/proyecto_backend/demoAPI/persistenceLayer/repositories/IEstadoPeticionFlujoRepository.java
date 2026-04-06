package com.proyecto_backend.demoAPI.persistenceLayer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto_backend.demoAPI.persistenceLayer.entities.EstadoPeticionFlujoEntity;

public interface IEstadoPeticionFlujoRepository extends JpaRepository<EstadoPeticionFlujoEntity, Long> {

    EstadoPeticionFlujoEntity findByNombre(String nombre);

}
