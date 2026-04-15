package com.proyecto_backend.demoAPI.persistenceLayer.daos;

import com.proyecto_backend.demoAPI.businessLayer.dtos.*;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.VersionDocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.mappers.VersionDocumentoMapper;
import com.proyecto_backend.demoAPI.persistenceLayer.repositories.IVersionDocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VersionDocumentoDAO {

    //Instancia del repository JPA
    private final IVersionDocumentoRepository versionDocumentoRepository;

    //Guardar el documento, recibe createDTO y guarda la entity, retorna el responseDTO
    public VersionDocumentoResponseDTO save(VersionDocumentoCreateDTO createDto){

        VersionDocumentoEntity entity= VersionDocumentoMapper.toEntity(createDto);
        VersionDocumentoEntity savedEntity= versionDocumentoRepository.save(entity);

        return VersionDocumentoMapper.toDTO(savedEntity);
    }

    //Guardar el documento, recibe entity y guarda la entity, retorna el responseDTO
    public VersionDocumentoResponseDTO saveEntity(VersionDocumentoEntity entity){

        VersionDocumentoEntity savedEntity= versionDocumentoRepository.save(entity);

        return VersionDocumentoMapper.toDTO(savedEntity);
    }

    //Buscar documento por ID
    public Optional<VersionDocumentoResponseDTO> findById(Long id) {

        return versionDocumentoRepository.findById(id)
                .map(VersionDocumentoMapper::toDTO);
    }

    //Obtener todos las versiones
    public List<VersionDocumentoResponseDTO> findAll() {

        List<VersionDocumentoEntity> entities = versionDocumentoRepository.findAll();
        return VersionDocumentoMapper.toDTOList(entities);
    }

    // Obtener versiones de un documento por id del documento
    public List<VersionDocumentoResponseDTO> findByDocumentoId(Long idDocumento) {
        return versionDocumentoRepository.findByDocumento_IdOrderByIdAsc(idDocumento)
                .stream()
                .map(VersionDocumentoMapper::toDTO)
                .toList();
    }

    //Actualizar version documento existente por ID, recibe un updateDTO, genera la actualizacion desde el mapper, guarda el entity y retorna responseDTO
    public Optional<VersionDocumentoResponseDTO> update(Long id, VersionDocumentoUpdateDTO updateDTO) {

        return versionDocumentoRepository.findById(id)
                .map(existingEntity -> {
                    VersionDocumentoMapper.updateEntityFromDTO(
                            updateDTO,
                            existingEntity
                    );

                    VersionDocumentoEntity updatedEntity = versionDocumentoRepository.save(existingEntity);
                    return VersionDocumentoMapper.toDTO(updatedEntity);
                });
    }


    //Buscar version documento por nombre
    public Optional<VersionDocumentoResponseDTO> findByNombre(String nombre) {

        return versionDocumentoRepository.findByNombreIgnoreCase(nombre)
                .map(VersionDocumentoMapper::toDTO);
    }

    //Verificar si la version documento ya existe
    public boolean existsByNombreIgnoreCare(String nombre) {
        return versionDocumentoRepository.existsByNombreIgnoreCase(nombre);
    }

    // Contar versiones de un documento
    public long countByDocumentoId(Long idDocumento) {
        return versionDocumentoRepository.countByDocumento_Id(idDocumento);
    }

    //Eliminar version por ID
    public boolean deleteById(Long id) {

        if (versionDocumentoRepository.existsById(id)) {
            versionDocumentoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
