package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import com.proyecto_backend.demoAPI.businessLayer.dtos.HistorialPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.exceptions.BadRequestException;
import com.proyecto_backend.demoAPI.exceptions.ResourceNotFoundException;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.HistorialPeticionFlujoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.HistorialPeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.PeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;
import org.junit.jupiter.api.DisplayName;
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
@DisplayName("HistorialPeticionFlujo - Unit Tests")
class HistorialPeticionFlujoServiceTest {

    // 👉 Simulamos DAO (sin BD real)
    @Mock
    private HistorialPeticionFlujoDAO historialDAO;

    // 👉 Inyecta el mock dentro del service
    @InjectMocks
    private HistorialPeticionFlujoServiceImpl historialService;


    // =========================================================
    // CREATE HISTORIAL
    // =========================================================

    @Test
    @DisplayName("Crear Historial -> Created_201 cuando entity es válida")
    void createHistorial_Created() {

        HistorialPeticionFlujoEntity entity = new HistorialPeticionFlujoEntity();
        entity.setDescripcion("Cambio de estado");
        entity.setPeticion(new PeticionFlujoEntity());
        entity.setUsuarioEditor(new Usuario());

        HistorialPeticionFlujoResponseDTO response = new HistorialPeticionFlujoResponseDTO();
        response.setId(1L);

        when(historialDAO.save(any())).thenReturn(response);

        HistorialPeticionFlujoResponseDTO result = historialService.createHistorial(entity);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(historialDAO).save(any(HistorialPeticionFlujoEntity.class));
    }

    @Test
    @DisplayName("Crear Historial -> BadRequest_400 cuando entity es null")
    void createHistorial_NullEntity() {
        assertThrows(BadRequestException.class,
                () -> historialService.createHistorial(null));
    }

    @Test
    @DisplayName("Crear Historial -> BadRequest_400 cuando peticion es null")
    void createHistorial_PeticionNull() {

        HistorialPeticionFlujoEntity entity = new HistorialPeticionFlujoEntity();
        entity.setDescripcion("desc");
        entity.setUsuarioEditor(new Usuario());

        assertThrows(BadRequestException.class,
                () -> historialService.createHistorial(entity));
    }

    @Test
    @DisplayName("Crear Historial -> BadRequest_400 cuando descripcion es null")
    void createHistorial_DescripcionNull() {

        HistorialPeticionFlujoEntity entity = new HistorialPeticionFlujoEntity();
        entity.setPeticion(new PeticionFlujoEntity());
        entity.setUsuarioEditor(new Usuario());

        assertThrows(BadRequestException.class,
                () -> historialService.createHistorial(entity));
    }

    @Test
    @DisplayName("Crear Historial -> BadRequest_400 cuando descripcion es blank")
    void createHistorial_DescripcionBlank() {

        HistorialPeticionFlujoEntity entity = new HistorialPeticionFlujoEntity();
        entity.setDescripcion("   ");
        entity.setPeticion(new PeticionFlujoEntity());
        entity.setUsuarioEditor(new Usuario());

        assertThrows(BadRequestException.class,
                () -> historialService.createHistorial(entity));
    }

    @Test
    @DisplayName("Crear Historial -> BadRequest_400 cuando usuarioEditor es null")
    void createHistorial_UsuarioNull() {

        HistorialPeticionFlujoEntity entity = new HistorialPeticionFlujoEntity();
        entity.setDescripcion("desc");
        entity.setPeticion(new PeticionFlujoEntity());

        assertThrows(BadRequestException.class,
                () -> historialService.createHistorial(entity));
    }

    @Test
    @DisplayName("Crear Historial -> Error 500 cuando el DAO falla")
    void createHistorial_InternalServerError() {

        HistorialPeticionFlujoEntity entity = new HistorialPeticionFlujoEntity();
        entity.setDescripcion("desc");
        entity.setPeticion(new PeticionFlujoEntity());
        entity.setUsuarioEditor(new Usuario());

        when(historialDAO.save(any()))
                .thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class,
                () -> historialService.createHistorial(entity));
    }


    // =========================================================
    // GET BY ID
    // =========================================================

    @Test
    @DisplayName("Buscar Historial por ID -> Ok_200")
    void getHistorialById_Ok() {

        HistorialPeticionFlujoResponseDTO response = new HistorialPeticionFlujoResponseDTO();
        response.setId(1L);

        when(historialDAO.findById(1L)).thenReturn(Optional.of(response));

        HistorialPeticionFlujoResponseDTO result = historialService.getHistorialById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Buscar Historial por ID -> BadRequest_400 cuando id es null")
    void getHistorialById_NullId() {
        assertThrows(BadRequestException.class,
                () -> historialService.getHistorialById(null));
    }

    @Test
    @DisplayName("Buscar Historial por ID -> NotFound_404")
    void getHistorialById_NotFound() {
        when(historialDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> historialService.getHistorialById(1L));
    }

    @Test
    @DisplayName("Buscar Historial por ID -> Error 500 cuando DAO falla")
    void getHistorialById_InternalServerError() {
        when(historialDAO.findById(1L))
                .thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class,
                () -> historialService.getHistorialById(1L));
    }


    // =========================================================
    // GET HISTORIAL BY PETICION ID
    // =========================================================

    @Test
    @DisplayName("Buscar historial por Peticion ID -> Ok_200")
    void getHistorialByPeticionId_Ok() {

        when(historialDAO.findAllByPeticionIdOrderByFecha(1L))
                .thenReturn(List.of(
                        new HistorialPeticionFlujoResponseDTO(),
                        new HistorialPeticionFlujoResponseDTO()
                ));

        List<HistorialPeticionFlujoResponseDTO> result =
                historialService.getHistorialCompletoByPeticionId(1L);

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Buscar historial por Peticion ID -> BadRequest_400")
    void getHistorialByPeticionId_NullId() {
        assertThrows(BadRequestException.class,
                () -> historialService.getHistorialCompletoByPeticionId(null));
    }

    @Test
    @DisplayName("Buscar historial por Peticion ID -> Error 500 cuando DAO falla")
    void getHistorialByPeticionId_InternalServerError() {

        when(historialDAO.findAllByPeticionIdOrderByFecha(any()))
                .thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class,
                () -> historialService.getHistorialCompletoByPeticionId(1L));
    }


    // =========================================================
    // GET HISTORIAL BY PETICION NOMBRE
    // =========================================================

    @Test
    @DisplayName("Buscar historial por nombre -> Ok_200")
    void getHistorialByNombre_Ok() {

        when(historialDAO.findAllByPeticionNombreOrderByFecha("test"))
                .thenReturn(List.of(new HistorialPeticionFlujoResponseDTO()));

        List<HistorialPeticionFlujoResponseDTO> result =
                historialService.getHistorialCompletoByPeticionNombre("test");

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Buscar historial por nombre -> BadRequest_400 cuando nombre null")
    void getHistorialByNombre_Null() {
        assertThrows(BadRequestException.class,
                () -> historialService.getHistorialCompletoByPeticionNombre(null));
    }

    @Test
    @DisplayName("Buscar historial por nombre -> BadRequest_400 cuando nombre blank")
    void getHistorialByNombre_Blank() {
        assertThrows(BadRequestException.class,
                () -> historialService.getHistorialCompletoByPeticionNombre("   "));
    }

    @Test
    @DisplayName("Buscar historial por nombre -> Error 500 cuando DAO falla")
    void getHistorialByNombre_InternalServerError() {

        when(historialDAO.findAllByPeticionNombreOrderByFecha(any()))
                .thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class,
                () -> historialService.getHistorialCompletoByPeticionNombre("test"));
    }

}