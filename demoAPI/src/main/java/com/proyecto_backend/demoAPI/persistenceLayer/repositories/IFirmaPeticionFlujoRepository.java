package com.proyecto_backend.demoAPI.persistenceLayer.repositories;

import com.proyecto_backend.demoAPI.persistenceLayer.entities.FirmaPeticionFlujoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IFirmaPeticionFlujoRepository extends JpaRepository<FirmaPeticionFlujoEntity, Long> {

    List<FirmaPeticionFlujoEntity> findAllByUsuarioIdUsuario(Long id);

    List<FirmaPeticionFlujoEntity> findAllByPeticionId(Long id);

    boolean existsByUsuarioIdUsuarioAndPeticionId(Long usuarioId, Long peticionId);

}
