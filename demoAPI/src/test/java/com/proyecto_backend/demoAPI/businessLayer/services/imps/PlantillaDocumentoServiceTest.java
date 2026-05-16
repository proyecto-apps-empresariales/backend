package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PlantillaDocumentoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PlantillaDocumentoResponseDTO;
import com.proyecto_backend.demoAPI.exceptions.BadRequestException;
import com.proyecto_backend.demoAPI.exceptions.ResourceNotFoundException;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.PlantillaDocumentoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.TipoDocumentoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.PlantillaDocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoDocumentoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlantillaDocumentoServiceTest {

    @Mock private PlantillaDocumentoDAO plantillaDAO;
    @Mock private TipoDocumentoDAO tipoDocumentoDAO;

    @InjectMocks
    private PlantillaDocumentoServiceImpl service;

    private PlantillaDocumentoCreateUpdateDTO dto;
    private PlantillaDocumentoResponseDTO responseDTO;
    private TipoDocumentoEntity tipoDocumento;
    private PlantillaDocumentoEntity plantillaEntity;

    @BeforeEach
    void setUp() {
        dto = new PlantillaDocumentoCreateUpdateDTO(
                "descripcion plantilla",
                "url/archivo.pdf",
                "CONTRATO"
        );

        responseDTO = new PlantillaDocumentoResponseDTO(
                1L,
                "descripcion plantilla",
                "url/archivo.pdf",
                "CONTRATO"
        );

        tipoDocumento = new TipoDocumentoEntity();
        plantillaEntity = new PlantillaDocumentoEntity();
    }

    // ================= CREATE =================

    @Test
    void createPlantilla_ok(){
        when(tipoDocumentoDAO.findByNombreEntidad("CONTRATO"))
                .thenReturn(Optional.of(tipoDocumento));
        when(plantillaDAO.save(any(PlantillaDocumentoEntity.class)))
                .thenReturn(responseDTO);

        PlantillaDocumentoResponseDTO result = service.createPlantilla(dto);

        assertNotNull(result);
        verify(plantillaDAO).save(any(PlantillaDocumentoEntity.class));
    }

    @Test
    void createPlantilla_descripcionVacia(){
        dto.setDescripcion(" ");
        assertThrows(BadRequestException.class, () -> service.createPlantilla(dto));
    }

    @Test
    void createPlantilla_archivoVacio(){
        dto.setArchivoUrl(" ");
        assertThrows(BadRequestException.class, () -> service.createPlantilla(dto));
    }

    @Test
    void createPlantilla_tipoDocumentoVacio(){
        dto.setTipoDocumento(" ");
        assertThrows(BadRequestException.class, () -> service.createPlantilla(dto));
    }

    @Test
    void createPlantilla_tipoDocumentoNoExiste(){
        when(tipoDocumentoDAO.findByNombreEntidad("CONTRATO"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.createPlantilla(dto));
    }

    // ================= UPDATE =================

    @Test
    void updatePlantilla_ok(){
        when(tipoDocumentoDAO.findByNombreEntidad("CONTRATO"))
                .thenReturn(Optional.of(tipoDocumento));
        when(plantillaDAO.findEntityById(1L))
                .thenReturn(Optional.of(plantillaEntity));
        when(plantillaDAO.save(any(PlantillaDocumentoEntity.class)))
                .thenReturn(responseDTO);

        PlantillaDocumentoResponseDTO result = service.updatePlantilla(1L, dto);

        assertNotNull(result);
        verify(plantillaDAO).save(any(PlantillaDocumentoEntity.class));
    }

    @Test
    void updatePlantilla_tipoDocumentoNoExiste(){
        when(tipoDocumentoDAO.findByNombreEntidad("CONTRATO"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.updatePlantilla(1L, dto));
    }

    @Test
    void updatePlantilla_plantillaNoExiste(){
        when(tipoDocumentoDAO.findByNombreEntidad("CONTRATO"))
                .thenReturn(Optional.of(tipoDocumento));
        when(plantillaDAO.findEntityById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.updatePlantilla(1L, dto));
    }

    // ================= GET BY ID =================

    @Test
    void getPlantillaById_ok(){
        when(plantillaDAO.findById(1L)).thenReturn(Optional.of(responseDTO));
        assertNotNull(service.getPlantillaById(1L));
    }

    @Test
    void getPlantillaById_notFound(){
        when(plantillaDAO.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> service.getPlantillaById(1L));
    }

    // ================= GET ALL =================

    @Test
    void getAllPlantillas_ok(){
        when(plantillaDAO.findAll()).thenReturn(List.of(responseDTO));
        assertFalse(service.getAllPlantillas().isEmpty());
    }
}