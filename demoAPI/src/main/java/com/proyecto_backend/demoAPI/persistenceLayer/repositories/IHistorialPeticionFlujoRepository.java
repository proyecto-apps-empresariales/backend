package com.proyecto_backend.demoAPI.persistenceLayer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto_backend.demoAPI.persistenceLayer.entities.HistorialPeticionFlujoEntity;

public interface IHistorialPeticionFlujoRepository extends JpaRepository<Long, HistorialPeticionFlujoEntity> {

}

