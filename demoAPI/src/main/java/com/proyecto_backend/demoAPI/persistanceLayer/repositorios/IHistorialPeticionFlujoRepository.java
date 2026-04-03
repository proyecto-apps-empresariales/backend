package com.proyecto_backend.demoAPI.persistanceLayer.repositorios;

import com.proyecto_backend.demoAPI.persistanceLayer.entidades.HistorialPeticionFlujoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHistorialPeticionFlujoRepository extends JpaRepository<Long, HistorialPeticionFlujoEntity> {

}

