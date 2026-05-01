package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

import com.proyecto_backend.demoAPI.businessLayer.dtos.NotificacionCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.NotificacionDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.NotificacionUpdateDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.NotificacionDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.UsuarioDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;

@ExtendWith(MockitoExtension.class)
@DisplayName("Notificacion - Unit Tests")
public class NotificacionServiceTest {

    // Simulamos la instancia del NotificacionDAO para poder inyectarla en el NotificacionServiceImp:
    @Mock
    private NotificacionDAO notificacionDAO;

    // Simulamos la instancia del UsuarioDAO para poder inyectarla en el NotificacionServiceImp:
    @Mock
    private UsuarioDAO usuarioDAO;

    // Inyectamos la instancia simulada del NotificacionDAO en el NotificacionServiceImp:
    @InjectMocks
    private NotificacionServiceImp notificacionService;

    // =========================
    // Helpers
    // =========================

    private NotificacionCreateDTO crearDTO() {
        
        NotificacionCreateDTO dto = new NotificacionCreateDTO();
        dto.setIdUsuario(1L);
        dto.setTitulo("Aviso");
        return dto;
    
    }

    private NotificacionUpdateDTO actualizarDTO() {
    
        NotificacionUpdateDTO dto = new NotificacionUpdateDTO();
        dto.setIdUsuario(1L);
        dto.setTitulo("Actualizado");
        return dto;
    
    }

    private NotificacionDTO crearNotificacionDTO(String titulo) {
    
        NotificacionDTO dto = new NotificacionDTO();
        dto.setTitulo(titulo);
        return dto;
    
    }

    private Usuario crearUsuario() {
    
        return new Usuario();
    
    }

    // =========================
    // GUARDAR
    // =========================

    @Test
    @DisplayName("Guardar Notificacion - OK")
    void guardar_OK() {
    
        NotificacionCreateDTO dto = crearDTO();
        Usuario usuario = crearUsuario();

        when(usuarioDAO.buscarUsuarioEntidadPorId(1L))
                .thenReturn(Optional.of(usuario));

        when(notificacionDAO.guardarNotificacion(dto, usuario))
                .thenReturn(crearNotificacionDTO("Aviso"));

        NotificacionDTO result = notificacionService.guardarNotificacion(dto);

        assertNotNull(result);
        assertEquals("Aviso", result.getTitulo());

        verify(notificacionDAO).guardarNotificacion(dto, usuario);
    
    }

    @Test
    @DisplayName("Guardar Notificacion - DTO null -> 400")
    void guardar_Null() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> notificacionService.guardarNotificacion(null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Guardar Notificacion - Usuario no existe -> 404")
    void guardar_UsuarioNoExiste() {
    
        NotificacionCreateDTO dto = crearDTO();

        when(usuarioDAO.buscarUsuarioEntidadPorId(1L))
                .thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> notificacionService.guardarNotificacion(dto));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());

        verify(notificacionDAO, never()).guardarNotificacion(any(), any());
    
    }

    // =========================
    // LISTA
    // =========================

    @Test
    @DisplayName("Lista Notificaciones - OK")
    void lista_OK() {
    
        when(notificacionDAO.listaNotificaciones())
                .thenReturn(List.of(crearNotificacionDTO("Aviso")));

        List<NotificacionDTO> result = notificacionService.listaNotificaciones();

        assertNotNull(result);
        assertEquals(1, result.size());
    
    }

    // =========================
    // BUSCAR POR ID
    // =========================

    @Test
    @DisplayName("Buscar por ID - OK")
    void buscarPorId_OK() {
    
        when(notificacionDAO.buscarPorId(1L))
                .thenReturn(Optional.of(crearNotificacionDTO("Aviso")));

        NotificacionDTO result = notificacionService.buscarPorId(1L);

        assertNotNull(result);
    
    }

    @Test
    @DisplayName("Buscar por ID - null -> 400")
    void buscarPorId_Null() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> notificacionService.buscarPorId(null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Buscar por ID - no existe -> 404")
    void buscarPorId_NotFound() {
    
        when(notificacionDAO.buscarPorId(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> notificacionService.buscarPorId(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    
    }

    // =========================
    // FILTROS
    // =========================

    @Test
    @DisplayName("Lista por titulo - OK")
    void listaPorTitulo_OK() {
    
        when(notificacionDAO.listaPorTitulo("Aviso"))
                .thenReturn(List.of(crearNotificacionDTO("Aviso")));

        List<NotificacionDTO> result = notificacionService.listaPorTitulo("Aviso");

        assertEquals(1, result.size());
    
    }

    @Test
    @DisplayName("Lista por titulo - null -> 400")
    void listaPorTitulo_Null() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> notificacionService.listaPorTitulo(null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Lista por usuario - OK")
    void listaPorUsuario_OK() {
    
        when(notificacionDAO.listaPorUsuario(1L))
                .thenReturn(List.of(crearNotificacionDTO("Aviso")));

        List<NotificacionDTO> result = notificacionService.listaPorUsuario(1L);

        assertEquals(1, result.size());
    
    }

    @Test
    @DisplayName("Lista por usuario - null -> 400")
    void listaPorUsuario_Null() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> notificacionService.listaPorUsuario(null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Lista por fueLeida - OK")
    void listaPorFueLeida_OK() {
    
        when(notificacionDAO.listaPorFueLeida(true))
                .thenReturn(List.of(crearNotificacionDTO("Aviso")));

        List<NotificacionDTO> result = notificacionService.listaPorFueLeida(true);

        assertEquals(1, result.size());
    
    }

    @Test
    @DisplayName("Lista por usuario y leida - OK")
    void listaPorUsuarioYLeida_OK() {
    
        when(usuarioDAO.buscarUsuarioEntidadPorId(1L))
                .thenReturn(Optional.of(crearUsuario()));

        when(notificacionDAO.listaPorUsuarioYFueLeida(1L, true))
                .thenReturn(List.of(crearNotificacionDTO("Aviso")));

        List<NotificacionDTO> result =
                notificacionService.listaPorUsuarioYFueLeida(1L, true);

        assertEquals(1, result.size());
    
    }

    @Test
    @DisplayName("Lista por usuario y leida - usuario null -> 400")
    void listaPorUsuarioYLeida_Null() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> notificacionService.listaPorUsuarioYFueLeida(null, true));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Lista por usuario y leida - usuario no existe -> 404")
    void listaPorUsuarioYLeida_NotFound() {
    
        when(usuarioDAO.buscarUsuarioEntidadPorId(1L))
                .thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> notificacionService.listaPorUsuarioYFueLeida(1L, true));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    
    }

    // =========================
    // ACTUALIZAR
    // =========================

    @Test
    @DisplayName("Actualizar - OK")
    void actualizar_OK() {
    
        NotificacionUpdateDTO dto = actualizarDTO();
        Usuario usuario = crearUsuario();

        when(usuarioDAO.buscarUsuarioEntidadPorId(1L))
                .thenReturn(Optional.of(usuario));

        when(notificacionDAO.actualizarNotificacion(dto, 1L, usuario))
                .thenReturn(Optional.of(crearNotificacionDTO("Actualizado")));

        NotificacionDTO result =
                notificacionService.actualizarNotificacion(dto, 1L);

        assertNotNull(result);
        assertEquals("Actualizado", result.getTitulo());
    
    }

    @Test
    @DisplayName("Actualizar - DTO null -> 400")
    void actualizar_DTONull() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> notificacionService.actualizarNotificacion(null, 1L));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Actualizar - ID null -> 400")
    void actualizar_IdNull() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> notificacionService.actualizarNotificacion(actualizarDTO(), null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Actualizar - usuario no existe -> 404")
    void actualizar_UsuarioNotFound() {
    
        NotificacionUpdateDTO dto = actualizarDTO();

        when(usuarioDAO.buscarUsuarioEntidadPorId(1L))
                .thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> notificacionService.actualizarNotificacion(dto, 1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Actualizar - notificacion no existe -> 404")
    void actualizar_NotFound() {
    
        NotificacionUpdateDTO dto = actualizarDTO();

        when(usuarioDAO.buscarUsuarioEntidadPorId(1L))
                .thenReturn(Optional.of(crearUsuario()));

        when(notificacionDAO.actualizarNotificacion(eq(dto), eq(1L), any()))
                .thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> notificacionService.actualizarNotificacion(dto, 1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    
    }

    // =========================
    // ELIMINAR
    // =========================

    @Test
    @DisplayName("Eliminar - OK")
    void eliminar_OK() {
    
        when(notificacionDAO.eliminarNotificacion(1L)).thenReturn(true);

        notificacionService.eliminarNotificacion(1L);

        verify(notificacionDAO).eliminarNotificacion(1L);
    
    }

    @Test
    @DisplayName("Eliminar - ID null -> 400")
    void eliminar_Null() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> notificacionService.eliminarNotificacion(null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Eliminar - no existe -> 404")
    void eliminar_NotFound() {
    
        when(notificacionDAO.eliminarNotificacion(1L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> notificacionService.eliminarNotificacion(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    
    }
    
}
