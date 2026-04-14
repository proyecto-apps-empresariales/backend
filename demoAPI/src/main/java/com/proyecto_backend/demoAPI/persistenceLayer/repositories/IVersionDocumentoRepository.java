package com.proyecto_backend.demoAPI.persistenceLayer.repositories;

import com.proyecto_backend.demoAPI.persistenceLayer.entities.DocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.VersionDocumentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IVersionDocumentoRepository extends JpaRepository<VersionDocumentoEntity, Long> {

    //Metodo para buscar por nombre
    Optional<VersionDocumentoEntity> findByNombreIgnoreCase(String nombre);

    //Existe por nombre
    Boolean existsByNombreIgnoreCase(String nombre);

    //Buscar por documento
    Optional<VersionDocumentoEntity> findByDocumento_IdOrderByIdAsc(Long idDocumento);

    //Contar versiones
    long countByDocumento_Id(Long idDocumento);
}
