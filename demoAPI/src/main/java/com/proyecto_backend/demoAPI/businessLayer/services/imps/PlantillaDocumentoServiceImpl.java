package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PlantillaDocumentoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PlantillaDocumentoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IPlantillaDocumentoService;
import com.proyecto_backend.demoAPI.exceptions.BadRequestException;
import com.proyecto_backend.demoAPI.exceptions.ResourceNotFoundException;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.PlantillaDocumentoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.TipoDocumentoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.PlantillaDocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoDocumentoEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantillaDocumentoServiceImpl implements IPlantillaDocumentoService {

    public final PlantillaDocumentoDAO plantillaDocumentoDAO;
    public final TipoDocumentoDAO tipoDocumentoDAO;

    @Override
    @Transactional
    public PlantillaDocumentoResponseDTO createPlantilla(PlantillaDocumentoCreateUpdateDTO dto){

        // Validar Campos
        if(dto.getDescripcion().isEmpty() || dto.getDescripcion().isBlank()){
            throw new BadRequestException("La descripcion de la plantilla no puede estar vacia");
        }

        if(dto.getArchivoUrl().isEmpty() || dto.getArchivoUrl().isBlank()){
            throw new BadRequestException("El archivo de la plantilla no puede estar vacio");
        }

        if(dto.getTipoDocumento().isEmpty() || dto.getTipoDocumento().isBlank()){
            throw new BadRequestException("El tipo de documento no puede estar vacio");
        }

        //Buscar la entidad tipo documento
        TipoDocumentoEntity tipoDocumento=tipoDocumentoDAO.findByNombreEntidad(dto.getTipoDocumento())
                .orElseThrow(() -> new ResourceNotFoundException("El tipo de documento no existe"));

        PlantillaDocumentoEntity plantilla= new PlantillaDocumentoEntity();
        plantilla.setArchivoUrl(dto.getArchivoUrl());
        plantilla.setDescripcion(dto.getDescripcion());
        plantilla.setTipoDocumento(tipoDocumento);

        return plantillaDocumentoDAO.save(plantilla);
    }

    @Override
    @Transactional
    public PlantillaDocumentoResponseDTO updatePlantilla(Long id, PlantillaDocumentoCreateUpdateDTO dto){

        // Validar Campos
        if(dto.getDescripcion().isEmpty() || dto.getDescripcion().isBlank()){
            throw new BadRequestException("La descripcion de la plantilla no puede estar vacia");
        }

        if(dto.getArchivoUrl().isEmpty() || dto.getArchivoUrl().isBlank()){
            throw new BadRequestException("El archivo de la plantilla no puede estar vacio");
        }

        if(dto.getTipoDocumento().isEmpty() || dto.getTipoDocumento().isBlank()){
            throw new BadRequestException("El tipo de documento no puede estar vacio");
        }

        //Buscar la entidad tipo documento
        TipoDocumentoEntity tipoDocumento=tipoDocumentoDAO.findByNombreEntidad(dto.getTipoDocumento())
                .orElseThrow(() -> new ResourceNotFoundException("El tipo de documento no existe"));

        //Buscar la entidad plantilla
        PlantillaDocumentoEntity plantilla= plantillaDocumentoDAO.findEntityById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plantilla no encontrada"));

        //Editar campos con informacion del dto
        plantilla.setTipoDocumento(tipoDocumento);
        plantilla.setDescripcion(dto.getDescripcion());
        plantilla.setArchivoUrl(dto.getArchivoUrl());

        //Guardar
        return plantillaDocumentoDAO.save(plantilla);
    }

    @Override
    public PlantillaDocumentoResponseDTO getPlantillaById(Long id){
        return plantillaDocumentoDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La plantilla no se encontro"));
    }

    @Override
    public List<PlantillaDocumentoResponseDTO> getAllPlantillas(){
        return plantillaDocumentoDAO.findAll();
    }
}
