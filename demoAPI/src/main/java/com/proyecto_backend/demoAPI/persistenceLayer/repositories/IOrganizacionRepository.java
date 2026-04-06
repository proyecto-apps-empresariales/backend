package com.proyecto_backend.demoAPI.persistenceLayer.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto_backend.demoAPI.persistenceLayer.entities.Organizacion;

@Repository
public interface IOrganizacionRepository extends JpaRepository<Organizacion, Long>{

    // Metodo para buscar una organizacion por nombre:
    Optional<Organizacion> findByNombre (String nombre);
    
}
