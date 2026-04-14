package com.proyecto_backend.demoAPI.persistenceLayer.daos;

import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoDocumentoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoDocumentoResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.RequerimientoDocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.mappers.RequerimientoDocumentoMapper;
import com.proyecto_backend.demoAPI.persistenceLayer.repositories.IRequerimientoDocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RequerimientoDocumentoDAO {

    //Instancia del repository JPA
    private final IRequerimientoDocumentoRepository requerimientoDocumentoRepository;

    //Guardar requerimiento de documento, recibe createDTO y guarda la entity, retorna el responseDTO
    public RequerimientoDocumentoResponseDTO save(RequerimientoDocumentoCreateUpdateDTO createDto){

        RequerimientoDocumentoEntity entity= RequerimientoDocumentoMapper.toEntity(createDto);
        RequerimientoDocumentoEntity savedEntity= requerimientoDocumentoRepository.save(entity);

        return RequerimientoDocumentoMapper.toDTO(savedEntity);
    }

    //Buscar requerimiento de documento por ID
    public Optional<RequerimientoDocumentoResponseDTO> findById(Long id) {

        return requerimientoDocumentoRepository.findById(id)
                .map(RequerimientoDocumentoMapper::toDTO);
    }

    //Obtener todos los requerimientos de documento
    public List<RequerimientoDocumentoResponseDTO> findAll() {

        List<RequerimientoDocumentoEntity> entities = requerimientoDocumentoRepository.findAll();
        return RequerimientoDocumentoMapper.toDTOList(entities);
    }


    //Actualizar requerimiento de documento existente por ID, recibe un updateDTO, genera la actualizacion desde el mapper, guarda el entity y retorna responseDTO
    public Optional<RequerimientoDocumentoResponseDTO> update(Long id, RequerimientoDocumentoCreateUpdateDTO updateDTO) {

        return requerimientoDocumentoRepository.findById(id)
                .map(existingEntity -> {
                    RequerimientoDocumentoMapper.updateEntityFromDTO(
                            updateDTO,
                            existingEntity
                    );

                    RequerimientoDocumentoEntity updatedEntity = requerimientoDocumentoRepository.save(existingEntity);
                    return RequerimientoDocumentoMapper.toDTO(updatedEntity);
                });
    }

    //Buscar el requerimiento de documento por nombre
    public Optional<RequerimientoDocumentoResponseDTO> findByNombre(String nombre) {

        return requerimientoDocumentoRepository.findByNombreIgnoreCase(nombre)
                .map(RequerimientoDocumentoMapper::toDTO);
    }

    //Buscar el requerimiento entity de documento por nombre
    public Optional<RequerimientoDocumentoEntity> findEntityByNombre(String nombre) {

        return requerimientoDocumentoRepository.findByNombreIgnoreCase(nombre);
    }

    //Verificar si el requerimiento de documento ya existe
    public boolean existsByNombreIgnoreCare(String nombre) {
        return requerimientoDocumentoRepository.existsByNombreIgnoreCase(nombre);
    }
}
