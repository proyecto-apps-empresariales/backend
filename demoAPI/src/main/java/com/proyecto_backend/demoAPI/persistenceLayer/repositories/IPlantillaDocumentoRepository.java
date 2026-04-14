package com.proyecto_backend.demoAPI.persistenceLayer.repositories;

import com.proyecto_backend.demoAPI.persistenceLayer.entities.PlantillaDocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoDocumentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IPlantillaDocumentoRepository extends JpaRepository<PlantillaDocumentoEntity, Long> {

    //Metodo para buscar por nombre
    Optional<PlantillaDocumentoEntity> findByNombreIgnoreCase(String nombre);

    //Existe por nombre
    Boolean existsByNombreIgnoreCase(String nombre);

    // Buscar por tipo de documento (nombre del tipo)
    Optional<PlantillaDocumentoEntity> findByTipoDocumento_NombreIgnoreCase(String nombreTipo);

    // Existe por tipo de documento
    Boolean existsByTipoDocumento_NombreIgnoreCase(String nombreTipo);

    // Todas las plantillas de un tipo de documento
    List<PlantillaDocumentoEntity> findByTipoDocumento_Id(Long idTipoDocumento);
}
