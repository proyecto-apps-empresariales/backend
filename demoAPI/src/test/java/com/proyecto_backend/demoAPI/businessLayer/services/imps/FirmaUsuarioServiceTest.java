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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioUpdateDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.FirmaUsuarioDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.UsuarioDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;

@ExtendWith(MockitoExtension.class)
@DisplayName("FirmaUsuario - Unit Tests")
public class FirmaUsuarioServiceTest {

    // Simulamos el comportamiento del FirmaUsuarioDAO utilizando Mockito:
    @Mock
    private FirmaUsuarioDAO firmaUsuarioDAO;

    // Simulamos el comportamiento del UsuarioDAO utilizando Mockito:
    @Mock
    private UsuarioDAO usuarioDAO;

    // Inyectamos el servicio que vamos a probar, utilizando los DAOs simulados:
    @InjectMocks
    private FirmaUsuarioServiceImp service;

    // =========================
    // HELPERS
    // =========================

    private Usuario usuario() {
    
        return new Usuario();
    
    }

    private FirmaUsuarioCreateDTO createDTO() {
    
        FirmaUsuarioCreateDTO dto = new FirmaUsuarioCreateDTO();
        dto.setArchivoFirma("firma.png");
        dto.setIdUsuario(1L);
        return dto;
    
    }

    private FirmaUsuarioUpdateDTO updateDTO() {
    
        FirmaUsuarioUpdateDTO dto = new FirmaUsuarioUpdateDTO();
        dto.setArchivoFirma("firma2.png");
        dto.setIdUsuario(1L);
        return dto;
    
    }

    private FirmaUsuarioDTO firmaDTO(String archivo) {
    
        FirmaUsuarioDTO dto = new FirmaUsuarioDTO();
        dto.setArchivoFirma(archivo);
        return dto;
    
    }

    // =========================
    // GUARDAR
    // =========================

    @Test
    @DisplayName("Guardar Firma - OK")
    void guardar_OK() {
    
        FirmaUsuarioCreateDTO dto = createDTO();

        when(firmaUsuarioDAO.buscarFirmaPorArchivo("firma.png"))
                .thenReturn(Optional.empty());

        when(firmaUsuarioDAO.guardarFirmaUsuario(dto))
                .thenReturn(firmaDTO("firma.png"));

        FirmaUsuarioDTO result = service.guardarFirmaUsuario(dto);

        assertNotNull(result);
        assertEquals("firma.png", result.getArchivoFirma());

        verify(firmaUsuarioDAO).guardarFirmaUsuario(dto);
    
    }

    @Test
    @DisplayName("Guardar Firma - DTO null -> 400")
    void guardar_null() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.guardarFirmaUsuario(null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Guardar Firma - archivo ya existe -> 409")
    void guardar_conflict() {
    
        FirmaUsuarioCreateDTO dto = createDTO();

        when(firmaUsuarioDAO.buscarFirmaPorArchivo("firma.png"))
                .thenReturn(Optional.of(firmaDTO("firma.png")));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.guardarFirmaUsuario(dto));

        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
    
    }

    // =========================
    // LISTA
    // =========================

    @Test
    @DisplayName("Lista Firmas - OK")
    void lista_OK() {
    
        when(firmaUsuarioDAO.listaFirmas())
                .thenReturn(List.of(firmaDTO("firma.png")));

        List<FirmaUsuarioDTO> result = service.listaFirmas();

        assertEquals(1, result.size());
    
    }

    @Test
    @DisplayName("Lista por usuario - OK")
    void listaPorUsuario_OK() {
    
        when(firmaUsuarioDAO.listaFirmasPorUsuario(1L))
                .thenReturn(List.of(firmaDTO("firma.png")));

        List<FirmaUsuarioDTO> result = service.listaFirmasPorUsuario(1L);

        assertEquals(1, result.size());
    
    }

    @Test
    @DisplayName("Lista por usuario - ID null -> 400")
    void listaPorUsuario_null() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.listaFirmasPorUsuario(null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    // =========================
    // BUSCAR POR ID
    // =========================

    @Test
    @DisplayName("Buscar por ID - OK")
    void buscarPorId_OK() {
    
        when(firmaUsuarioDAO.buscarFirmaPorId(1L))
                .thenReturn(Optional.of(firmaDTO("firma.png")));

        FirmaUsuarioDTO result = service.buscarFirmaPorIdFirma(1L);

        assertNotNull(result);
    
    }

    @Test
    @DisplayName("Buscar por ID - null -> 400")
    void buscarPorId_null() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.buscarFirmaPorIdFirma(null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Buscar por ID - no existe -> 404")
    void buscarPorId_notFound() {
    
        when(firmaUsuarioDAO.buscarFirmaPorId(1L))
                .thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.buscarFirmaPorIdFirma(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    
    }

    // =========================
    // BUSCAR POR ARCHIVO
    // =========================

    @Test
    @DisplayName("Buscar por archivo - OK")
    void buscarPorArchivo_OK() {
    
        when(firmaUsuarioDAO.buscarFirmaPorArchivo("firma.png"))
                .thenReturn(Optional.of(firmaDTO("firma.png")));

        FirmaUsuarioDTO result = service.buscarFirmaPorArchivoFirma("firma.png");

        assertNotNull(result);
    
    }

    @Test
    @DisplayName("Buscar por archivo - null -> 400")
    void buscarPorArchivo_null() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.buscarFirmaPorArchivoFirma(null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Buscar por archivo - no existe -> 404")
    void buscarPorArchivo_notFound() {
    
        when(firmaUsuarioDAO.buscarFirmaPorArchivo("firma.png"))
                .thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.buscarFirmaPorArchivoFirma("firma.png"));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    
    }

    // =========================
    // ACTUALIZAR
    // =========================

    @Test
    @DisplayName("Actualizar Firma - OK")
    void actualizar_OK() {
    
        FirmaUsuarioUpdateDTO dto = updateDTO();

        when(firmaUsuarioDAO.buscarFirmaPorArchivo("firma2.png"))
                .thenReturn(Optional.empty());

        when(usuarioDAO.buscarUsuarioEntidadPorId(1L))
                .thenReturn(Optional.of(usuario()));

        when(firmaUsuarioDAO.actualizarFirma(eq(1L), eq(dto), any(Usuario.class)))
                .thenReturn(Optional.of(firmaDTO("firma2.png")));

        FirmaUsuarioDTO result = service.actualizarFirmaUsuario(dto, 1L);

        assertNotNull(result);
        assertEquals("firma2.png", result.getArchivoFirma());
    
    }

    @Test
    @DisplayName("Actualizar Firma - DTO null -> 400")
    void actualizar_nullDTO() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.actualizarFirmaUsuario(null, 1L));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Actualizar Firma - ID null -> 400")
    void actualizar_nullId() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.actualizarFirmaUsuario(updateDTO(), null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Actualizar Firma - conflicto archivo -> 409")
    void actualizar_conflict() {
    
        FirmaUsuarioUpdateDTO dto = updateDTO();

        when(firmaUsuarioDAO.buscarFirmaPorArchivo("firma2.png"))
                .thenReturn(Optional.of(firmaDTO("firma2.png")));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.actualizarFirmaUsuario(dto, 1L));

        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Actualizar Firma - usuario no existe -> 404")
    void actualizar_usuarioNotFound() {
    
        FirmaUsuarioUpdateDTO dto = updateDTO();

        when(firmaUsuarioDAO.buscarFirmaPorArchivo("firma2.png"))
                .thenReturn(Optional.empty());

        when(usuarioDAO.buscarUsuarioEntidadPorId(1L))
                .thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.actualizarFirmaUsuario(dto, 1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Actualizar Firma - firma no existe -> 404")
    void actualizar_firmaNotFound() {
    
        FirmaUsuarioUpdateDTO dto = updateDTO();

        when(firmaUsuarioDAO.buscarFirmaPorArchivo("firma2.png"))
                .thenReturn(Optional.empty());

        when(usuarioDAO.buscarUsuarioEntidadPorId(1L))
                .thenReturn(Optional.of(usuario()));

        when(firmaUsuarioDAO.actualizarFirma(eq(1L), eq(dto), any()))
                .thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.actualizarFirmaUsuario(dto, 1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    
    }

    // =========================
    // ELIMINAR
    // =========================

    @Test
    @DisplayName("Eliminar Firma - OK")
    void eliminar_OK() {
    
        when(firmaUsuarioDAO.eliminarFirma(1L)).thenReturn(true);

        service.eliminarFirmaUsuario(1L);

        verify(firmaUsuarioDAO).eliminarFirma(1L);
    
    }

    @Test
    @DisplayName("Eliminar Firma - null -> 400")
    void eliminar_null() {
    
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.eliminarFirmaUsuario(null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    
    }

    @Test
    @DisplayName("Eliminar Firma - no existe -> 404")
    void eliminar_notFound() {
    
        when(firmaUsuarioDAO.eliminarFirma(1L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.eliminarFirmaUsuario(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    
    }

}
