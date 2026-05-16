package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoDocumentoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoDocumentoResponseDTO;
import com.proyecto_backend.demoAPI.exceptions.BadRequestException;
import com.proyecto_backend.demoAPI.exceptions.ForbiddenException;
import com.proyecto_backend.demoAPI.exceptions.ResourceNotFoundException;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.RequerimientoDocumentoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.TipoDocumentoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.RequerimientoDocumentoEntity;
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
class TipoDocumentoServiceTest {

    @Mock
    private TipoDocumentoDAO tipoDocumentoDAO;

    @Mock
    private RequerimientoDocumentoDAO requerimientoDocumentoDAO;

    @InjectMocks
    private TipoDocumentoServiceImpl service;

    private TipoDocumentoCreateUpdateDTO dto;
    private TipoDocumentoResponseDTO responseDTO;
    private TipoDocumentoEntity entity;
    private RequerimientoDocumentoEntity requerimientoEntity;

    @BeforeEach
    void setUp() {
        dto = new TipoDocumentoCreateUpdateDTO();
        dto.setNombre("Documento");
        dto.setDescripcion("Documento prueba");
        dto.setRequerimientos(List.of("Firma Destinatario"));

        responseDTO = new TipoDocumentoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("Documento");

        entity = new TipoDocumentoEntity();
        entity.setId(1L);
        entity.setNombre("Documento");

        requerimientoEntity = new RequerimientoDocumentoEntity();
        requerimientoEntity.setId(1L);
        requerimientoEntity.setNombre("Firma Destinatario");
    }

    // ================= CREATE =================

    @Test
    void createTipoDocumento_ok() {
        when(tipoDocumentoDAO.existsByNombreIgnoreCase("Documento")).thenReturn(false);
        when(requerimientoDocumentoDAO.findEntityByNombre("Firma Destinatario")).thenReturn(Optional.of(requerimientoEntity));
        when(tipoDocumentoDAO.saveEntity(any())).thenReturn(responseDTO);

        TipoDocumentoResponseDTO result = service.createTipoDocumento(dto);

        assertNotNull(result);
        verify(tipoDocumentoDAO).saveEntity(any());
    }

    @Test
    void createTipoDocumento_nombreVacio() {
        dto.setNombre("");

        assertThrows(BadRequestException.class,
                () -> service.createTipoDocumento(dto));
    }

    @Test
    void createTipoDocumento_descripcionVacia() {
        dto.setDescripcion("");

        assertThrows(BadRequestException.class,
                () -> service.createTipoDocumento(dto));
    }

    @Test
    void createTipoDocumento_nombreDuplicado() {
        when(tipoDocumentoDAO.existsByNombreIgnoreCase("Documento")).thenReturn(true);

        assertThrows(RuntimeException.class,
                () -> service.createTipoDocumento(dto));
    }

    @Test
    void createTipoDocumento_requerimientoNoExiste() {
        when(tipoDocumentoDAO.existsByNombreIgnoreCase("Documento")).thenReturn(false);
        when(requerimientoDocumentoDAO.findEntityByNombre("Firma Destinatario")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.createTipoDocumento(dto));
    }

    // ================= GET =================

    @Test
    void getTipoDocumentoByID_ok() {
        when(tipoDocumentoDAO.findById(1L)).thenReturn(Optional.of(responseDTO));

        TipoDocumentoResponseDTO result = service.getTipoDocumentoByID(1L);

        assertNotNull(result);
    }

    @Test
    void getTipoDocumentoByID_notFound() {
        when(tipoDocumentoDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getTipoDocumentoByID(1L));
    }

    @Test
    void getAllTipoDocumento_ok() {
        when(tipoDocumentoDAO.findAll()).thenReturn(List.of(responseDTO));

        List<TipoDocumentoResponseDTO> result = service.getAllTipoDocumento();

        assertEquals(1, result.size());
    }

    // ================= UPDATE =================

    @Test
    void updateTipoDocumento_ok() {
        when(tipoDocumentoDAO.findById(1L)).thenReturn(Optional.of(responseDTO));
        when(tipoDocumentoDAO.findByNombre("Documento")).thenReturn(Optional.of(responseDTO));
        when(tipoDocumentoDAO.findEntityById(1L)).thenReturn(Optional.of(entity));
        when(requerimientoDocumentoDAO.findEntityByNombre("Firma Destinatario")).thenReturn(Optional.of(requerimientoEntity));
        when(tipoDocumentoDAO.saveEntity(any())).thenReturn(responseDTO);

        TipoDocumentoResponseDTO result = service.updateTipoDocumento(1L, dto);

        assertNotNull(result);
        verify(tipoDocumentoDAO).saveEntity(any());
    }

    @Test
    void updateTipoDocumento_notFound() {
        when(tipoDocumentoDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.updateTipoDocumento(1L, dto));
    }

    @Test
    void updateTipoDocumento_nombreVacio() {
        dto.setNombre("");
        when(tipoDocumentoDAO.findById(1L)).thenReturn(Optional.of(responseDTO));

        assertThrows(BadRequestException.class,
                () -> service.updateTipoDocumento(1L, dto));
    }

    @Test
    void updateTipoDocumento_descripcionVacia() {
        dto.setDescripcion("");
        when(tipoDocumentoDAO.findById(1L)).thenReturn(Optional.of(responseDTO));

        assertThrows(BadRequestException.class,
                () -> service.updateTipoDocumento(1L, dto));
    }

    @Test
    void updateTipoDocumento_nombreEnUsoPorOtro() {
        TipoDocumentoResponseDTO otro = new TipoDocumentoResponseDTO();
        otro.setId(99L);
        otro.setNombre("Documento");

        when(tipoDocumentoDAO.findById(1L)).thenReturn(Optional.of(responseDTO));
        when(tipoDocumentoDAO.findByNombre("Documento")).thenReturn(Optional.of(otro));

        assertThrows(ForbiddenException.class,
                () -> service.updateTipoDocumento(1L, dto));
    }

    @Test
    void updateTipoDocumento_entityNotFound() {
        when(tipoDocumentoDAO.findById(1L)).thenReturn(Optional.of(responseDTO));
        when(tipoDocumentoDAO.findByNombre("Documento")).thenReturn(Optional.of(responseDTO));
        when(tipoDocumentoDAO.findEntityById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.updateTipoDocumento(1L, dto));
    }

    // ================= DELETE =================

    @Test
    void deleteTipoDocumento_ok() {
        when(tipoDocumentoDAO.deleteById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> service.deleteTipoDocumento(1L));
    }

    @Test
    void deleteTipoDocumento_nullId() {
        assertThrows(BadRequestException.class,
                () -> service.deleteTipoDocumento(null));
    }

    @Test
    void deleteTipoDocumento_notFound() {
        when(tipoDocumentoDAO.deleteById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> service.deleteTipoDocumento(1L));
    }
}