package com.proyecto_backend.demoAPI.persistenceLayer.daos;

import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoDocumentoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoDocumentoResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.EstadoPeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoDocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.mappers.EstadoPeticionFlujoMapper;
import com.proyecto_backend.demoAPI.persistenceLayer.mappers.TipoDocumentoMapper;
import com.proyecto_backend.demoAPI.persistenceLayer.repositories.ITipoDocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TipoDocumentoDAO {

    //Instancia del repository JPA
    private final ITipoDocumentoRepository tipoDocumentoRepository;

    //Guardar tipo de documento, recibe createDTO y guarda la entity, retorna el responseDTO
    public TipoDocumentoResponseDTO save(TipoDocumentoCreateUpdateDTO createDto){

        TipoDocumentoEntity entity= TipoDocumentoMapper.toEntity(createDto);
        TipoDocumentoEntity savedEntity= tipoDocumentoRepository.save(entity);

        return TipoDocumentoMapper.toDTO(savedEntity);
    }

    //Guardar tipo de documento, recibe entity y guarda la entity, retorna el responseDTO
    public TipoDocumentoResponseDTO saveEntity(TipoDocumentoEntity entity){

        TipoDocumentoEntity savedEntity= tipoDocumentoRepository.save(entity);

        return TipoDocumentoMapper.toDTO(savedEntity);
    }

    //Buscar tipo de documento por ID
    public Optional<TipoDocumentoResponseDTO> findById(Long id) {

        return tipoDocumentoRepository.findById(id)
                .map(TipoDocumentoMapper::toDTO);
    }

    //Buscar tipo de documento entidad por ID
    public Optional<TipoDocumentoEntity> findEntityById(Long id) {

        return tipoDocumentoRepository.findById(id);
    }

    //Obtener todos los tipos de documento
    public List<TipoDocumentoResponseDTO> findAll() {

        List<TipoDocumentoEntity> entities = tipoDocumentoRepository.findAll();
        return TipoDocumentoMapper.toDTOList(entities);
    }

    //Buscar tipo de documento por nombre
    public Optional<TipoDocumentoResponseDTO> findByNombre(String nombre) {

        return tipoDocumentoRepository.findByNombreIgnoreCase(nombre)
                .map(TipoDocumentoMapper::toDTO);
    }

    //Buscar tipo de documento por nombre
    public Optional<TipoDocumentoEntity> findByNombreEntidad(String nombre) {

        return tipoDocumentoRepository.findByNombreIgnoreCase(nombre);
    }

    //Verificar si el tipo de documento ya existe
    public boolean existsByNombreIgnoreCase(String nombre) {
        return tipoDocumentoRepository.existsByNombreIgnoreCase(nombre);
    }

    //Eliminar tipo de documento por ID
    public boolean deleteById(Long id) {

        if (tipoDocumentoRepository.existsById(id)) {
            tipoDocumentoRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
