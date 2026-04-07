package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IEstadoPeticionFlujoService;
import com.proyecto_backend.demoAPI.exceptions.BadRequestException;
import com.proyecto_backend.demoAPI.exceptions.ConflictException;
import com.proyecto_backend.demoAPI.exceptions.ResourceNotFoundException;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.EstadoPeticionFlujoDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EstadoPeticionFlujoServiceImpl implements IEstadoPeticionFlujoService {

    private final EstadoPeticionFlujoDAO estadoDAO;

    //Guardar Estado
    @Override
    @Transactional
    public EstadoPeticionFlujoResponseDTO createEstado(EstadoPeticionFlujoCreateUpdateDTO createDTO) {

        String nombreNormalizado = createDTO.getNombre().trim().toLowerCase();
        createDTO.setNombre(nombreNormalizado);

        //Valida el nombre del estado
        validateDuplicatedName(createDTO);

        log.info("Creando nuevo Estado: {}", createDTO.getNombre());

        EstadoPeticionFlujoResponseDTO createdEstado = estadoDAO.save(createDTO);
        log.info("Estado creado exitosamente con ID: {}", createdEstado.getId());

        return createdEstado;
    }

    //Buscar estado con Id
    @Override
    @Transactional(readOnly = true)
    public EstadoPeticionFlujoResponseDTO getEstadoById(Long id) {

        //Verifica que el id no sea nulo
        if (id == null) {
            log.warn("El Id es obligatorio: {}", id);
            throw new BadRequestException("El Id es obligatorio");
        }

        log.debug("Buscando Estado por ID: {}", id);

        return estadoDAO.findById(id)
                .orElseThrow(() -> {
                    log.warn("Estado no encontrado con ID: {}", id);

                    return new ResourceNotFoundException("Producto no con encontrado. Id: " + id);
                });
    }

    //Buscar Estado por le nombre
    @Override
    @Transactional(readOnly = true)
    public EstadoPeticionFlujoResponseDTO getEstadoByNombre(String nombre) {

        //Verifica que el id no sea nulo
        if (nombre == null) {
            log.warn("El nombre es obligatorio: {}", nombre);
            throw new BadRequestException("El nombre es obligatorio");
        }

        String nombreNormalizado = nombre.trim().toLowerCase();
        log.debug("Buscando Estado por Nombre: {}", nombreNormalizado);

        return estadoDAO.findByNombre(nombreNormalizado)
                .orElseThrow(() -> {
                    log.warn("Estado no encontrado con Nombre: {}", nombreNormalizado);
                    return new ResourceNotFoundException("Estado no encontrado con Nombre: " + nombreNormalizado);
                });

    }

    //Obtener todos los estados
    @Override
    @Transactional(readOnly = true)
    public List<EstadoPeticionFlujoResponseDTO> getAllEstados() {
        log.debug("Obteniendo todos los Estados");
        return estadoDAO.findAll();
    }

    //Actualizar Estado por Id
    @Override
    @Transactional
    public EstadoPeticionFlujoResponseDTO updateEstado(Long id, EstadoPeticionFlujoCreateUpdateDTO updateDTO) {
        log.info("Actualizando Estado ID: {}", id);

        //Verifica que el id no sea nulo
        if (id == null) {
            log.warn("El Id es obligatorio: {}", id);
            throw new BadRequestException("El Id es obligatorio");
        }

        EstadoPeticionFlujoResponseDTO updatedEstado = estadoDAO.update(id, updateDTO)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No se encotró el estado con ID: " + id)
                );

        log.info("Estado actualizado exitosamente ID: {}", id);
        return updatedEstado;
    }

    //Eliminar estado por Id
    @Override
    @Transactional
    public void deleteEstado(Long id) {

        if (id == null) {
            log.warn("El Id es obligatorio: {}", id);
            throw new BadRequestException("El Id es obligatorio");
        }

        // El DAO intenta eliminar
        boolean deleted = estadoDAO.deleteById(id);

        // 404 → no existía
        if (!deleted) {
            log.warn("Intento de eliminar estado inexistente ID: {}", id);
            throw new ResourceNotFoundException("Estado no encontrado con ID: " + id);
        }

        log.info("Estado eliminado correctamente ID: {}", id);
    }

    //Validar que el nombre no exista aún
    private void validateDuplicatedName(EstadoPeticionFlujoCreateUpdateDTO createDTO) {
        if (estadoDAO.existsByNombreIgnoreCare(createDTO.getNombre())) {
            throw new ConflictException("El nombre de Estado ya está en uso, no puede duplicarse");
        }
    }
}
