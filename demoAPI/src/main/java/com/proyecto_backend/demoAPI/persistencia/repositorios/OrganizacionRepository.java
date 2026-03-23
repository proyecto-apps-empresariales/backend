package com.proyecto_backend.demoAPI.persistencia.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto_backend.demoAPI.persistencia.entidades.Organizacion;

@Repository
public interface OrganizacionRepository extends JpaRepository<Organizacion, Long>{
    
}
