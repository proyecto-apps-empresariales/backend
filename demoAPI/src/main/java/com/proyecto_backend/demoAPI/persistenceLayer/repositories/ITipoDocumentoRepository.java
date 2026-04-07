package com.proyecto_backend.demoAPI.persistenceLayer.repositories;


import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoDocumentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITipoDocumentoRepository extends JpaRepository<TipoDocumentoEntity, Long> {

    //Metodo para buscar por nombre
    Optional<TipoDocumentoEntity> findByNombre(String nombre);
}
