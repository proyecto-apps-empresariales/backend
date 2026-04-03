package com.proyecto_backend.demoAPI.persistanceLayer.repositorios;

import com.proyecto_backend.demoAPI.persistanceLayer.entidades.PeticionFlujoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPeticionFlujoRepository extends JpaRepository<Long, PeticionFlujoEntity> {

}
