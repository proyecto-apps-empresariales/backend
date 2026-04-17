package com.proyecto_backend.demoAPI.persistenceLayer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto_backend.demoAPI.persistenceLayer.entities.PeticionFlujoEntity;

import java.util.List;
import java.util.Optional;

public interface IPeticionFlujoRepository extends JpaRepository<PeticionFlujoEntity, Long> {

    Optional<PeticionFlujoEntity> findByNombre(String nombre);

    List<PeticionFlujoEntity> findByRemitenteIdUsuario(Long id);

    List<PeticionFlujoEntity> findByDestinatarioIdUsuario(Long id);

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);

}
