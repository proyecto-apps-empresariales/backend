package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoPeticionCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoPeticionResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IRequerimientoPeticionService;
import com.proyecto_backend.demoAPI.exceptions.BadRequestException;
import com.proyecto_backend.demoAPI.exceptions.ConflictException;
import com.proyecto_backend.demoAPI.exceptions.ResourceNotFoundException;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.RequerimientoPeticionDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.RequerimientoPeticionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RequerimientoPeticionServiceImpl implements IRequerimientoPeticionService {

    private final RequerimientoPeticionDAO requerimientoDAO;

    @Override
    @Transactional
    public RequerimientoPeticionResponseDTO createRequerimiento(RequerimientoPeticionCreateUpdateDTO createDTO) {

        createDTO.setNombre(createDTO.getNombre().trim().toLowerCase());

        validateNombreRequerimiento(createDTO);

        log.info("Creando nuevo Requerimiento: {}", createDTO.getNombre());

        RequerimientoPeticionResponseDTO createdRequerimiento = requerimientoDAO.save(createDTO);
        log.info("Requerimiento creado exitosamente con ID: {}", createdRequerimiento.getId());

        return createdRequerimiento;
    }

    @Override
    @Transactional(readOnly = true)
    public RequerimientoPeticionResponseDTO getRequerimientoById(Long id) {

        if (id == null) {
            log.warn("El Id es obligatorio: {}", id);
            throw new BadRequestException("El id es obligatorio");
        }

        log.debug("Buscando Requerimiento por ID: {}", id);

        return requerimientoDAO.findById(id)
                .orElseThrow(() ->{
                    log.warn("Requerimiento no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Requerimiento no encontrado con ID: " + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public RequerimientoPeticionResponseDTO getRequerimientoByNombre(String nombre) {

        if (nombre == null) {
            log.warn("El nombre es obligatorio: {}", nombre);
            throw new BadRequestException("El nombre es obligatorio");
        }

        log.debug("Buscando Requerimiento por nombre: {}", nombre);

        return requerimientoDAO.findByNombre(nombre)
                .orElseThrow(() ->{
                    log.warn("Requerimiento no encontrado con nombre: {}", nombre);
                    return new ResourceNotFoundException("Requerimiento no encontrado con nombre: " + nombre);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequerimientoPeticionResponseDTO> getAllRequerimientos() {
        log.debug("Obteniendo todos los Requerimientos");
        return requerimientoDAO.findAll();
    }

    @Override
    @Transactional
    public RequerimientoPeticionResponseDTO updateRequerimiento(Long id, RequerimientoPeticionCreateUpdateDTO updateDTO) {

        if (id == null) {
            log.warn("El Id del requerimiento es obligatorio: {}", id);
            throw new BadRequestException("El id es obligatorio");
        }

        updateDTO.setNombre(updateDTO.getNombre().trim().toLowerCase());

        //Si se va actualizar el nombre, se verifica que no alla otro tipo con el mismo nombre
        validateNombreRequerimientoUpdate(updateDTO, id);
        log.info("Actualizando Peticion: {}", updateDTO.getNombre());

        RequerimientoPeticionResponseDTO updatedRequerimiento = requerimientoDAO.update(id, updateDTO)
                .orElseThrow(() -> {
                    log.warn("No se encontró el requerimiento con Id: {}", id);
                    return new ResourceNotFoundException("No se encotró el Requerimiento con ID: " + id);
                });
        log.info("Requerimiento actualizado exitosamente con ID: {}", updatedRequerimiento.getId());
        return  updatedRequerimiento;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequerimientoPeticionEntity> getAllByNombreIn(List<String> nombres) {
        return requerimientoDAO.findByNombreIn(nombres);
    }

    @Override
    @Transactional
    public void deleteRequerimiento(Long id) {

        if (id == null) {
            log.warn("El Id de requerimiento es obligatorio: {}", id);
            throw new BadRequestException("El id es obligatorio");
        }

        boolean deleted = requerimientoDAO.delete(id);

        if (!deleted) {
            log.warn("Intento de eliminar requerimiento inexistente ID: {}", id);
            throw new ResourceNotFoundException("Requerimiento no encontrado con ID: " + id);
        }

        log.info("Requerimiento eliminado correctamente ID: {}", id);
    }

    //VAlida que el nombre del requerimiento no este en uso
    private void validateNombreRequerimiento(RequerimientoPeticionCreateUpdateDTO createDTO) {
        if (requerimientoDAO.existsByNombreIgnoreCase(createDTO.getNombre())) {
            throw new ConflictException("El nombre del Requerimiento ya está en uso");
        }
    }

    //VAlida los datos del update
    private void validateNombreRequerimientoUpdate(RequerimientoPeticionCreateUpdateDTO updateDTO, Long id) {
        if (requerimientoDAO.existsByNombreIgnoreCaseAndIdNot(updateDTO.getNombre(), id)) {
            throw new ConflictException("El nombre del Requerimiento ya está en uso");
        }
    }
}
