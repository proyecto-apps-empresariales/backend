package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoPeticionCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoPeticionResponseDTO;
import com.proyecto_backend.demoAPI.exceptions.BadRequestException;
import com.proyecto_backend.demoAPI.exceptions.ConflictException;
import com.proyecto_backend.demoAPI.exceptions.ResourceNotFoundException;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.RequerimientoPeticionDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.RequerimientoPeticionEntity;
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
class RequerimientoPeticionServiceTest {

    @Mock
    private RequerimientoPeticionDAO requerimientoDAO;

    @InjectMocks
    private RequerimientoPeticionServiceImpl service;

    private RequerimientoPeticionCreateUpdateDTO createDTO;
    private RequerimientoPeticionResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        createDTO = new RequerimientoPeticionCreateUpdateDTO();
        createDTO.setNombre("  Requerimiento Test  ");

        responseDTO = new RequerimientoPeticionResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("requerimiento test");
    }

    // ================= CREATE =================

    @Test
    void createRequerimiento_ok() {
        when(requerimientoDAO.existsByNombreIgnoreCase("requerimiento test")).thenReturn(false);
        when(requerimientoDAO.save(any(RequerimientoPeticionCreateUpdateDTO.class))).thenReturn(responseDTO);

        RequerimientoPeticionResponseDTO result = service.createRequerimiento(createDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(requerimientoDAO).save(any());
    }

    @Test
    void createRequerimiento_conflict() {
        when(requerimientoDAO.existsByNombreIgnoreCase("requerimiento test")).thenReturn(true);

        assertThrows(ConflictException.class,
                () -> service.createRequerimiento(createDTO));

        verify(requerimientoDAO, never()).save(any());
    }

    // ================= GET BY ID =================

    @Test
    void getRequerimientoById_ok() {
        when(requerimientoDAO.findById(1L)).thenReturn(Optional.of(responseDTO));

        RequerimientoPeticionResponseDTO result = service.getRequerimientoById(1L);

        assertNotNull(result);
        verify(requerimientoDAO).findById(1L);
    }

    @Test
    void getRequerimientoById_nullId() {
        assertThrows(BadRequestException.class,
                () -> service.getRequerimientoById(null));
    }

    @Test
    void getRequerimientoById_notFound() {
        when(requerimientoDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getRequerimientoById(1L));
    }

    // ================= GET BY NOMBRE =================

    @Test
    void getRequerimientoByNombre_ok() {
        when(requerimientoDAO.findByNombre("test")).thenReturn(Optional.of(responseDTO));

        RequerimientoPeticionResponseDTO result = service.getRequerimientoByNombre("test");

        assertNotNull(result);
        verify(requerimientoDAO).findByNombre("test");
    }

    @Test
    void getRequerimientoByNombre_null() {
        assertThrows(BadRequestException.class,
                () -> service.getRequerimientoByNombre(null));
    }

    @Test
    void getRequerimientoByNombre_notFound() {
        when(requerimientoDAO.findByNombre("test")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getRequerimientoByNombre("test"));
    }

    // ================= GET ALL =================

    @Test
    void getAllRequerimientos_ok() {
        when(requerimientoDAO.findAll()).thenReturn(List.of(responseDTO));

        List<RequerimientoPeticionResponseDTO> result = service.getAllRequerimientos();

        assertEquals(1, result.size());
        verify(requerimientoDAO).findAll();
    }

    // ================= UPDATE =================

    @Test
    void updateRequerimiento_ok() {
        when(requerimientoDAO.existsByNombreIgnoreCaseAndIdNot("requerimiento test", 1L)).thenReturn(false);
        when(requerimientoDAO.update(eq(1L), any())).thenReturn(Optional.of(responseDTO));

        RequerimientoPeticionResponseDTO result = service.updateRequerimiento(1L, createDTO);

        assertNotNull(result);
        verify(requerimientoDAO).update(eq(1L), any());
    }

    @Test
    void updateRequerimiento_nullId() {
        assertThrows(BadRequestException.class,
                () -> service.updateRequerimiento(null, createDTO));
    }

    @Test
    void updateRequerimiento_conflictNombre() {
        when(requerimientoDAO.existsByNombreIgnoreCaseAndIdNot("requerimiento test", 1L)).thenReturn(true);

        assertThrows(ConflictException.class,
                () -> service.updateRequerimiento(1L, createDTO));
    }

    @Test
    void updateRequerimiento_notFound() {
        when(requerimientoDAO.existsByNombreIgnoreCaseAndIdNot("requerimiento test", 1L)).thenReturn(false);
        when(requerimientoDAO.update(eq(1L), any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.updateRequerimiento(1L, createDTO));
    }

    // ================= GET BY NOMBRES =================

    @Test
    void getAllByNombreIn_ok() {
        when(requerimientoDAO.findByNombreIn(anyList()))
                .thenReturn(List.of(new RequerimientoPeticionEntity()));

        List<RequerimientoPeticionEntity> result =
                service.getAllByNombreIn(List.of("a", "b"));

        assertEquals(1, result.size());
        verify(requerimientoDAO).findByNombreIn(anyList());
    }

    // ================= DELETE =================

    @Test
    void deleteRequerimiento_ok() {
        when(requerimientoDAO.delete(1L)).thenReturn(true);

        assertDoesNotThrow(() -> service.deleteRequerimiento(1L));

        verify(requerimientoDAO).delete(1L);
    }

    @Test
    void deleteRequerimiento_nullId() {
        assertThrows(BadRequestException.class,
                () -> service.deleteRequerimiento(null));
    }

    @Test
    void deleteRequerimiento_notFound() {
        when(requerimientoDAO.delete(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> service.deleteRequerimiento(1L));
    }
}