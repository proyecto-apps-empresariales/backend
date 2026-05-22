package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import com.proyecto_backend.demoAPI.persistenceLayer.daos.PermisoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.RolDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Permiso;

@ExtendWith(MockitoExtension.class)
@DisplayName("Rol - Unit Tests")
public class RolServiceTest {

    @Mock
    private RolDAO rolDAO;

    @Mock
    private PermisoDAO permisoDAO;

    @InjectMocks
    private RolServiceImp rolService;

    // =========================
    // GUARDAR ROL
    // =========================

    @Test
    @DisplayName("Guardar Rol - Datos Validos -> 201 CREATED")
    void guardarRol_Created() {

        RolCreateDTO dto = new RolCreateDTO();
        dto.setNombre("ADMIN");

        Set<Permiso> permisos = Set.of(new Permiso());

        when(rolDAO.buscarRolPorNombre("ADMIN")).thenReturn(Optional.empty());
        when(permisoDAO.buscarEntidadPorId(any())).thenReturn(permisos);
        when(rolDAO.guardarRol(eq(dto), any())).thenReturn(new RolDTO());

        RolDTO resultado = rolService.guardarRol(dto);

        assertNotNull(resultado);

        verify(rolDAO).buscarRolPorNombre("ADMIN");
        verify(permisoDAO).buscarEntidadPorId(any());
        verify(rolDAO).guardarRol(eq(dto), any());
    }

    @Test
    @DisplayName("Guardar Rol - Datos Nulos -> 400 BAD_REQUEST")
    void guardarRol_BadRequest() {

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.guardarRol(null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    @DisplayName("Guardar Rol - Nombre ya registrado -> 409 CONFLICT")
    void guardarRol_Conflict() {

        RolCreateDTO dto = new RolCreateDTO();
        dto.setNombre("ADMIN");

        when(rolDAO.buscarRolPorNombre("ADMIN")).thenReturn(Optional.of(new RolDTO()));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.guardarRol(dto));

        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
    }

    // =========================
    // LISTA ROLES
    // =========================

    @Test
    @DisplayName("Lista Roles - Exito -> 200 OK")
    void listaRoles_OK() {

        when(rolDAO.listaRoles()).thenReturn(List.of(new RolDTO()));

        List<RolDTO> resultado = rolService.listaRoles();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    // =========================
    // BUSCAR POR ID
    // =========================

    @Test
    @DisplayName("Buscar Rol por ID - ID Valido -> 200 OK")
    void buscarRolPorId_OK() {

        when(rolDAO.buscarRolPorId(1L)).thenReturn(Optional.of(new RolDTO()));

        RolDTO resultado = rolService.buscarRolPorId(1L);

        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Buscar Rol por ID - ID Nulo -> 400 BAD_REQUEST")
    void buscarRolPorId_BadRequest() {

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.buscarRolPorId(null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    @DisplayName("Buscar Rol por ID - No encontrado -> 404 NOT_FOUND")
    void buscarRolPorId_NotFound() {

        when(rolDAO.buscarRolPorId(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.buscarRolPorId(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    // =========================
    // BUSCAR POR NOMBRE
    // =========================

    @Test
    @DisplayName("Buscar Rol por Nombre - Valido -> 200 OK")
    void buscarRolPorNombre_OK() {

        when(rolDAO.buscarRolPorNombre("ADMIN")).thenReturn(Optional.of(new RolDTO()));

        RolDTO resultado = rolService.buscarRolPorNombre("ADMIN");

        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Buscar Rol por Nombre - Nulo -> 400 BAD_REQUEST")
    void buscarRolPorNombre_BadRequest() {

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.buscarRolPorNombre(null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    @DisplayName("Buscar Rol por Nombre - No encontrado -> 404 NOT_FOUND")
    void buscarRolPorNombre_NotFound() {

        when(rolDAO.buscarRolPorNombre("ADMIN")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.buscarRolPorNombre("ADMIN"));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    // =========================
    // ACTUALIZAR ROL
    // =========================

    @Test
    @DisplayName("Actualizar Rol - Datos Validos -> 200 OK")
    void actualizarRol_OK() {

        RolUpdateDTO dto = new RolUpdateDTO();
        dto.setNombre("ADMIN");

        when(rolDAO.buscarRolPorNombre("ADMIN")).thenReturn(Optional.empty());
        when(rolDAO.actualizarRol(dto, 1L)).thenReturn(Optional.of(new RolDTO()));

        RolDTO resultado = rolService.actualizarRol(dto, 1L);

        assertNotNull(resultado);

        verify(rolDAO).buscarRolPorNombre("ADMIN");
        verify(rolDAO).actualizarRol(dto, 1L);
    }

    @Test
    @DisplayName("Actualizar Rol - Datos Nulos -> 400 BAD_REQUEST")
    void actualizarRol_DatosNulos() {

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.actualizarRol(null, 1L));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    @DisplayName("Actualizar Rol - ID Nulo -> 400 BAD_REQUEST")
    void actualizarRol_IdNulo() {

        RolUpdateDTO dto = new RolUpdateDTO();
        dto.setNombre("ADMIN");

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.actualizarRol(dto, null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    @DisplayName("Actualizar Rol - Nombre ya registrado -> 409 CONFLICT")
    void actualizarRol_Conflict() {

        RolUpdateDTO dto = new RolUpdateDTO();
        dto.setNombre("ADMIN");

        when(rolDAO.buscarRolPorNombre("ADMIN")).thenReturn(Optional.of(new RolDTO()));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.actualizarRol(dto, 1L));

        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
    }

    @Test
    @DisplayName("Actualizar Rol - No encontrado -> 404 NOT_FOUND")
    void actualizarRol_NotFound() {

        RolUpdateDTO dto = new RolUpdateDTO();
        dto.setNombre("ADMIN");

        when(rolDAO.buscarRolPorNombre("ADMIN")).thenReturn(Optional.empty());
        when(rolDAO.actualizarRol(dto, 1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.actualizarRol(dto, 1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    // =========================
    // ELIMINAR ROL
    // =========================

    @Test
    @DisplayName("Eliminar Rol - OK -> 204 NO_CONTENT")
    void eliminarRol_OK() {

        when(rolDAO.eliminarRol(1L)).thenReturn(true);

        rolService.eliminarRol(1L);

        verify(rolDAO).eliminarRol(1L);
    }

    @Test
    @DisplayName("Eliminar Rol - ID Nulo -> 400 BAD_REQUEST")
    void eliminarRol_BadRequest() {

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.eliminarRol(null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    @DisplayName("Eliminar Rol - No encontrado -> 404 NOT_FOUND")
    void eliminarRol_NotFound() {

        when(rolDAO.eliminarRol(1L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> rolService.eliminarRol(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }
}