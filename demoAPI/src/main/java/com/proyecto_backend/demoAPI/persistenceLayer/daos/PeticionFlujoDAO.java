package com.proyecto_backend.demoAPI.persistenceLayer.daos;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoUpdateDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.EstadoPeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.PeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoPeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;
import com.proyecto_backend.demoAPI.persistenceLayer.mappers.PeticionFlujoMapper;
import com.proyecto_backend.demoAPI.persistenceLayer.repositories.IPeticionFlujoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PeticionFlujoDAO {

    private final IPeticionFlujoRepository peticionRepository;

    //Crear peticion
    public PeticionFlujoResponseDTO save(
            PeticionFlujoCreateDTO createDTO,
            Usuario remitente,
            Usuario destinatario,
            TipoPeticionFlujoEntity tipoPeticion,
            EstadoPeticionFlujoEntity estado,
            LocalDate fechaFin) {

        PeticionFlujoEntity entity = PeticionFlujoMapper.toEntity(
                createDTO,
                remitente,
                destinatario,
                tipoPeticion,
                estado,
                fechaFin
        );

        PeticionFlujoEntity savedEntity = peticionRepository.save(entity);

        return PeticionFlujoMapper.toDTO(savedEntity);
    }

    //Buscar peticion por Id
    public Optional<PeticionFlujoResponseDTO> findById(Long id) {

        return peticionRepository.findById(id)
                .map(PeticionFlujoMapper::toDTO);
    }

    public Optional<PeticionFlujoEntity> findEntityById(Long id) {
        return peticionRepository.findById(id);
    }

    //Buscar todas las peticiones
    public List<PeticionFlujoResponseDTO> findAll() {

        List<PeticionFlujoEntity> entities = peticionRepository.findAll();
        return PeticionFlujoMapper.toDTOList(entities);
    }

    //Actualizar una peticion
    public Optional<PeticionFlujoResponseDTO> update(
            Long id,
            PeticionFlujoUpdateDTO updateDTO,
            Usuario destinatario,
            TipoPeticionFlujoEntity tipoPeticion,
            LocalDate fechaFin) {

        return peticionRepository.findById(id)
                .map(existingEntity -> {

                    PeticionFlujoMapper.updateEntityFromDto(
                            updateDTO,
                            destinatario,
                            tipoPeticion,
                            fechaFin,
                            existingEntity //-> Entidad encontrada que será actualizada
                    );

                    // -> Se puede omitir esta linea y su conversión a DTO ya que se trata de la misma entidad gestionada.
                    //PeticionFlujoEntity updatedEntity = peticionRepository.save(existingEntity);

                    peticionRepository.save(existingEntity);
                    return PeticionFlujoMapper.toDTO(existingEntity);
                });
    }

    //Eliminar una peticion
    public boolean delete(Long id) {

        if (peticionRepository.existsById(id)) {
            peticionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //Buscar peticiones por el id del remitente
    public List<PeticionFlujoResponseDTO> findByRemitenteId(Long id) {

        List<PeticionFlujoEntity> entities = peticionRepository.findByRemitenteIdUsuario(id);
        return PeticionFlujoMapper.toDTOList(entities);
    }

    //Buscar peticiones por el id del destinatario
    public List<PeticionFlujoResponseDTO> findByDestinatarioId(Long id) {

        List<PeticionFlujoEntity> entities = peticionRepository.findByDestinatarioIdUsuario(id);
        return PeticionFlujoMapper.toDTOList(entities);
    }

    //Verifica si el nombre de la peticion ya existe
    public boolean existsByNombreIgnoreCase(String nombre) {
        return peticionRepository.existsByNombreIgnoreCase(nombre);
    }

    //Buscar peticion por nombre
    public Optional<PeticionFlujoResponseDTO> findByNombre(String nombre) {
        return peticionRepository.findByNombre(nombre)
                .map(PeticionFlujoMapper::toDTO);
    }

    //Se usa para el update
    //Busca la peticion por el nombre, si encuentra peticion por el nombre, pero el id es diferente retorna true
    //Si es true significa que ese nombre ya lo tiene otra entidad, entonces no actualiza
    public boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id) {
        return peticionRepository.existsByNombreIgnoreCaseAndIdNot(nombre, id);
    }

    //Cambia el estado de la peticion
    //Veficica que el estado actual de la peticion corresponsa al estado esperado para poder pasar al siguiente estado
    public Optional<PeticionFlujoResponseDTO> cambiarEstado(
            Long id,
            Long estadoActualEsperadoId,
            EstadoPeticionFlujoEntity nuevoEstado
    ) {

        return peticionRepository.findById(id)
                .filter(existingEntity ->
                        existingEntity.getEstado().getId().equals(estadoActualEsperadoId)
                )
                .map(existingEntity -> {

                    existingEntity.setEstado(nuevoEstado);
                    peticionRepository.save(existingEntity);

                    return PeticionFlujoMapper.toDTO(existingEntity);
                });
    }

}
