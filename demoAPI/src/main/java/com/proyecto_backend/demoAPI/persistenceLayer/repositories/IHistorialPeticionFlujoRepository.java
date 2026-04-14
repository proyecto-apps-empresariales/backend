package com.proyecto_backend.demoAPI.persistenceLayer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto_backend.demoAPI.persistenceLayer.entities.HistorialPeticionFlujoEntity;

import java.util.List;

public interface IHistorialPeticionFlujoRepository extends JpaRepository<HistorialPeticionFlujoEntity, Long> {

    List<HistorialPeticionFlujoEntity> findAllByPeticionId(Long id);

    List<HistorialPeticionFlujoEntity> findAllByPeticionIdOrderByFecha(Long peticionID);

    List<HistorialPeticionFlujoEntity> findAllByPeticionNombreOrderByFecha(String peticionNombre);

}

