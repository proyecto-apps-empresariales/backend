package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import com.proyecto_backend.demoAPI.businessLayer.dtos.HistorialPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IHistorialPeticionFlujoService;
import com.proyecto_backend.demoAPI.exceptions.BadRequestException;
import com.proyecto_backend.demoAPI.exceptions.ResourceNotFoundException;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.HistorialPeticionFlujoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.HistorialPeticionFlujoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class HistorialPeticionFlujoServiceImpl implements IHistorialPeticionFlujoService {

    private final HistorialPeticionFlujoDAO historialDAO;

    @Override
    @Transactional
    public HistorialPeticionFlujoResponseDTO createHistorial(HistorialPeticionFlujoEntity entity) {

        if (entity == null) {
            log.warn("El historial recibido es null");
            throw new BadRequestException("El historial es obligatorio");
        }

        validateCreateHistorialEntity(entity);

        log.info("Creando nuevo registro Historial: {}", entity.getId());

        HistorialPeticionFlujoResponseDTO createdHistorial = historialDAO.save(entity);
        log.info("Registro de historial creado exitosamente con ID: {}", createdHistorial.getId());

        return createdHistorial;
    }

    @Override
    @Transactional(readOnly = true)
    public HistorialPeticionFlujoResponseDTO getHistorialById(Long id) {

        if (id == null) {
            log.warn("El Id del Historial es obligatorio: {}", id);
            throw new BadRequestException("El Id del Historial es obligatorio");
        }

        log.debug("Buscando Historial por ID: {}", id);

        return historialDAO.findById(id)
                .orElseThrow(() -> {
                            log.warn("Historial no encontrado con ID: {}", id);

                            return new ResourceNotFoundException("Historial no encontrado con ID: " + id);
                        }
                );

    }

    @Override
    @Transactional(readOnly = true)
    public List<HistorialPeticionFlujoResponseDTO> getHistorialCompletoByPeticionId(Long id) {

        if (id == null) {
            log.warn("El Id es obligatorio: {}", id);
            throw new BadRequestException("El Id de es obligatorio");
        }

        log.debug("Buscando Historial COMPLETO de la petición con ID: {}", id);

        return historialDAO.findAllByPeticionIdOrderByFecha(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistorialPeticionFlujoResponseDTO> getHistorialCompletoByPeticionNombre(String nombre) {

        if (nombre == null || nombre.isBlank()) {
            log.warn("El nombre es obligatorio: {}", nombre);
            throw new BadRequestException("El nombre de es obligatorio");
        }

        String nombreNormalizado = nombre.trim().toLowerCase();
        log.debug("Buscando Historial COMPLETO de la petición con nombre: {}", nombre);

        return historialDAO.findAllByPeticionNombreOrderByFecha(nombreNormalizado);    }

    private void validateCreateHistorialEntity(HistorialPeticionFlujoEntity createEntity) {

        if (createEntity.getPeticion() == null) {
            throw new BadRequestException("La petición asociada es obligatoria");
        }

        if (createEntity.getDescripcion() == null || createEntity.getDescripcion().isBlank()) {
            throw new BadRequestException("La descripción es obligatoria");
        }

        if (createEntity.getUsuarioEditor() == null) {
            throw new BadRequestException("El usuario editor es obligatorio");
        }
    }
}
