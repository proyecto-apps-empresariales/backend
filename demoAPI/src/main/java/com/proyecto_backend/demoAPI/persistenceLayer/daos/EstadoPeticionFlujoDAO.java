package com.proyecto_backend.demoAPI.persistenceLayer.daos;

import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.EstadoPeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.mappers.EstadoPeticionFlujoMapper;
import com.proyecto_backend.demoAPI.persistenceLayer.repositories.IEstadoPeticionFlujoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EstadoPeticionFlujoDAO {

    private final IEstadoPeticionFlujoRepository estadoRepository;
    //Como el mapper es static, no se inyecta
    //private final EstadoPeticionFlujoMapper estadoMapper;

    //Guardar entidad -> Responde con DTO
    public EstadoPeticionFlujoResponseDTO save(EstadoPeticionFlujoCreateUpdateDTO createDTO) {

        EstadoPeticionFlujoEntity entity = EstadoPeticionFlujoMapper.toEntity(createDTO);
        EstadoPeticionFlujoEntity savedEntity = estadoRepository.save(entity);

        return EstadoPeticionFlujoMapper.toDTO(savedEntity);
    }

    //Buscar por ID
    public Optional<EstadoPeticionFlujoResponseDTO> findById(Long id) {

        return estadoRepository.findById(id)
                .map(EstadoPeticionFlujoMapper::toDTO);
    }

    //Obtener todos los productos
    public List<EstadoPeticionFlujoResponseDTO> findAll() {

        List<EstadoPeticionFlujoEntity> entities = estadoRepository.findAll();
        return EstadoPeticionFlujoMapper.toDTOList(entities);
    }

    //Actualizar estado existente por ID
    public Optional<EstadoPeticionFlujoResponseDTO> update(Long id, EstadoPeticionFlujoCreateUpdateDTO updateDTO) {

        return estadoRepository.findById(id)
                .map(existingEntity -> {
                    EstadoPeticionFlujoMapper.updateEntityFromDTO(
                            updateDTO,
                            existingEntity
                    );

                    EstadoPeticionFlujoEntity updatedEntity = estadoRepository.save(existingEntity);
                    return EstadoPeticionFlujoMapper.toDTO(updatedEntity);
                });
    }

    //Eliminar estado por ID
    public boolean deleteById(Long id) {

        if (estadoRepository.existsById(id)) {
            estadoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //BUscar la entidad por id -> Envia el optional al servicio para resolver la entidad
    public Optional<EstadoPeticionFlujoEntity> findEntityById(Long id) {
        return estadoRepository.findById(id);
    }

    //Buscar Estado por nombre
    public Optional<EstadoPeticionFlujoResponseDTO> findByNombre(String nombre) {

        return estadoRepository.findByNombreIgnoreCase(nombre)
                .map(EstadoPeticionFlujoMapper::toDTO);
    }

    //Verificar si nombre del estado ya existe
    public boolean existsByNombreIgnoreCare(String nombre) {
        return estadoRepository.existsByNombreIgnoreCase(nombre);
    }

    //Se usa para el update
    //Busca la peticion por el nombre, si encuentra peticion por el nombre, pero el id es diferente retorna true
    //Si es true significa que ese nombre ya lo tiene otra entidad, entonces no actualiza
    public boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id) {
        return estadoRepository.existsByNombreIgnoreCaseAndIdNot(nombre, id);
    }
}
