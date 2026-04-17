package com.proyecto_backend.demoAPI.persistenceLayer.daos;

import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoPeticionFlujoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.RequerimientoPeticionEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoPeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;
import com.proyecto_backend.demoAPI.persistenceLayer.mappers.TipoPeticionFlujoMapper;
import com.proyecto_backend.demoAPI.persistenceLayer.repositories.ITipoPeticionFlujoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TipoPeticionFlujoDAO {

    private final ITipoPeticionFlujoRepository tipoPeticionRepository;

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

    //Buscar tipo de petición por Nombre
    public Optional<TipoPeticionFlujoResponseDTO> findByNombre(String nombre) {

        return tipoPeticionRepository.findByNombre(nombre)
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

    // Metodo para buscar un tipo de petición por id y retornar la entidad resuelta(casos especiales):
    public Optional<TipoPeticionFlujoEntity> findTipoPeticionEntityById (Long idUsuario) {
        return tipoPeticionRepository.findById(idUsuario);
    }

    //Eliminar tipo peticion por id
    public boolean delete(Long id) {

        if (tipoPeticionRepository.existsById(id)) {
            tipoPeticionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsByNombreIgnoreCase(String nombre) {
        return tipoPeticionRepository.existsByNombreIgnoreCase(nombre);
    }

    //Se usa para el update
    //Busca el tipo de petición por el nombre, si encuentra peticion por el nombre, pero el id es diferente retorna true
    //Si es true significa que ese nombre ya lo tiene otra entidad, entonces no actualiza
    public boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id) {
        return tipoPeticionRepository.existsByNombreIgnoreCaseAndIdNot(nombre, id);
    }
}
