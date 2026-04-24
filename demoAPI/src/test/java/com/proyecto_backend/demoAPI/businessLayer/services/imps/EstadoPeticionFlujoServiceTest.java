package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.exceptions.BadRequestException;
import com.proyecto_backend.demoAPI.exceptions.ConflictException;
import com.proyecto_backend.demoAPI.exceptions.ResourceNotFoundException;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.EstadoPeticionFlujoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.EstadoPeticionFlujoEntity;
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
@DisplayName("EstadoPeticion - Unit Tests")
class EstadoPeticionFlujoServiceTest {

    // 👉 Simula el DAO (no se usa BD real)
    @Mock
    private EstadoPeticionFlujoDAO estadoPeticionDAO;

    // 👉 Inyecta el mock dentro del service automáticamente
    @InjectMocks
    private EstadoPeticionFlujoServiceImpl estadoPeticionService;

    @Test
    @DisplayName("Crear Estado → Created_201 cuando el nombre no está duplicado")
    void testCreateEstado_Created() {

        // DTO entrada
        EstadoPeticionFlujoCreateUpdateDTO createDTO = new EstadoPeticionFlujoCreateUpdateDTO();
        createDTO.setNombre("Creado");
        createDTO.setDescripcion("Estado por defecto");

        // DTO salida simulada del DAO
        EstadoPeticionFlujoResponseDTO responseMock = new EstadoPeticionFlujoResponseDTO();
        responseMock.setId(1L);
        responseMock.setNombre("Creado");
        responseMock.setDescripcion("Estado por defecto");

        // 🔥 EL MOCK CORRECTO
        when(estadoPeticionDAO.save(any()))
                .thenReturn(responseMock);

        // ejecutar
        EstadoPeticionFlujoResponseDTO resultado =
                estadoPeticionService.createEstado(createDTO);

        // asserts
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Creado", resultado.getNombre());

        verify(estadoPeticionDAO, times(1))
                .save(any(EstadoPeticionFlujoCreateUpdateDTO.class));
    }

    @Test
    @DisplayName("Crear Estado → Conflict_409 cuando el nombre ya existe")
    void createEstado_DuplicatedName() {

        EstadoPeticionFlujoCreateUpdateDTO createDTO = new EstadoPeticionFlujoCreateUpdateDTO();
        createDTO.setNombre("Creado");

        // Simulamos duplicado
        when(estadoPeticionDAO.existsByNombreIgnoreCare("creado")).thenReturn(true);

        assertThrows(ConflictException.class,
                () -> estadoPeticionService.createEstado(createDTO));

        // DAO.save NO debe ejecutarse
        verify(estadoPeticionDAO, never()).save(any());
    }

    @Test
    @DisplayName("Crear Estado -> Error 500 cuando el DAO falla")
    void createEstado_InternalServerError() {

        // DTO de entrada válido
        EstadoPeticionFlujoCreateUpdateDTO createDTO = new EstadoPeticionFlujoCreateUpdateDTO();
        createDTO.setNombre("creado");
        createDTO.setDescripcion("desc");

        // Simulamos que NO existe duplicado
        when(estadoPeticionDAO.existsByNombreIgnoreCare(any()))
                .thenReturn(false);

        // 💥 Simulamos fallo inesperado del DAO
        when(estadoPeticionDAO.save(any()))
                .thenThrow(new RuntimeException("DB error"));

        // Verificamos que la excepción se propaga
        assertThrows(RuntimeException.class, () ->
                estadoPeticionService.createEstado(createDTO));

        verify(estadoPeticionDAO).save(any());
    }

    // GET BY ID
    @Test
    @DisplayName("Buscar Estado por Id → Ok_200 cuando se encuentra el estado")
    void getEstadoById_Ok() {
        EstadoPeticionFlujoResponseDTO response = new EstadoPeticionFlujoResponseDTO();
        response.setId(1L);

        when(estadoPeticionDAO.findById(1L)).thenReturn(Optional.of(response));

        EstadoPeticionFlujoResponseDTO result = estadoPeticionService.getEstadoById(1L);

        assertEquals(1L, result.getId());
    }


    @Test
    @DisplayName("Buscar Estado por Id → BadRequest_400 cuando el id recibido es nulo")
    void getEstadoById_NullId() {
        // ID null → BadRequest
        assertThrows(BadRequestException.class,
                () -> estadoPeticionService.getEstadoById(null));
    }

    @Test
    @DisplayName("Buscar Estado por Id → NotFound_404 cuando no existe el estado")
    void getEstadoById_NotFound() {
        when(estadoPeticionDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> estadoPeticionService.getEstadoById(1L));

    }

    @Test
    @DisplayName("Buscar Estado por Id -> Error 500 cuando el DAO falla")
    void getEstadoById_InternalServerError() {

        // DAO explota
        when(estadoPeticionDAO.findById(1L))
                .thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class, () ->
                estadoPeticionService.getEstadoById(1L));
    }

    // GET BY NOMBRE
    @Test
    @DisplayName("Buscar Estado por Nombre → Ok_200 cuando se encuentra el estado")
    void getEstadoByNombre_Ok() {
        EstadoPeticionFlujoResponseDTO response = new EstadoPeticionFlujoResponseDTO();
        response.setNombre("creado");

        when(estadoPeticionDAO.findByNombre("creado")).thenReturn(Optional.of(response));

        EstadoPeticionFlujoResponseDTO result = estadoPeticionService.getEstadoByNombre("Creado");

        assertEquals("creado", result.getNombre());
    }

    @Test
    @DisplayName("Buscar Estado por Nombre → BadRequest_400 cuando el nombre es nulo")
    void getEstadoByNombre_NullNombre() {
        assertThrows(BadRequestException.class,
                () -> estadoPeticionService.getEstadoByNombre(null));
    }

    @Test
    @DisplayName("Buscar Estado por Nombre → NotFound_404 cuando no se encuentra estado con ese nombre")
    void getEstadoByNombre_NotFound() {
        when(estadoPeticionDAO.findByNombre(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> estadoPeticionService.getEstadoByNombre("Creado"));
    }

    @Test
    @DisplayName("Buscar Estado por Nombre -> Error 500 cuando el DAO falla")
    void getEstadoByNombre_InternalServerError() {

        when(estadoPeticionDAO.findByNombre(any()))
                .thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class, () ->
                estadoPeticionService.getEstadoByNombre("creado"));
    }

    // GET ALL
    @Test
    @DisplayName("Obtener todos los estados -> Ok_200 cuando retorna la lista")
    void getAllEstados_Ok200() {
        when(estadoPeticionDAO.findAll()).thenReturn(List.of(
                new EstadoPeticionFlujoResponseDTO(),
                new EstadoPeticionFlujoResponseDTO()
        ));

        List<EstadoPeticionFlujoResponseDTO> result = estadoPeticionService.getAllEstados();

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Obtener todos los estados -> Error 500 cuando el DAO falla")
    void getAllEstados_InternalServerError() {

        when(estadoPeticionDAO.findAll())
                .thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class, () ->
                estadoPeticionService.getAllEstados());
    }

    // UPDATE
    @Test
    @DisplayName("Actualizar estado -> Ok_200 cuando se actualiza el estado")
    void updateEstado_Ok() {
        EstadoPeticionFlujoCreateUpdateDTO dto = new EstadoPeticionFlujoCreateUpdateDTO();
        dto.setNombre("Actualizado");

        EstadoPeticionFlujoResponseDTO response = new EstadoPeticionFlujoResponseDTO();
        response.setId(1L);

        when(estadoPeticionDAO.existsByNombreIgnoreCaseAndIdNot(any(), any()))
                .thenReturn(false);

        when(estadoPeticionDAO.update(eq(1L), any()))
                .thenReturn(Optional.of(response));

        EstadoPeticionFlujoResponseDTO result = estadoPeticionService.updateEstado(1L, dto);

        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Actualizar estado -> BadRequest_400 cuando el Id es nulo")
    void updateEstado_NullId() {
        assertThrows(BadRequestException.class,
                () -> estadoPeticionService.updateEstado(null, new EstadoPeticionFlujoCreateUpdateDTO()));
    }

    @Test
    @DisplayName("Actualizar estado -> Conflict_409 cuando el nombre ya existe ")
    void updateEstado_conflict() {
        EstadoPeticionFlujoCreateUpdateDTO dto = new EstadoPeticionFlujoCreateUpdateDTO();
        dto.setNombre("Duplicado");

        when(estadoPeticionDAO.existsByNombreIgnoreCaseAndIdNot(any(String.class), any(Long.class)))
                .thenReturn(true);

        assertThrows(ConflictException.class,
                () -> estadoPeticionService.updateEstado(1L, dto));
    }

    @Test
    @DisplayName("Actualizar estado -> NotFound_404 cuando el id no existe ")
    void updateEstado_NotFound() {
        EstadoPeticionFlujoCreateUpdateDTO dto = new EstadoPeticionFlujoCreateUpdateDTO();
        dto.setNombre("Nuevo");

        when(estadoPeticionDAO.existsByNombreIgnoreCaseAndIdNot(any(), any()))
                .thenReturn(false);

        when(estadoPeticionDAO.update(eq(1L), any()))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> estadoPeticionService.updateEstado(1L, dto));
    }

    @Test
    @DisplayName("Actualizar estado -> Error 500 cuando el DAO falla")
    void updateEstado_InternalServerError() {

        EstadoPeticionFlujoCreateUpdateDTO dto = new EstadoPeticionFlujoCreateUpdateDTO();
        dto.setNombre("nuevo");
        dto.setDescripcion("descripción");

        when(estadoPeticionDAO.existsByNombreIgnoreCaseAndIdNot(any(String.class), any(Long.class)))
                .thenReturn(false);

        when(estadoPeticionDAO.update(any(), any()))
                .thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class, () ->
                estadoPeticionService.updateEstado(1L, dto));
    }

    // DELETE
    @Test
    @DisplayName("Eliminar estado -> Ok_200 Cuando se elimina el estado")
    void deleteEstado_Ok() {
        when(estadoPeticionDAO.deleteById(1L)).thenReturn(true);

        estadoPeticionService.deleteEstado(1L);

        verify(estadoPeticionDAO).deleteById(1L);
    }

    @Test
    @DisplayName("Eliminar estado -> BadRequest_400 Cuando el id es nulo")
    void deleteEstado_NullId() {
        assertThrows(BadRequestException.class,
                () -> estadoPeticionService.deleteEstado(null));
    }

    @Test
    @DisplayName("Eliminar estado -> NotFound_404 Cuando no existe estado con el id")
    void deleteEstado_NotFound() {
        when(estadoPeticionDAO.deleteById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> estadoPeticionService.deleteEstado(1L));
    }

    @Test
    @DisplayName("Eliminar estado -> Error 500 cuando el DAO falla")
    void deleteEstado_InternalServerError() {

        when(estadoPeticionDAO.deleteById(1L))
                .thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class, () ->
                estadoPeticionService.deleteEstado(1L));
    }

    // GET ENTITY BY ID
    @Test
    @DisplayName("Obtener Entidad -> Ok_200 Cuando se encuentra la entidad")
    void getEstadoEntityById_Ok() {
        EstadoPeticionFlujoEntity entity = new EstadoPeticionFlujoEntity();
        entity.setId(1L);

        when(estadoPeticionDAO.findEntityById(1L)).thenReturn(Optional.of(entity));

        EstadoPeticionFlujoEntity result = estadoPeticionService.getEstadoEntityById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Obtener Entidad -> NotFound_404 Cuando no existe entidad con el id")
    void getEstadoEntityById_NotFound() {
        when(estadoPeticionDAO.findEntityById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> estadoPeticionService.getEstadoEntityById(1L));
    }

    @Test
    @DisplayName("getEstadoEntityById -> Error 500 cuando el DAO falla")
    void getEstadoEntityById_InternalServerError() {

        when(estadoPeticionDAO.findEntityById(1L))
                .thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class, () ->
                estadoPeticionService.getEstadoEntityById(1L));
    }
}