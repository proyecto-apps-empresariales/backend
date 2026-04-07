package com.proyecto_backend.demoAPI.persistenceLayer.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto_backend.demoAPI.persistenceLayer.entities.Permiso;

public interface IPermisoRepository extends JpaRepository<Permiso, Long> {

    // Metodo para buscar un permiso por su nombre:
    Optional<Permiso> findByNombre (String nombre);
    
}
