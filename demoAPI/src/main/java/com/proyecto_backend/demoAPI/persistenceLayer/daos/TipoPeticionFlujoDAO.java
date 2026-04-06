package com.proyecto_backend.demoAPI.persistenceLayer.daos;

import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoPeticionFlujoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.RequerimientoPeticionEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoPeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.mappers.TipoPeticionFlujoMapper;
import com.proyecto_backend.demoAPI.persistenceLayer.repositories.ITipoPeticionFlujoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TipoPeticionFlujoDAO {

    private ITipoPeticionFlujoRepository tipoPeticionRepository;

    //Crear un tipo de petición
    public TipoPeticionFlujoResponseDTO save(
            TipoPeticionFlujoCreateUpdateDTO createDTO,
            List<RequerimientoPeticionEntity> requerimientos) {

        TipoPeticionFlujoEntity entity = TipoPeticionFlujoMapper.toEntity(createDTO, requerimientos);

        TipoPeticionFlujoEntity savedEntity = tipoPeticionRepository.save(entity);

        return TipoPeticionFlujoMapper.toDTO(savedEntity);
    }

    //Buscar tipo de petición por ID
    public Optional<TipoPeticionFlujoResponseDTO> findById(Long id) {

        return tipoPeticionRepository.findById(id)
                .map(TipoPeticionFlujoMapper::toDTO);
    }


    //Buscar todos los tipos de petición
    public List<TipoPeticionFlujoResponseDTO> findAll() {

        List<TipoPeticionFlujoEntity> entities = tipoPeticionRepository.findAll();

        return TipoPeticionFlujoMapper.toDTOList(entities);
    }

    //Actualizar tipo de petición
    public Optional<TipoPeticionFlujoResponseDTO> update(Long id,
                                                         List<RequerimientoPeticionEntity> requerimientos,
                                                         TipoPeticionFlujoCreateUpdateDTO updateDTO) {

        return tipoPeticionRepository.findById(id)
                .map(existingEntity -> {

                    TipoPeticionFlujoMapper.updateEntityFromDTO(updateDTO,
                            requerimientos,
                            existingEntity);

                    tipoPeticionRepository.save(existingEntity);
                    return TipoPeticionFlujoMapper.toDTO(existingEntity);
                });
    }

    //Eliminar tipo peticion por id
    public boolean delete(Long id) {

        if (tipoPeticionRepository.existsById(id)) {
            tipoPeticionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
