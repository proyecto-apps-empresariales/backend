package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IEstadoPeticionFlujoService;
import com.proyecto_backend.demoAPI.businessLayer.services.IPeticionFlujoService;
import com.proyecto_backend.demoAPI.businessLayer.services.IUsuarioService;
import com.proyecto_backend.demoAPI.exceptions.BadRequestException;
import com.proyecto_backend.demoAPI.exceptions.ConflictException;
import com.proyecto_backend.demoAPI.exceptions.ResourceNotFoundException;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.PeticionFlujoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.EstadoPeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.PeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoPeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PeticionFlujoServiceImpl implements IPeticionFlujoService {

    private final PeticionFlujoDAO peticionDAO;
    private final IUsuarioService usuarioService;
    private final IEstadoPeticionFlujoService estadoService;

    @Override
    @Transactional
    public PeticionFlujoResponseDTO createPeticion(PeticionFlujoCreateDTO createDTO) {

        String nombreNormalizado = createDTO.getNombre().toLowerCase();
        createDTO.setNombre(nombreNormalizado);

        //VAlida que no exista una petiion con el mismo nombre
        validateNombrePeticion(createDTO);

        //Resolver las entidades necesarias para enviar al dao
        //El remitente se debe resolver con jwt por medio de autenticacion. Debe removerse del cratedto -> Suplantacion
        Usuario remitente = usuarioService.buscarUsuarioEntityById(createDTO.getRemitente());
        Usuario destinatario = usuarioService.buscarUsuarioEntityById(createDTO.getDestinatario());

        //TipoPeticionFlujoEntity tipoPeticion = ....
        TipoPeticionFlujoEntity tipoPeticion = new TipoPeticionFlujoEntity();

        //Setea el estado 1 por defecto => Estado CREADO
        EstadoPeticionFlujoEntity estadoPeticion = estadoService.getEstadoEntityById(1L);

        LocalDate fechaFin = createDTO.getFechaFin();

        log.info("Creando nueva Peticion: {}", createDTO.getNombre());

        PeticionFlujoResponseDTO createdPeticion = peticionDAO.save(createDTO, remitente, destinatario, tipoPeticion, estadoPeticion, fechaFin);
        log.info("Peticion creada exitosamente con ID: {}", createdPeticion.getId());

        return createdPeticion;
    }

    @Override
    @Transactional(readOnly = true)
    public PeticionFlujoResponseDTO getPeticionById(Long id) {

        //Verifica que el id no sea nulo
        if (id == null) {
            log.warn("El Id es obligatorio: {}", id);
            throw new BadRequestException("El Id es obligatorio");
        }

        log.debug("Buscando Petición por ID: {}", id);

        return peticionDAO.findById(id)
                .orElseThrow(() -> {
                    log.warn("Petición no encontrada con ID: {}", id);
                    return new ResourceNotFoundException("Petición no encontrada con ID: {}" + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public PeticionFlujoResponseDTO getPeticionByNombre(String nombre) {

        if (nombre == null || nombre.isBlank()) {
            log.warn("El nombre es obligatorio");
            throw new BadRequestException("El nombre es obligatorio");
        }

        return peticionDAO.findByNombre(nombre)
                .orElseThrow(() -> {
                    log.warn("Petición no encontrada con Nombre: {}", nombre);
                    return new ResourceNotFoundException("Petición no encontrada con Nombre: {}" + nombre);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<PeticionFlujoResponseDTO> getAllPeticiones() {
        log.debug("Obteniendo todas las Peticiones");

        return peticionDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PeticionFlujoResponseDTO> getAllPeticionesByRemitenteId(Long id) {

        if (id == null) {
            log.warn("El Id del Remitente es obligatorio: {}", id);
            throw new BadRequestException("El Id es obligatorio: {}" + id);
        }

        log.debug("Obteniendo todas las Peticiones del Remitente por su Id");

        return peticionDAO.findByRemitenteId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PeticionFlujoResponseDTO> getAllPeticionesByDestinatarioId(Long id) {

        if (id == null) {
            log.warn("El Id del Destinatario es obligatorio: {}", id);
            throw new BadRequestException("El Id es obligatorio: {}" + id);
        }

        log.debug("Obteniendo todas las Peticiones del Destinatario por su Id");

        return peticionDAO.findByDestinatarioId(id);
    }

    @Override
    @Transactional
    public PeticionFlujoResponseDTO updatePeticion(Long id, PeticionFlujoUpdateDTO updateDTO) {
        log.info("Actualizando Estado ID: {}", id);

        //Verifica que el id no sea nulo
        if (id == null) {
            log.warn("El Id de la petición es obligatorio: {}", id);
            throw new BadRequestException("El Id es obligatorio");
        }

        validateUpdateData(updateDTO);

        //Si se va actualizar el nombre, se verifica que no alla otro tipo con el mismo nombre
        PeticionFlujoResponseDTO existingEntity = this.getPeticionById(id);
        if (!existingEntity.getNombre().equals(updateDTO.getNombre())) {
            validateNombrePeticionUpdate(updateDTO);
        }

        Usuario destinatario = usuarioService.buscarUsuarioEntityById(id);

        //TipoPeticionFlujoEntity tipoPeticion = ....
        //Los estados se van a manejar desde endpoints en el controller para evitar tablas y enums
        TipoPeticionFlujoEntity tipoPeticion = new TipoPeticionFlujoEntity();

        LocalDate fechaFin = updateDTO.getFechaFin();

        log.info("Actualizando Peticion: {}", updateDTO.getNombre());

        PeticionFlujoResponseDTO updatedPeticion = peticionDAO.update(id, updateDTO, destinatario, tipoPeticion, fechaFin)
                .orElseThrow(() -> {
                    log.warn("No se encontró la petición con Id: {}", id);
                    return new ResourceNotFoundException("No se encotró la petición con ID: " + id);
                });

        log.info("Peticion actualizada exitosamente con ID: {}", updatedPeticion.getId());

        return updatedPeticion;
    }

    @Override
    @Transactional
    public void deletePeticion(Long id) {

        if (id == null) {
            log.warn("El Id de la Petición es obligatorio: {}", id);
            throw new BadRequestException("El Id es obligatorio: {}" + id);
        }

        boolean deleted = peticionDAO.delete(id);

        if (!deleted) {
            log.warn("Intento de eliminar petición inexistente ID: {}", id);
            throw new ResourceNotFoundException("Petición no encontrada con ID: " + id);
        }

        log.info("Petición eliminada correctamente ID: {}", id);
    }


    //VAlida que el nombre de la peticion no este en uso
    private void validateNombrePeticion(PeticionFlujoCreateDTO createDTO) {
        if (peticionDAO.existsByNombreIgnoreCase(createDTO.getNombre())) {
            throw new ConflictException("El nombre de la petición la está en uso");
        }
    }

    //VAlida que el nombre de la peticion para hace rupdate
    private void validateNombrePeticionUpdate(PeticionFlujoUpdateDTO updateDTO) {
        if (peticionDAO.existsByNombreIgnoreCase(updateDTO.getNombre())) {
            throw new ConflictException("El nombre de la petición la está en uso");
        }
    }

    //VAlida los datos del update
    private void validateUpdateData(PeticionFlujoUpdateDTO updateDTO) {

        if (updateDTO.getNombre().isBlank()) throw new BadRequestException("El nombre es obligatorio");
        if (updateDTO.getDescripcion().isBlank()) throw new BadRequestException("La descripción es obligatoria");
    }

    public PeticionFlujoEntity getEntityById(Long id) {

        if (id == null) {
            throw new BadRequestException("El id no es válido");
        }

        return peticionDAO.findEntityById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }
}


