package com.proyecto_backend.demoAPI.persistenceLayer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoPeticionFlujoEntity;

public interface ITipoPeticionFlujoRepository extends JpaRepository<Long, TipoPeticionFlujoEntity> {

}
