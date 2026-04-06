package com.proyecto_backend.demoAPI.persistenceLayer.daos;

import com.proyecto_backend.demoAPI.businessLayer.dtos.HistorialPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.HistorialPeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.mappers.HistorialPeticionFlujoMapper;
import com.proyecto_backend.demoAPI.persistenceLayer.repositories.IHistorialPeticionFlujoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HistorialPeticionFlujoDAO {

    private final IHistorialPeticionFlujoRepository historialRepository;

    //Crear Registro de historial -> Recibe instancia creada por el servicio
    public HistorialPeticionFlujoResponseDTO save(HistorialPeticionFlujoEntity entity) {

        HistorialPeticionFlujoEntity savedEntity = historialRepository.save(entity);

        return HistorialPeticionFlujoMapper.toDTO(savedEntity);
    }

    //Buscar por id del Historial/registro
    public Optional<HistorialPeticionFlujoResponseDTO> findById(Long id) {

        return historialRepository.findById(id)
                .map(HistorialPeticionFlujoMapper::toDTO);
    }

    //Buscar todos los registros
    public List<HistorialPeticionFlujoResponseDTO> findAll() {

        List<HistorialPeticionFlujoEntity> entities = historialRepository.findAll();
        return HistorialPeticionFlujoMapper.toDTOList(entities);
    }

    //Buscar todos los registros de un proceso/peticion especifica
    public List<HistorialPeticionFlujoResponseDTO> findAllByPeticionId(Long id) {

        List<HistorialPeticionFlujoEntity> entities = historialRepository.findAllByPeticionId(id);
        return HistorialPeticionFlujoMapper.toDTOList(entities);
    }

    //Buscar todos los registros de un proceso/peticion especifica ordenado del mas reciente al mas viejo (Desc)
    public List<HistorialPeticionFlujoResponseDTO> findAllByPeticionIdOrderByFecha(Long id) {

        List<HistorialPeticionFlujoEntity> entities = historialRepository.findAllByPeticionIdOrderByFecha(id);
        return HistorialPeticionFlujoMapper.toDTOList(entities);
    }


}
