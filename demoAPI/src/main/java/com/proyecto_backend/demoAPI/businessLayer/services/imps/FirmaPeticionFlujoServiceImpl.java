package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaPeticionFlujoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IFirmaPeticionFlujoService;
import com.proyecto_backend.demoAPI.businessLayer.services.IPeticionFlujoService;
import com.proyecto_backend.demoAPI.businessLayer.services.IUsuarioService;
import com.proyecto_backend.demoAPI.exceptions.BadRequestException;
import com.proyecto_backend.demoAPI.exceptions.ConflictException;
import com.proyecto_backend.demoAPI.exceptions.ResourceNotFoundException;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.FirmaPeticionFlujoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.PeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FirmaPeticionFlujoServiceImpl implements IFirmaPeticionFlujoService {

    private final FirmaPeticionFlujoDAO firmaPeticionDAO;
    private final IUsuarioService usuarioService;
    private final IPeticionFlujoService peticionService;

    @Override
    @Transactional
    public FirmaPeticionFlujoResponseDTO createFirmaPeticion(FirmaPeticionFlujoCreateDTO createDTO) {

        if (firmaPeticionDAO.existsByUsuarioIdAndPeticionId(
                createDTO.getUsuarioFirmador(),
                createDTO.getPeticion())) {

            log.warn("El usuario {} ya firmó la petición {}",
                    createDTO.getUsuarioFirmador(),
                    createDTO.getPeticion());

            throw new ConflictException("El usuario ya firmó esta petición");
        }

        Usuario usuario = usuarioService.buscarUsuarioEntityById(createDTO.getUsuarioFirmador());

        PeticionFlujoEntity peticion = peticionService.getPeticionEntityById(createDTO.getPeticion());

        log.info("Creando nueva Firma en Petición con ID: {}", createDTO.getPeticion());

        FirmaPeticionFlujoResponseDTO createdFirma = firmaPeticionDAO.save(createDTO, usuario, peticion);
        log.info("Firma registrada exitosamente con ID: {}", createdFirma.getId());

        return createdFirma;
    }

    @Override
    @Transactional(readOnly = true)
    public FirmaPeticionFlujoResponseDTO getFirmaPeticionById(Long id) {

        //Verifica que el id no sea nulo
        if (id == null) {
            log.warn("El Id es obligatorio: {}", id);
            throw new BadRequestException("El Id es obligatorio");
        }

        log.debug("Buscando Firma de petición por ID: {}", id);

        return firmaPeticionDAO.findById(id)
                .orElseThrow(() -> {
                    log.warn("Firma de petición no encontrada con ID: {}", id);
                    return new ResourceNotFoundException("Firma de petición no encontrada con ID: " + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<FirmaPeticionFlujoResponseDTO> getAllFirmaPeticion() {

        log.debug("Obteniendo todas las Firmas de Peticiones");

        return firmaPeticionDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FirmaPeticionFlujoResponseDTO> getAllFirmaPeticionByUsuarioId(Long id) {

        if (id == null) {
            log.warn("El Id de la Petición es obligatorio: {}", id);
            throw new BadRequestException("El Id es obligatorio: " + id);
        }

        log.debug("Obteniendo todas las Firmas que ha hecho el usuario por su Id");

        return firmaPeticionDAO.findByUsuarioId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FirmaPeticionFlujoResponseDTO> getAllByPeticionId(Long id) {

        if (id == null) {
            log.warn("El Id de la petición es obligatorio: {}", id);
            throw new BadRequestException("El Id es obligatorio: " + id);
        }

        log.debug("Obteniendo todas las Firmas de la petición por su Id");

        return firmaPeticionDAO.findByPeticionId(id);
    }


    @Override
    @Transactional
    public void deleteFirmaPeticion(Long id) {

        if (id == null) {
            log.warn("El Id de la firmaPetición es obligatorio: {}", id);
            throw new BadRequestException("El Id es obligatorio: " + id);
        }

        boolean deleted = firmaPeticionDAO.delete(id);

        if (!deleted) {
            log.warn("Intento de eliminar firma de petición inexistente. ID: {}", id);
            throw new ResourceNotFoundException("Firma de petición no encontrada con ID: " + id);
        }

        log.info("Firma de petición eliminada correctamente ID: {}", id);

    }
}
