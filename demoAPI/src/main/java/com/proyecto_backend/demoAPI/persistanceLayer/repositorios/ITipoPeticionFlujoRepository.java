package com.proyecto_backend.demoAPI.persistanceLayer.repositorios;

import com.proyecto_backend.demoAPI.persistanceLayer.entidades.TipoPeticionFlujoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITipoPeticionFlujoRepository extends JpaRepository<Long, TipoPeticionFlujoEntity> {

}
