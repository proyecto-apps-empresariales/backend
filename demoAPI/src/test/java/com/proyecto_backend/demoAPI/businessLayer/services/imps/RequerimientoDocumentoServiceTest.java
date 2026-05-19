package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoDocumentoResponseDTO;
import com.proyecto_backend.demoAPI.exceptions.ResourceNotFoundException;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.RequerimientoDocumentoDAO;
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
class RequerimientoDocumentoServiceTest {

    @Mock
    private RequerimientoDocumentoDAO requerimientoDocumentoDAO;

    @InjectMocks
    private RequerimientoDocumentoServiceImpl service;

    private RequerimientoDocumentoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new RequerimientoDocumentoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("Requerimiento prueba");
        responseDTO.setDescripcion("Descripcion prueba");
    }

    @Test
    void getRequerimientoById_ok() {
        when(requerimientoDocumentoDAO.findById(1L)).thenReturn(Optional.of(responseDTO));

        RequerimientoDocumentoResponseDTO result = service.getRequerimientoById(1L);

        assertNotNull(result);
        assertEquals("Requerimiento prueba", result.getNombre());
        verify(requerimientoDocumentoDAO).findById(1L);
    }

    @Test
    void getRequerimientoById_notFound() {
        when(requerimientoDocumentoDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getRequerimientoById(1L));

        verify(requerimientoDocumentoDAO).findById(1L);
    }

    @Test
    void getAllRequerimientos_ok() {
        when(requerimientoDocumentoDAO.findAll()).thenReturn(List.of(responseDTO));

        List<RequerimientoDocumentoResponseDTO> result = service.getAllRequerimientos();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(requerimientoDocumentoDAO).findAll();
    }
}