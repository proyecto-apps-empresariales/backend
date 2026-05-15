package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PermisoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PermisoDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PermisoUpdateDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.PermisoDAO;

@ExtendWith(MockitoExtension.class)
@DisplayName("Permiso - Unit Tests")
public class PermisoServiceTest {

    // Simulamos la instancia del PermisoDAO para poder inyectarla en el PermisoServiceImp:
    @Mock
    private PermisoDAO permisoDAO;

    // Inyectamos la instancia simulada del PermisoDAO en el PermisoServiceImp:
    @InjectMocks
    private PermisoServiceImp permisoService;
    
    // =========================
    // Helpers
    // =========================

    private PermisoCreateDTO crearDTO() {

        PermisoCreateDTO dto = new PermisoCreateDTO();
        dto.setNombre("READ_USERS");
        return dto;
    
    }

    private PermisoUpdateDTO actualizarDTO() {
    
        PermisoUpdateDTO dto = new PermisoUpdateDTO();
        dto.setNombre("READ_USERS_UPDATED");
        return dto;
    
    }

    private PermisoDTO crearPermisoDTO(String nombre) {
    
        PermisoDTO dto = new PermisoDTO();
        dto.setNombre(nombre);
        return dto;
    
    }

    // =========================
    // GUARDAR
    // =========================

    @Test
    @DisplayName("Guardar Permiso - OK")
    void guardarPermiso_OK() {
    
        PermisoCreateDTO dto = crearDTO();
        PermisoDTO respuesta = crearPermisoDTO("READ_USERS");

        when(permisoDAO.buscarPorNombre("READ_USERS")).thenReturn(Optional.empty());
        when(permisoDAO.guardarPermiso(dto)).thenReturn(respuesta);

        PermisoDTO result = permisoService.guardarPermiso(dto);

        assertNotNull(result);
        assertEquals("READ_USERS", result.getNombre());

        verify(permisoDAO).guardarPermiso(dto);
    
    }

    @Test
    @DisplayName("Guardar Permiso - Nombre existente -> 409")
    void guardarPermiso_Conflicto() {
    
        PermisoCreateDTO dto = crearDTO();

        when(permisoDAO.buscarPorNombre("READ_USERS"))
                .thenReturn(Optional.of(new PermisoDTO()));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> permisoService.guardarPermiso(dto));

        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());

        verify(permisoDAO, never()).guardarPermiso(any());
    
    }

    @Test
    @DisplayName("Guardar Permiso - DTO null -> 400")
    void guardarPermiso_Null() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> permisoService.guardarPermiso(null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    // =========================
    // LISTA
    // =========================

    @Test
    @DisplayName("Lista Permisos - OK")
    void listaPermisos_OK() {
    
        when(permisoDAO.listaPermisos())
                .thenReturn(List.of(crearPermisoDTO("READ_USERS")));

        List<PermisoDTO> result = permisoService.listaPermisos();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("READ_USERS", result.get(0).getNombre());

        verify(permisoDAO).listaPermisos();
    
    }

    // =========================
    // BUSCAR POR ID
    // =========================

    @Test
    @DisplayName("Buscar Permiso por ID - OK")
    void buscarPorId_OK() {
    
        when(permisoDAO.buscarPorId(1L))
                .thenReturn(Optional.of(crearPermisoDTO("READ_USERS")));

        PermisoDTO result = permisoService.buscarPorId(1L);

        assertNotNull(result);
        assertEquals("READ_USERS", result.getNombre());
    
    }

    @Test
    @DisplayName("Buscar Permiso por ID - null -> 400")
    void buscarPorId_Null() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> permisoService.buscarPorId(null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Buscar Permiso por ID - no existe -> 404")
    void buscarPorId_NotFound() {
    
        when(permisoDAO.buscarPorId(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> permisoService.buscarPorId(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    
    }

    // =========================
    // BUSCAR POR NOMBRE
    // =========================

    @Test
    @DisplayName("Buscar Permiso por nombre - OK")
    void buscarPorNombre_OK() {
    
        when(permisoDAO.buscarPorNombre("READ_USERS"))
                .thenReturn(Optional.of(crearPermisoDTO("READ_USERS")));

        PermisoDTO result = permisoService.buscarPorNombre("READ_USERS");

        assertNotNull(result);
        assertEquals("READ_USERS", result.getNombre());
    
    }

    @Test
    @DisplayName("Buscar Permiso por nombre - null -> 400")
    void buscarPorNombre_Null() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> permisoService.buscarPorNombre(null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Buscar Permiso por nombre - no existe -> 404")
    void buscarPorNombre_NotFound() {
    
        when(permisoDAO.buscarPorNombre("READ_USERS")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> permisoService.buscarPorNombre("READ_USERS"));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    
    }

    // =========================
    // ACTUALIZAR
    // =========================

    @Test
    @DisplayName("Actualizar Permiso - OK")
    void actualizarPermiso_OK() {
    
        PermisoUpdateDTO dto = actualizarDTO();

        when(permisoDAO.actualizarPermiso(dto, 1L))
                .thenReturn(Optional.of(crearPermisoDTO("READ_USERS_UPDATED")));

        PermisoDTO result = permisoService.actualizarPermiso(dto, 1L);

        assertNotNull(result);
        assertEquals("READ_USERS_UPDATED", result.getNombre());
    
    }

    @Test
    @DisplayName("Actualizar Permiso - DTO null -> 400")
    void actualizarPermiso_DTONull() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> permisoService.actualizarPermiso(null, 1L));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Actualizar Permiso - ID null -> 400")
    void actualizarPermiso_IdNull() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> permisoService.actualizarPermiso(actualizarDTO(), null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Actualizar Permiso - no existe -> 404")
    void actualizarPermiso_NotFound() {
    
        PermisoUpdateDTO dto = actualizarDTO();

        when(permisoDAO.actualizarPermiso(dto, 1L))
                .thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> permisoService.actualizarPermiso(dto, 1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    
    }

    // =========================
    // ELIMINAR
    // =========================

    @Test
    @DisplayName("Eliminar Permiso - OK")
    void eliminarPermiso_OK() {
    
        when(permisoDAO.eliminarPermiso(1L)).thenReturn(true);

        permisoService.eliminarPermiso(1L);

        verify(permisoDAO).eliminarPermiso(1L);
    
    }

    @Test
    @DisplayName("Eliminar Permiso - ID null -> 400")
    void eliminarPermiso_IdNull() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> permisoService.eliminarPermiso(null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Eliminar Permiso - no existe -> 404")
    void eliminarPermiso_NotFound() {
    
        when(permisoDAO.eliminarPermiso(1L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> permisoService.eliminarPermiso(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    
    }
    
}

