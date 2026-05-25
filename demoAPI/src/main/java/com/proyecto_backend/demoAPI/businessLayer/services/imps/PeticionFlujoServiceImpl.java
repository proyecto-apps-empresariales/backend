package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.*;
import com.proyecto_backend.demoAPI.exceptions.BadRequestException;
import com.proyecto_backend.demoAPI.exceptions.ConflictException;
import com.proyecto_backend.demoAPI.exceptions.ResourceNotFoundException;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.PeticionFlujoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.*;
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
    private final ITipoPeticionFlujoService tipoPeticionService;
    private final IDocumentoService documentoService;
    private final IHistorialPeticionFlujoService historialService;

    @Override
    @Transactional
    public PeticionFlujoResponseDTO createPeticion(PeticionFlujoCreateDTO createDTO) {

        String nombreNormalizado = createDTO.getNombre().trim().toLowerCase();
        createDTO.setNombre(nombreNormalizado);

        //VAlida que no exista una petiion con el mismo nombre
        validateNombrePeticion(createDTO);

        //Resolver las entidades necesarias para enviar al dao
        //El remitente se debe resolver con jwt por medio de autenticacion. Debe removerse del cratedto -> Suplantacion
        Usuario remitente = usuarioService.buscarUsuarioEntityById(createDTO.getRemitente());
        Usuario destinatario = usuarioService.buscarUsuarioEntityById(createDTO.getDestinatario());

        DocumentoEntity documento = documentoService.getDocumentoEntityById(createDTO.getDocumento());

        TipoPeticionFlujoEntity tipoPeticion = tipoPeticionService.getTipoPeticionEntityById(createDTO.getTipoPeticion());

        //Setea el estado 1 por defecto => Estado CREADO
        EstadoPeticionFlujoEntity estadoPeticion = estadoService.getEstadoEntityById(1L);

        LocalDate fechaFin = createDTO.getFechaFin();

        log.info("Creando nueva Peticion: {}", createDTO.getNombre());

        PeticionFlujoResponseDTO createdPeticion = peticionDAO.save(createDTO, remitente, destinatario, documento, tipoPeticion, estadoPeticion, fechaFin);
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
                    return new ResourceNotFoundException("Petición no encontrada con ID: " + id);
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
                    return new ResourceNotFoundException("Petición no encontrada con Nombre: " + nombre);
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
            throw new BadRequestException("El Id es obligatorio: " + id);
        }

        log.debug("Obteniendo todas las Peticiones del Remitente por su Id");

        return peticionDAO.findByRemitenteId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PeticionFlujoResponseDTO> getAllPeticionesByDestinatarioId(Long id) {

        if (id == null) {
            log.warn("El Id del Destinatario es obligatorio: {}", id);
            throw new BadRequestException("El Id es obligatorio: " + id);
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

        updateDTO.setNombre(updateDTO.getNombre().trim().toLowerCase());

        validateUpdateData(updateDTO);

        //Si se va actualizar el nombre, se verifica que no alla otro tipo con el mismo nombre, se verifica con id
        validateNombrePeticionUpdate(updateDTO, id);

        Usuario destinatario = usuarioService.buscarUsuarioEntityById(updateDTO.getDestinatario());

        DocumentoEntity documento = documentoService.getDocumentoEntityById(updateDTO.getDocumento());

        //TipoPeticionFlujoEntity tipoPeticion = ....
        //Los estados se van a manejar desde endpoints en el controller para evitar tablas y enums
        TipoPeticionFlujoEntity tipoPeticion = tipoPeticionService.getTipoPeticionEntityById(updateDTO.getTipoPeticion());

        LocalDate fechaFin = updateDTO.getFechaFin();

        log.info("Actualizando Peticion: {}", updateDTO.getNombre());

        PeticionFlujoResponseDTO updatedPeticion = peticionDAO.update(id, updateDTO, destinatario, documento, tipoPeticion, fechaFin)
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
            throw new BadRequestException("El Id es obligatorio: " + id);
        }

        boolean deleted = peticionDAO.delete(id);

        if (!deleted) {
            log.warn("Intento de eliminar petición inexistente ID: {}", id);
            throw new ResourceNotFoundException("Petición no encontrada con ID: " + id);
        }

        log.info("Petición eliminada correctamente ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public PeticionFlujoEntity getPeticionEntityById(Long id) {
        return peticionDAO.findEntityById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Petición no encontrada con id: " + id));
    }

    //VAlida que el nombre de la peticion no este en uso
    private void validateNombrePeticion(PeticionFlujoCreateDTO createDTO) {
        if (peticionDAO.existsByNombreIgnoreCase(createDTO.getNombre())) {
            throw new ConflictException("El nombre de la petición la está en uso");
        }
    }

    //VAlida que el nombre de la peticion para hacer update
    private void validateNombrePeticionUpdate(PeticionFlujoUpdateDTO updateDTO, Long id) {
        if (peticionDAO.existsByNombreIgnoreCaseAndIdNot(updateDTO.getNombre(), id)) {
            throw new ConflictException("El nombre de la petición ya está en uso");
        }
    }

    //VAlida los datos del update
    private void validateUpdateData(PeticionFlujoUpdateDTO updateDTO) {

        if (updateDTO.getNombre().isBlank()) throw new BadRequestException("El nombre es obligatorio");
        if (updateDTO.getDescripcion().isBlank()) throw new BadRequestException("La descripción es obligatoria");
    }

    //Estados
//    CREADO	1
//    EN_REVISION	2
//    APROBADO  3
//    RECHAZADO	4
//    FIRMADO	5
//    FINALIZADO 6

    private void registrarHistorial(PeticionFlujoEntity peticion, String descripcion) {
        // Usa el destinatario como editor (quien hace la acción)
        HistorialPeticionFlujoEntity historial = new HistorialPeticionFlujoEntity();
        historial.setPeticion(peticion);
        historial.setUsuarioEditor(peticion.getDestinatario()); // o el usuario autenticado
        historial.setDescripcion(descripcion);
        historialService.createHistorial(historial);
    }

    //Enviar a revisión (CREADO → EN_REVISION)
    @Override
    @Transactional
    public PeticionFlujoResponseDTO enviarRevision(Long id) {

        if (id == null) {
            throw new BadRequestException("El id es obligatorio");
        }

        EstadoPeticionFlujoEntity estadoRevision =
                estadoService.getEstadoEntityById(2L);

         PeticionFlujoResponseDTO resultado=peticionDAO.cambiarEstado(id, 1L, estadoRevision)
                .orElseThrow(() ->
                        new ConflictException(
                                "La petición no existe o no está en estado CREADO"
                        )
                );

        PeticionFlujoEntity entity = peticionDAO.findEntityById(id).get();
        registrarHistorial(entity, "Petición enviada a revisión");

         return resultado;
    }

    //Aprobar petición (EN_REVISION → APROBADO)
    @Override
    @Transactional
    public PeticionFlujoResponseDTO aprobarPeticion(Long id) {

        if (id == null) {
            throw new BadRequestException("El id es obligatorio");
        }

        EstadoPeticionFlujoEntity estadoAprobado =
                estadoService.getEstadoEntityById(3L);

        PeticionFlujoResponseDTO resultado= peticionDAO.cambiarEstado(id, 2L, estadoAprobado)
                .orElseThrow(() ->
                        new ConflictException(
                                "La petición no existe o no está en estado EN_REVISION."
                        )
                );

        PeticionFlujoEntity entity = peticionDAO.findEntityById(id).get();
        registrarHistorial(entity, "Petición APROBADA");

        return resultado;
    }

    //Rechazar petición (EN_REVISION → RECHAZADO)
    @Override
    @Transactional
    public PeticionFlujoResponseDTO rechazarPeticion(Long id) {

        if (id == null) {
            throw new BadRequestException("El id es obligatorio");
        }

        EstadoPeticionFlujoEntity estadoRechazado =
                estadoService.getEstadoEntityById(4L);

        PeticionFlujoResponseDTO resultado= peticionDAO.cambiarEstado(id, 2L, estadoRechazado)
                .orElseThrow(() ->
                        new ConflictException(
                                "La petición no existe o no está en estado -> EN_REVISION"
                        )
                );

        PeticionFlujoEntity entity = peticionDAO.findEntityById(id).get();
        registrarHistorial(entity, "Petición RECHAZADA");

        return resultado;
    }

    @Override
    @Transactional
    public PeticionFlujoResponseDTO firmarPeticion(Long id) {

        if (id == null) {
            throw new BadRequestException("El id es obligatorio");
        }

        EstadoPeticionFlujoEntity estadoFirmado =
                estadoService.getEstadoEntityById(5L);

        PeticionFlujoResponseDTO resultado= peticionDAO.cambiarEstado(id, 3L, estadoFirmado)
                .orElseThrow(() ->
                        new ConflictException(
                                "La petición no existe o no está en estado EN_REVISION"
                        )
                );

        PeticionFlujoEntity entity = peticionDAO.findEntityById(id).get();
        registrarHistorial(entity, "Petición FIRMADA");

        return resultado;
    }

    @Override
    @Transactional
    public PeticionFlujoResponseDTO finalizarPeticion(Long id) {

        if (id == null) {
            throw new BadRequestException("El id es obligatorio");
        }

        EstadoPeticionFlujoEntity estadoFinalizado =
                estadoService.getEstadoEntityById(6L);

        PeticionFlujoResponseDTO resultado= peticionDAO.cambiarEstado(id, 5L, estadoFinalizado)
                .orElseThrow(() ->
                        new ConflictException(
                                "La petición no existe o no está en estado FIRMADO"
                        )
                );

        PeticionFlujoEntity entity = peticionDAO.findEntityById(id).get();
        registrarHistorial(entity, "Petición FINALIZADA");

        return resultado;
    }


}


