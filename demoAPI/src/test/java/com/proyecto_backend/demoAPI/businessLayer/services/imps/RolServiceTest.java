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

import com.proyecto_backend.demoAPI.businessLayer.dtos.RolCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RolDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RolUpdateDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.RolDAO;

@ExtendWith(MockitoExtension.class)
@DisplayName("Rol - Unit Tests")
public class RolServiceTest {

    // Simulamos la instancia del RolDAO para poder inyectarla en el RolServiceImp:
    @Mock
    private RolDAO rolDAO;

    // Inyectamos la instancia simulada del RolDAO en el RolServiceImp:
    @InjectMocks
    private RolServiceImp rolService;

    // =========================
    // Helpers
    // =========================
    private RolCreateDTO crearDTO() {

        RolCreateDTO dto = new RolCreateDTO();
        dto.setNombre("ADMIN");
        return dto;

    }

    private RolUpdateDTO actualizarDTO() {

        RolUpdateDTO dto = new RolUpdateDTO();
        dto.setNombre("ADMIN_UPDATED");
        return dto;

    }

    private RolDTO crearRolDTO(String nombre) {

        RolDTO dto = new RolDTO();
        dto.setNombre(nombre);
        return dto;

    }

    // =========================
    // GUARDAR
    // =========================

    @Test
    @DisplayName("Guardar Rol - OK")
    void guardarRol_OK() {

        RolCreateDTO dto = crearDTO();
        RolDTO respuesta = crearRolDTO("ADMIN");

        when(rolDAO.buscarRolPorNombre("ADMIN")).thenReturn(Optional.empty());
        when(rolDAO.guardarRol(dto)).thenReturn(respuesta);

        RolDTO result = rolService.guardarRol(dto);

        assertNotNull(result);
        assertEquals("ADMIN", result.getNombre());

        verify(rolDAO).guardarRol(dto);

    }

    @Test
    @DisplayName("Guardar Rol - Nombre existente -> 409")
    void guardarRol_Conflicto() {

        RolCreateDTO dto = crearDTO();

        when(rolDAO.buscarRolPorNombre("ADMIN"))
                .thenReturn(Optional.of(new RolDTO()));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.guardarRol(dto));

        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());

        verify(rolDAO, never()).guardarRol(any());
    
    }

    @Test
    @DisplayName("Guardar Rol - DTO nulo -> 400")
    void guardarRol_Null() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.guardarRol(null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    // =========================
    // LISTA
    // =========================

    @Test
    @DisplayName("Lista Roles - OK")
    void listaRoles_OK() {
    
        when(rolDAO.listaRoles()).thenReturn(List.of(crearRolDTO("ADMIN")));

        List<RolDTO> result = rolService.listaRoles();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ADMIN", result.get(0).getNombre());

        verify(rolDAO).listaRoles();
    
    }

    // =========================
    // BUSCAR POR ID
    // =========================

    @Test
    @DisplayName("Buscar Rol por ID - OK")
    void buscarPorId_OK() {
    
        when(rolDAO.buscarRolPorId(1L))
                .thenReturn(Optional.of(crearRolDTO("ADMIN")));

        RolDTO result = rolService.buscarRolPorId(1L);

        assertNotNull(result);
        assertEquals("ADMIN", result.getNombre());
    
    }

    @Test
    @DisplayName("Buscar Rol por ID - null -> 400")
    void buscarPorId_Null() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.buscarRolPorId(null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Buscar Rol por ID - no existe -> 404")
    void buscarPorId_NotFound() {
    
        when(rolDAO.buscarRolPorId(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.buscarRolPorId(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());

    }

    // =========================
    // BUSCAR POR NOMBRE
    // =========================

    @Test
    @DisplayName("Buscar Rol por nombre - OK")
    void buscarPorNombre_OK() {
    
        when(rolDAO.buscarRolPorNombre("ADMIN"))
                .thenReturn(Optional.of(crearRolDTO("ADMIN")));

        RolDTO result = rolService.buscarRolPorNombre("ADMIN");

        assertNotNull(result);
        assertEquals("ADMIN", result.getNombre());
    
    }

    @Test
    @DisplayName("Buscar Rol por nombre - null -> 400")
    void buscarPorNombre_Null() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.buscarRolPorNombre(null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Buscar Rol por nombre - no existe -> 404")
    void buscarPorNombre_NotFound() {
    
        when(rolDAO.buscarRolPorNombre("ADMIN")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.buscarRolPorNombre("ADMIN"));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    
    }

    // =========================
    // ACTUALIZAR
    // =========================

    @Test
    @DisplayName("Actualizar Rol - OK")
    void actualizarRol_OK() {
    
        RolUpdateDTO dto = actualizarDTO();

        when(rolDAO.buscarRolPorNombre("ADMIN_UPDATED"))
                .thenReturn(Optional.empty());

        when(rolDAO.actualizarRol(dto, 1L))
                .thenReturn(Optional.of(crearRolDTO("ADMIN_UPDATED")));

        RolDTO result = rolService.actualizarRol(dto, 1L);

        assertNotNull(result);
        assertEquals("ADMIN_UPDATED", result.getNombre());
    
    }

    @Test
    @DisplayName("Actualizar Rol - DTO null -> 400")
    void actualizarRol_DTONull() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.actualizarRol(null, 1L));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Actualizar Rol - ID null -> 400")
    void actualizarRol_IdNull() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.actualizarRol(actualizarDTO(), null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Actualizar Rol - Nombre existente -> 409")
    void actualizarRol_Conflicto() {
    
        RolUpdateDTO dto = actualizarDTO();

        when(rolDAO.buscarRolPorNombre("ADMIN_UPDATED"))
                .thenReturn(Optional.of(new RolDTO()));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.actualizarRol(dto, 1L));

        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());

        verify(rolDAO, never()).actualizarRol(any(), any());
    
    }

    @Test
    @DisplayName("Actualizar Rol - no existe -> 404")
    void actualizarRol_NotFound() {
    
        RolUpdateDTO dto = actualizarDTO();

        when(rolDAO.buscarRolPorNombre("ADMIN_UPDATED"))
                .thenReturn(Optional.empty());

        when(rolDAO.actualizarRol(dto, 1L))
                .thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.actualizarRol(dto, 1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    
    }

    // =========================
    // ELIMINAR
    // =========================

    @Test
    @DisplayName("Eliminar Rol - OK")
    void eliminarRol_OK() {
    
        when(rolDAO.eliminarRol(1L)).thenReturn(true);

        rolService.eliminarRol(1L);

        verify(rolDAO).eliminarRol(1L);
    
    }

    @Test
    @DisplayName("Eliminar Rol - ID null -> 400")
    void eliminarRol_IdNull() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.eliminarRol(null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Eliminar Rol - no existe -> 404")
    void eliminarRol_NotFound() {
    
        when(rolDAO.eliminarRol(1L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.eliminarRol(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    
    }

}
