package com.proyecto_backend.demoAPI.persistanceLayer.repositorios;

import com.proyecto_backend.demoAPI.persistanceLayer.entidades.EstadoPeticionFlujoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEstadoPeticionFlujoRepository extends JpaRepository<Long, EstadoPeticionFlujoEntity> {

}
