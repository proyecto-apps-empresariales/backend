package com.proyecto_backend.demoAPI.persistenceLayer.daos;

import com.proyecto_backend.demoAPI.businessLayer.dtos.*;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.DocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoDocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.mappers.DocumentoMapper;
import com.proyecto_backend.demoAPI.persistenceLayer.mappers.TipoDocumentoMapper;
import com.proyecto_backend.demoAPI.persistenceLayer.repositories.IDocumentoRepository;
import com.proyecto_backend.demoAPI.persistenceLayer.repositories.ITipoDocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DocumentoDAO {

    //Instancia del repository JPA
    private final IDocumentoRepository documentoRepository;

    //Guardar el documento, recibe createDTO y guarda la entity, retorna el responseDTO
    public DocumentoResponseDTO save(DocumentoCreateDTO createDto){

        DocumentoEntity entity= DocumentoMapper.toEntity(createDto);
        DocumentoEntity savedEntity= documentoRepository.save(entity);

        return DocumentoMapper.toDTO(savedEntity);
    }

    //Buscar documento por ID
    public Optional<DocumentoResponseDTO> findById(Long id) {

        return documentoRepository.findById(id)
                .map(DocumentoMapper::toDTO);
    }

    //Obtener todos los documentos
    public List<DocumentoResponseDTO> findAll() {

        List<DocumentoEntity> entities = documentoRepository.findAll();
        return DocumentoMapper.toDTOList(entities);
    }


    //Actualizar documento existente por ID, recibe un updateDTO, genera la actualizacion desde el mapper, guarda el entity y retorna responseDTO
    public Optional<DocumentoResponseDTO> update(Long id, DocumentoUpdateDTO updateDTO) {

        return documentoRepository.findById(id)
                .map(existingEntity -> {
                    DocumentoMapper.updateEntityFromDTO(
                            updateDTO,
                            existingEntity
                    );

                    DocumentoEntity updatedEntity = documentoRepository.save(existingEntity);
                    return DocumentoMapper.toDTO(updatedEntity);
                });
    }


    //Buscar documento por nombre
    public Optional<DocumentoResponseDTO> findByNombre(String nombre) {

        return documentoRepository.findByNombreIgnoreCase(nombre)
                .map(DocumentoMapper::toDTO);
    }

    //Verificar si el documento ya existe
    public boolean existsByNombreIgnoreCare(String nombre) {
        return documentoRepository.existsByNombreIgnoreCase(nombre);
    }
}
