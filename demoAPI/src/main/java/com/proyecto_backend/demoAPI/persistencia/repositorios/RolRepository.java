package com.proyecto_backend.demoAPI.persistencia.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto_backend.demoAPI.persistencia.entidades.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long>{
    
    // Metodo para buscar un rol por nombre:
    public Optional<Rol> findByNombre (String nombre);
    
}
