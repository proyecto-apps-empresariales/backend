package com.proyecto_backend.demoAPI.persistenceLayer.daos;

import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoPeticionCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoPeticionResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.RequerimientoPeticionEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.mappers.RequerimientoPeticionMapper;
import com.proyecto_backend.demoAPI.persistenceLayer.repositories.IRequerimientoPeticionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RequerimientoPeticionDAO {

    private IRequerimientoPeticionRepository requerimientoRepository;

    //Guardar requerimiento/peticion
    public RequerimientoPeticionResponseDTO save(RequerimientoPeticionCreateUpdateDTO createDTO) {

        RequerimientoPeticionEntity entity = RequerimientoPeticionMapper.toEntity(createDTO);
        RequerimientoPeticionEntity savedEntity = requerimientoRepository.save(entity);

        return RequerimientoPeticionMapper.toDTO(savedEntity);
    }

    //Buscar requerimiento por id
    public Optional<RequerimientoPeticionResponseDTO> findById(Long id) {

        return requerimientoRepository.findById(id)
                .map(RequerimientoPeticionMapper::toDTO);
    }

    //Buscar todos los requerimientos por id
    public List<RequerimientoPeticionResponseDTO> findAll() {

        List<RequerimientoPeticionEntity> entities = requerimientoRepository.findAll();

        return RequerimientoPeticionMapper.toDTOList(entities);
    }

    //Actualizar un requerimiento por id
    public Optional<RequerimientoPeticionResponseDTO> update(Long id, RequerimientoPeticionCreateUpdateDTO updateDTO) {

        return requerimientoRepository.findById(id)
                .map(existingEntity -> {
                    RequerimientoPeticionMapper.updateEntityFromDTO(
                            updateDTO,
                            existingEntity
                    );

                    requerimientoRepository.save(existingEntity);
                    return RequerimientoPeticionMapper.toDTO(existingEntity);
                });
    }

    //Eliminar requerimiento por id
    public boolean delete(Long id) {

        if (requerimientoRepository.existsById(id)) {
            requerimientoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
