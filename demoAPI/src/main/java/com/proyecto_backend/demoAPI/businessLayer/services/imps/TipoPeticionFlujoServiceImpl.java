package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoPeticionFlujoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IRequerimientoPeticionService;
import com.proyecto_backend.demoAPI.businessLayer.services.ITipoPeticionFlujoService;
import com.proyecto_backend.demoAPI.exceptions.BadRequestException;
import com.proyecto_backend.demoAPI.exceptions.ConflictException;
import com.proyecto_backend.demoAPI.exceptions.ResourceNotFoundException;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.TipoPeticionFlujoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.RequerimientoPeticionEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoPeticionFlujoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TipoPeticionFlujoServiceImpl implements ITipoPeticionFlujoService {

    private final TipoPeticionFlujoDAO tipoPeticionDAO;
    private final IRequerimientoPeticionService requerimientoService;

    @Override
    @Transactional
    public TipoPeticionFlujoResponseDTO createTipoPeticionFlujo(TipoPeticionFlujoCreateUpdateDTO createDTO) {
        String nombreNormalizado = createDTO.getNombre().trim().toLowerCase();
        createDTO.setNombre(nombreNormalizado);

        validateNombreTipoPeticion(createDTO);

        List<RequerimientoPeticionEntity> requerimientosEntities = requerimientoService.getAllByNombreIn(createDTO.getRequerimientos());

        if (requerimientosEntities.size() != createDTO.getRequerimientos().size()) {
            throw new BadRequestException("Uno o más requerimientos no existen en el sistema");
        }

        log.info("Creando nuevo TipoPetición: {}", createDTO.getNombre());

        TipoPeticionFlujoResponseDTO createdTipo = tipoPeticionDAO.save(createDTO, requerimientosEntities);
        log.info("TipoPetición creado exitosamente con ID: {}", createdTipo.getId());

        return createdTipo;
    }

    @Override
    @Transactional(readOnly = true)
    public TipoPeticionFlujoResponseDTO getTipoPeticionFlujoById(Long id) {

        if (id == null) {
            log.warn("El Id es obligatorio: {}", id);
            throw new BadRequestException("El id es obligatorio");
        }

        log.debug("Buscando TipoPetición por ID: {}", id);

        return tipoPeticionDAO.findById(id)
                .orElseThrow(() -> {
                    log.warn("TipoPeticion no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("TipoPetición no encontrado con ID: " + id);
                });
    }

    @Override
    public TipoPeticionFlujoEntity getTipoPeticionEntityById(Long id) {
        if (id == null) {
            log.warn("El Id del tipo de petición es obligatorio: {}", id);
            throw new BadRequestException("El id es obligatorio");
        }

        log.debug("Buscando el TipoPetición por ID: {}", id);

        return tipoPeticionDAO.findTipoPeticionEntityById(id)
                .orElseThrow(() -> {
                    log.warn("El TipoPeticion no fue encontrado con ID: {}", id);
                    return new ResourceNotFoundException("TipoPetición no encontrado con ID: " + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public TipoPeticionFlujoResponseDTO getTipoPeticionFlujoByNombre(String nombre) {

        if (nombre == null) {
            log.warn("El nombre es obligatorio: {}", nombre);
            throw new BadRequestException("El nombre es obligatorio");
        }

        log.debug("Buscando TipoPetición por nombre: {}", nombre);

        return tipoPeticionDAO.findByNombre(nombre)
                .orElseThrow(() -> {
                    log.warn("TipoPetición no encontrado con nombre: {}", nombre);
                    return new ResourceNotFoundException("TipoPetición no encontrado con nombre: " + nombre);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoPeticionFlujoResponseDTO> getAllTipoPeticionFlujos() {
        log.debug("Obteniendo todos los TipoPetición");
        return tipoPeticionDAO.findAll();
    }

    @Override
    @Transactional
    public TipoPeticionFlujoResponseDTO updateTipoPeticionFlujo(Long id, TipoPeticionFlujoCreateUpdateDTO updateDTO) {

        if (id == null) {
            log.warn("El Id del TipoPetición es obligatorio: {}", id);
            throw new BadRequestException("El id es obligatorio");
        }

        updateDTO.setNombre(updateDTO.getNombre().trim().toLowerCase());

        validateNombreTipoPeticionUpdate(updateDTO, id);
        log.info("Actualizando TipoPetición: {}", updateDTO.getNombre());

        List<RequerimientoPeticionEntity> requerimientosEntities = requerimientoService.getAllByNombreIn(updateDTO.getRequerimientos());

        if (requerimientosEntities.size() != updateDTO.getRequerimientos().size()) {
            throw new BadRequestException("Uno o más requerimientos no existen en el sistema");
        }

        TipoPeticionFlujoResponseDTO updatedTipoPeticion = tipoPeticionDAO.update(id, requerimientosEntities, updateDTO)
                .orElseThrow(() -> {
                    log.warn("No se encontró TipoPetición con Id: {}", id);
                    return new ResourceNotFoundException("No se encotró TipoPetición con ID: " + id);
                });
        log.info("TipoPetición actualizado exitosamente con ID: {}", updatedTipoPeticion.getId());
        return updatedTipoPeticion;
    }

    @Override
    @Transactional
    public void deleteTipoPeticionFlujo(Long id) {
        if (id == null) {
            log.warn("El Id de TipoPetición es obligatorio: {}", id);
            throw new BadRequestException("El id es obligatorio");
        }

        boolean deleted = tipoPeticionDAO.delete(id);

        if (!deleted) {
            log.warn("Intento de eliminar TipoPetición inexistente ID: {}", id);
            throw new ResourceNotFoundException("TipoPetición no encontrado con ID: " + id);
        }

        log.info("TipoPetición eliminado correctamente ID: {}", id);
    }

    //VAlida que el nombre de la peticion no este en uso
    private void validateNombreTipoPeticion(TipoPeticionFlujoCreateUpdateDTO createDTO) {
        if (tipoPeticionDAO.existsByNombreIgnoreCase(createDTO.getNombre())) {
            throw new ConflictException("El nombre del Tipo de Petición ya está en uso");
        }
    }

    private void validateNombreTipoPeticionUpdate(TipoPeticionFlujoCreateUpdateDTO updateDTO, Long id) {
        if (tipoPeticionDAO.existsByNombreIgnoreCaseAndIdNot(updateDTO.getNombre(), id)) {
            throw new ConflictException("El nombre del Tipo de Petición ya está en uso");
        }
    }

}
