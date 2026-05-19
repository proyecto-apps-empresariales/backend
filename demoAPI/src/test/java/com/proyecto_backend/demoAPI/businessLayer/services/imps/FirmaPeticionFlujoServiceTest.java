package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaPeticionFlujoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IPeticionFlujoService;
import com.proyecto_backend.demoAPI.businessLayer.services.IUsuarioService;
import com.proyecto_backend.demoAPI.exceptions.BadRequestException;
import com.proyecto_backend.demoAPI.exceptions.ConflictException;
import com.proyecto_backend.demoAPI.exceptions.ResourceNotFoundException;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.FirmaPeticionFlujoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.PeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FirmaPeticionFlujoServiceTest {

    @Mock
    private FirmaPeticionFlujoDAO firmaPeticionDAO;

    @Mock
    private IUsuarioService usuarioService;

    @Mock
    private IPeticionFlujoService peticionService;

    @InjectMocks
    private FirmaPeticionFlujoServiceImpl service;

    private FirmaPeticionFlujoCreateDTO createDTO;
    private FirmaPeticionFlujoResponseDTO responseDTO;
    private Usuario usuario;
    private PeticionFlujoEntity peticion;

    @BeforeEach
    void setUp() {
        createDTO = new FirmaPeticionFlujoCreateDTO();
        createDTO.setUsuarioFirmador(1L);
        createDTO.setPeticion(10L);

        responseDTO = new FirmaPeticionFlujoResponseDTO();
        responseDTO.setId(99L);

        usuario = new Usuario();
        peticion = new PeticionFlujoEntity();
    }

    // ================= CREATE =================

    @Test
    void createFirmaPeticion_OK() {

        when(firmaPeticionDAO.existsByUsuarioIdAndPeticionId(1L, 10L))
                .thenReturn(false);

        when(usuarioService.buscarUsuarioEntityById(1L))
                .thenReturn(usuario);

        when(peticionService.getPeticionEntityById(10L))
                .thenReturn(peticion);

        when(firmaPeticionDAO.save(any(), any(), any()))
                .thenReturn(responseDTO);

        FirmaPeticionFlujoResponseDTO result = service.createFirmaPeticion(createDTO);

        assertNotNull(result);
        assertEquals(99L, result.getId());

        verify(firmaPeticionDAO).existsByUsuarioIdAndPeticionId(1L, 10L);
        verify(usuarioService).buscarUsuarioEntityById(1L);
        verify(peticionService).getPeticionEntityById(10L);
        verify(firmaPeticionDAO, times(1))
                .save(any(FirmaPeticionFlujoCreateDTO.class), any(Usuario.class), any(PeticionFlujoEntity.class));
    }

    @Test
    void createFirmaPeticion_Conflict() {

        when(firmaPeticionDAO.existsByUsuarioIdAndPeticionId(1L, 10L))
                .thenReturn(true);

        assertThrows(ConflictException.class,
                () -> service.createFirmaPeticion(createDTO));

        verify(firmaPeticionDAO, never()).save(any(), any(), any());
    }

    // ================= GET BY ID =================

    @Test
    void getFirmaPeticionById_OK() {

        when(firmaPeticionDAO.findById(5L))
                .thenReturn(Optional.of(responseDTO));

        var result = service.getFirmaPeticionById(5L);

        assertNotNull(result);
        assertEquals(99L, result.getId());
    }

    @Test
    void getFirmaPeticionById_BadRequest() {

        assertThrows(BadRequestException.class,
                () -> service.getFirmaPeticionById(null));
    }

    @Test
    void getFirmaPeticionById_NotFound() {

        when(firmaPeticionDAO.findById(5L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getFirmaPeticionById(5L));
    }

    // ================= GET ALL =================

    @Test
    void getAllFirmaPeticion_OK() {

        when(firmaPeticionDAO.findAll())
                .thenReturn(List.of(responseDTO));

        var result = service.getAllFirmaPeticion();

        assertEquals(1, result.size());
    }

    // ================= GET BY USUARIO =================

    @Test
    void getAllFirmaPeticionByUsuarioId_OK() {

        when(firmaPeticionDAO.findByUsuarioId(1L))
                .thenReturn(List.of(responseDTO));

        var result = service.getAllFirmaPeticionByUsuarioId(1L);

        assertEquals(1, result.size());
    }

    @Test
    void getAllFirmaPeticionByUsuarioId_BadRequest() {

        assertThrows(BadRequestException.class,
                () -> service.getAllFirmaPeticionByUsuarioId(null));
    }

    // ================= GET BY PETICION =================

    @Test
    void getAllByPeticionId_OK() {

        when(firmaPeticionDAO.findByPeticionId(10L))
                .thenReturn(List.of(responseDTO));

        var result = service.getAllByPeticionId(10L);

        assertEquals(1, result.size());
    }

    @Test
    void getAllByPeticionId_BadRequest() {

        assertThrows(BadRequestException.class,
                () -> service.getAllByPeticionId(null));
    }

    // ================= DELETE =================

    @Test
    void deleteFirmaPeticion_OK() {

        when(firmaPeticionDAO.delete(5L)).thenReturn(true);

        service.deleteFirmaPeticion(5L);

        verify(firmaPeticionDAO).delete(5L);
    }

    @Test
    void deleteFirmaPeticion_BadRequest() {

        assertThrows(BadRequestException.class,
                () -> service.deleteFirmaPeticion(null));
    }

    @Test
    void deleteFirmaPeticion_NotFound() {

        when(firmaPeticionDAO.delete(5L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> service.deleteFirmaPeticion(5L));
    }
}

