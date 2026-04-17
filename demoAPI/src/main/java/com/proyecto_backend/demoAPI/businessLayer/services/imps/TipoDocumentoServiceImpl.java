package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoDocumentoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoDocumentoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.ITipoDocumentoService;
import com.proyecto_backend.demoAPI.exceptions.BadRequestException;
import com.proyecto_backend.demoAPI.exceptions.ConflictException;
import com.proyecto_backend.demoAPI.exceptions.ForbiddenException;
import com.proyecto_backend.demoAPI.exceptions.ResourceNotFoundException;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.RequerimientoDocumentoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.TipoDocumentoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.RequerimientoDocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoDocumentoEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoDocumentoServiceImpl implements ITipoDocumentoService {

    public final TipoDocumentoDAO tipoDocumentoDAO;
    public final RequerimientoDocumentoDAO requerimientoDocumentoDAO;

    @Override
    @Transactional
    public TipoDocumentoResponseDTO createTipoDocumento(TipoDocumentoCreateUpdateDTO createDTO){

        // Validar Campos
        if(createDTO.getNombre() == null || createDTO.getNombre().isBlank()){
            throw new BadRequestException("El nombre del tipo de documento no puede estar vacio");
        }

        if(createDTO.getDescripcion().isEmpty() || createDTO.getDescripcion().isBlank()){
            throw new BadRequestException("El nombre del tipo de documento no puede estar vacio");
        }

        // Validar que no exista otro tipo de documento con el mismo nombre
        if(tipoDocumentoDAO.existsByNombreIgnoreCase(createDTO.getNombre())){
            throw new RuntimeException("El tipo de documento con nombre: "+ createDTO.getNombre()+" ya existe");
        }

        //Construir entidad base
        TipoDocumentoEntity entity = new TipoDocumentoEntity();
        entity.setNombre(createDTO.getNombre());
        entity.setDescripcion(createDTO.getDescripcion());

        //Resolver y asociar requerimientos si vienen en el DTO
        if (createDTO.getRequerimientos() != null && !createDTO.getRequerimientos().isEmpty()) {
            List<RequerimientoDocumentoEntity> requerimientos = resolverRequerimientos(createDTO.getRequerimientos());
            entity.setRequerimientos(requerimientos);
        } else {
            entity.setRequerimientos(new ArrayList<>());
        }

        //Guardar entidad
        return tipoDocumentoDAO.saveEntity(entity);
    }

    //Metodo para transformar los requerimientos de nombre a requerimientoDocumento entity
    private List<RequerimientoDocumentoEntity> resolverRequerimientos(List<String> nombres) {
        return new ArrayList<>(nombres.stream()
                .map(nombre -> requerimientoDocumentoDAO.findEntityByNombre(nombre)
                        .orElseThrow(() -> new RuntimeException("Requerimiento no encontrado: " + nombre)))
                .toList());
    }

    @Override
    public TipoDocumentoResponseDTO getTipoDocumentoByID(Long id){
        return tipoDocumentoDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de Documento no encontrado con id: " + id));
    }

    @Override
    public List<TipoDocumentoResponseDTO> getAllTipoDocumento(){
        return tipoDocumentoDAO.findAll();
    }

    @Override
    @Transactional
    public TipoDocumentoResponseDTO updateTipoDocumento(Long id, TipoDocumentoCreateUpdateDTO dto){

        // Verificar que el tipo de documento existe
        if (tipoDocumentoDAO.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("El tipo de documento a editar no existe");
        }

        //Validar que nombre y descripción no estén vacíos
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new BadRequestException("El nombre del tipo de documento no puede estar vacío");
        }
        if (dto.getDescripcion() == null || dto.getDescripcion().isBlank()) {
            throw new BadRequestException("La descripción del tipo de documento no puede estar vacía");
        }

        //Que el nuevo nombre no lo tenga otro tipo de documento
        tipoDocumentoDAO.findByNombre(dto.getNombre()).ifPresent(existente -> {
            if (!existente.getId().equals(id)) {
                throw new ForbiddenException("Ya existe otro tipo de documento con el nombre: " + dto.getNombre());
            }
        });

        //Buscar la entidad a editar
        TipoDocumentoEntity entity = tipoDocumentoDAO.findEntityById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de documento no encontrado con id: " + id));

        //Actualizar campos
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());

        //Actualizar requerimientos si vienen
        if (dto.getRequerimientos() != null) {
            List<RequerimientoDocumentoEntity> requerimientos = resolverRequerimientos(dto.getRequerimientos());
            entity.setRequerimientos(requerimientos);
        }

        //Actualizar
        return tipoDocumentoDAO.saveEntity(entity);
    }

    //Eliminar tipo de documento por Id
    @Override
    @Transactional
    public void deleteTipoDocumento(Long id) {

        if (id == null) {
            throw new BadRequestException("El Id es obligatorio");
        }

        // El DAO intenta eliminar
        boolean deleted = tipoDocumentoDAO.deleteById(id);

        // 404 → no existía
        if (!deleted) {
            throw new ResourceNotFoundException("Estado no encontrado con ID: " + id);
        }

    }
}
