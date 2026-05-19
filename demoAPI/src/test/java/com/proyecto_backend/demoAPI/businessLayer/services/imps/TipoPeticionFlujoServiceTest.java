package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoPeticionFlujoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IRequerimientoPeticionService;
import com.proyecto_backend.demoAPI.exceptions.BadRequestException;
import com.proyecto_backend.demoAPI.exceptions.ConflictException;
import com.proyecto_backend.demoAPI.exceptions.ResourceNotFoundException;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.TipoPeticionFlujoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.RequerimientoPeticionEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoPeticionFlujoEntity;
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
class TipoPeticionFlujoServiceTest {

    @Mock
    private TipoPeticionFlujoDAO tipoPeticionDAO;

    @Mock
    private IRequerimientoPeticionService requerimientoService;

    @InjectMocks
    private TipoPeticionFlujoServiceImpl service;

    private TipoPeticionFlujoCreateUpdateDTO createDTO;
    private TipoPeticionFlujoResponseDTO responseDTO;
    private TipoPeticionFlujoEntity entity;
    private RequerimientoPeticionEntity reqEntity;

    @BeforeEach
    void setUp() {
        createDTO = new TipoPeticionFlujoCreateUpdateDTO();
        createDTO.setNombre("Registro");
        createDTO.setRequerimientos(List.of("doc1"));

        responseDTO = new TipoPeticionFlujoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("registro");

        entity = new TipoPeticionFlujoEntity();
        entity.setId(1L);

        reqEntity = new RequerimientoPeticionEntity();
        reqEntity.setId(1L);
    }

    // ================= CREATE =================

    @Test
    void createTipoPeticionFlujo_OK() {
        when(tipoPeticionDAO.existsByNombreIgnoreCase("registro")).thenReturn(false);
        when(requerimientoService.getAllByNombreIn(any())).thenReturn(List.of(reqEntity));
        when(tipoPeticionDAO.save(any(), any())).thenReturn(responseDTO);

        TipoPeticionFlujoResponseDTO result = service.createTipoPeticionFlujo(createDTO);

        assertNotNull(result);
        verify(tipoPeticionDAO).save(any(), any());
    }

    @Test
    void createTipoPeticionFlujo_conflictNombre() {
        when(tipoPeticionDAO.existsByNombreIgnoreCase("registro")).thenReturn(true);

        assertThrows(ConflictException.class,
                () -> service.createTipoPeticionFlujo(createDTO));
    }

    @Test
    void createTipoPeticionFlujo_requerimientosNoExisten() {
        when(tipoPeticionDAO.existsByNombreIgnoreCase("registro")).thenReturn(false);
        when(requerimientoService.getAllByNombreIn(any())).thenReturn(List.of());

        assertThrows(BadRequestException.class,
                () -> service.createTipoPeticionFlujo(createDTO));
    }

    // ================= GET BY ID =================

    @Test
    void getTipoPeticionById_OK() {
        when(tipoPeticionDAO.findById(1L)).thenReturn(Optional.of(responseDTO));

        assertNotNull(service.getTipoPeticionFlujoById(1L));
    }

    @Test
    void getTipoPeticionById_null() {
        assertThrows(BadRequestException.class,
                () -> service.getTipoPeticionFlujoById(null));
    }

    @Test
    void getTipoPeticionById_notFound() {
        when(tipoPeticionDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getTipoPeticionFlujoById(1L));
    }

    // ================= GET ENTITY =================

    @Test
    void getTipoPeticionEntity_OK() {
        when(tipoPeticionDAO.findTipoPeticionEntityById(1L)).thenReturn(Optional.of(entity));

        assertNotNull(service.getTipoPeticionEntityById(1L));
    }

    @Test
    void getTipoPeticionEntity_nullId() {
        assertThrows(BadRequestException.class,
                () -> service.getTipoPeticionEntityById(null));
    }

    @Test
    void getTipoPeticionEntity_notFound() {
        when(tipoPeticionDAO.findTipoPeticionEntityById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getTipoPeticionEntityById(1L));
    }

    // ================= GET BY NOMBRE =================

    @Test
    void getTipoPeticionByNombre_OK() {
        when(tipoPeticionDAO.findByNombre("registro")).thenReturn(Optional.of(responseDTO));

        assertNotNull(service.getTipoPeticionFlujoByNombre("registro"));
    }

    @Test
    void getTipoPeticionByNombre_null() {
        assertThrows(BadRequestException.class,
                () -> service.getTipoPeticionFlujoByNombre(null));
    }

    @Test
    void getTipoPeticionByNombre_notFound() {
        when(tipoPeticionDAO.findByNombre("registro")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getTipoPeticionFlujoByNombre("registro"));
    }

    // ================= GET ALL =================

    @Test
    void getAll_OK() {
        when(tipoPeticionDAO.findAll()).thenReturn(List.of(responseDTO));

        assertFalse(service.getAllTipoPeticionFlujos().isEmpty());
    }

    // ================= UPDATE =================

    @Test
    void updateTipoPeticion_OK() {
        when(tipoPeticionDAO.existsByNombreIgnoreCaseAndIdNot("registro", 1L)).thenReturn(false);
        when(requerimientoService.getAllByNombreIn(any())).thenReturn(List.of(reqEntity));
        when(tipoPeticionDAO.update(eq(1L), any(), any())).thenReturn(Optional.of(responseDTO));

        assertNotNull(service.updateTipoPeticionFlujo(1L, createDTO));
    }

    @Test
    void updateTipoPeticion_nullId() {
        assertThrows(BadRequestException.class,
                () -> service.updateTipoPeticionFlujo(null, createDTO));
    }

    @Test
    void updateTipoPeticion_conflictNombre() {
        when(tipoPeticionDAO.existsByNombreIgnoreCaseAndIdNot("registro", 1L)).thenReturn(true);

        assertThrows(ConflictException.class,
                () -> service.updateTipoPeticionFlujo(1L, createDTO));
    }

    @Test
    void updateTipoPeticion_requerimientosInvalidos() {
        when(tipoPeticionDAO.existsByNombreIgnoreCaseAndIdNot("registro", 1L)).thenReturn(false);
        when(requerimientoService.getAllByNombreIn(any())).thenReturn(List.of());

        assertThrows(BadRequestException.class,
                () -> service.updateTipoPeticionFlujo(1L, createDTO));
    }

    @Test
    void updateTipoPeticion_notFound() {
        when(tipoPeticionDAO.existsByNombreIgnoreCaseAndIdNot("registro", 1L)).thenReturn(false);
        when(requerimientoService.getAllByNombreIn(any())).thenReturn(List.of(reqEntity));
        when(tipoPeticionDAO.update(eq(1L), any(), any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.updateTipoPeticionFlujo(1L, createDTO));
    }

    // ================= DELETE =================

    @Test
    void delete_OK() {
        when(tipoPeticionDAO.delete(1L)).thenReturn(true);

        assertDoesNotThrow(() -> service.deleteTipoPeticionFlujo(1L));
    }

    @Test
    void delete_nullId() {
        assertThrows(BadRequestException.class,
                () -> service.deleteTipoPeticionFlujo(null));
    }

    @Test
    void delete_notFound() {
        when(tipoPeticionDAO.delete(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> service.deleteTipoPeticionFlujo(1L));
    }
}