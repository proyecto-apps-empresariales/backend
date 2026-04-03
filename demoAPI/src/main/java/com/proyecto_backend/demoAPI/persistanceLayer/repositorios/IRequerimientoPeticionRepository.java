package com.proyecto_backend.demoAPI.persistanceLayer.repositorios;

import com.proyecto_backend.demoAPI.persistanceLayer.entidades.RequerimientoPeticionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRequerimientoPeticionRepository extends JpaRepository<Long, RequerimientoPeticionEntity> {

}
