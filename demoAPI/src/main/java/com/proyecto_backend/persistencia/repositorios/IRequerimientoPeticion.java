package com.proyecto_backend.persistencia.repositorios;

import com.proyecto_backend.persistencia.entidades.RequerimientoPeticion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRequerimientoPeticion extends JpaRepository<RequerimientoPeticion, Long> {

}
