package com.proyecto_backend.demoAPI.persistenceLayer.repositories;

import com.proyecto_backend.demoAPI.persistenceLayer.entities.RequerimientoDocumentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRequerimientoDocumentoRepository extends JpaRepository<RequerimientoDocumentoEntity, Long> {
    //Metodo para buscar por nombre
    Optional<RequerimientoDocumentoEntity> findByNombreIgnoreCase(String nombre);

    //Existe por nombre
    Boolean existsByNombreIgnoreCase(String nombre);
}
