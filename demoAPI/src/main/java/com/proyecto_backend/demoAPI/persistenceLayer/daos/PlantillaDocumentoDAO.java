package com.proyecto_backend.demoAPI.persistenceLayer.daos;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PlantillaDocumentoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PlantillaDocumentoResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.PlantillaDocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.mappers.PlantillaDocumentoMapper;
import com.proyecto_backend.demoAPI.persistenceLayer.repositories.IPlantillaDocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PlantillaDocumentoDAO {

    //Instancia del repository JPA
    private final IPlantillaDocumentoRepository plantillaDocumentoRepository;

    //Guardar plantilla de documento, recibe createDTO y guarda la entity, retorna el responseDTO
    public PlantillaDocumentoResponseDTO save(PlantillaDocumentoCreateUpdateDTO createDto){

        PlantillaDocumentoEntity entity= PlantillaDocumentoMapper.toEntity(createDto);
        PlantillaDocumentoEntity savedEntity= plantillaDocumentoRepository.save(entity);

        return PlantillaDocumentoMapper.toDTO(savedEntity);
    }

    //Buscar plantilla de documento por ID
    public Optional<PlantillaDocumentoResponseDTO> findById(Long id) {

        return plantillaDocumentoRepository.findById(id)
                .map(PlantillaDocumentoMapper::toDTO);
    }

    //Obtener todos las plantillas de documento
    public List<PlantillaDocumentoResponseDTO> findAll() {

        List<PlantillaDocumentoEntity> entities = plantillaDocumentoRepository.findAll();
        return PlantillaDocumentoMapper.toDTOList(entities);
    }


    //Actualizar plantilla de documento existente por ID, recibe un updateDTO, genera la actualizacion desde el mapper, guarda el entity y retorna responseDTO
    public Optional<PlantillaDocumentoResponseDTO> update(Long id, PlantillaDocumentoCreateUpdateDTO updateDTO) {

        return plantillaDocumentoRepository.findById(id)
                .map(existingEntity -> {
                    PlantillaDocumentoMapper.updateEntityFromDTO(
                            updateDTO,
                            existingEntity
                    );

                    PlantillaDocumentoEntity updatedEntity = plantillaDocumentoRepository.save(existingEntity);
                    return PlantillaDocumentoMapper.toDTO(updatedEntity);
                });
    }

    //Buscar plantilla de documento por nombre
    public Optional<PlantillaDocumentoResponseDTO> findByNombre(String nombre) {

        return plantillaDocumentoRepository.findByNombreIgnoreCase(nombre)
                .map(PlantillaDocumentoMapper::toDTO);
    }

    //Verificar si la plantilla de documento ya existe
    public boolean existsByNombreIgnoreCare(String nombre) {
        return plantillaDocumentoRepository.existsByNombreIgnoreCase(nombre);
    }

}
