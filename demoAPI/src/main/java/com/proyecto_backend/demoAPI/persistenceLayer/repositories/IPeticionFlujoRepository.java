package com.proyecto_backend.demoAPI.persistenceLayer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto_backend.demoAPI.persistenceLayer.entities.PeticionFlujoEntity;

public interface IPeticionFlujoRepository extends JpaRepository<PeticionFlujoEntity, Long> {

}
