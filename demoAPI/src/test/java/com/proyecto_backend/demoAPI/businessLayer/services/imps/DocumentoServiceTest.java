package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.proyecto_backend.demoAPI.businessLayer.dtos.*;
import com.proyecto_backend.demoAPI.exceptions.BadRequestException;
import com.proyecto_backend.demoAPI.exceptions.ResourceNotFoundException;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.DocumentoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.TipoDocumentoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.UsuarioDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.DocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoDocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Documento - Unit Tests")
public class DocumentoServiceTest {

    @Mock
    private DocumentoDAO documentoDAO;

    @Mock
    private UsuarioDAO usuarioDAO;

    @Mock
    private TipoDocumentoDAO tipoDocumentoDAO;

    @InjectMocks
    private DocumentoServiceImpl documentoService;

    // ✅ CREATE OK
    @Test
    @DisplayName("Crear Documento - Datos válidos")
    void createDocumento_OK() {

        DocumentoCreateDTO dto = new DocumentoCreateDTO();
        dto.setNombre("Doc1");
        dto.setDescripcion("Desc");
        dto.setUsuarioCreador("correo@test.com");
        dto.setTipoDocumento("PDF");

        when(documentoDAO.existsByNombreIgnoreCase("Doc1")).thenReturn(false);
        when(usuarioDAO.buscarUsuarioEntidadPorCorreo("correo@test.com"))
                .thenReturn(Optional.of(new Usuario()));
        when(tipoDocumentoDAO.findByNombreEntidad("PDF"))
                .thenReturn(Optional.of(new TipoDocumentoEntity()));
        when(documentoDAO.saveEntity(any())).thenReturn(new DocumentoResponseDTO());

        DocumentoResponseDTO result = documentoService.createDocumento(dto);

        assertNotNull(result);

        verify(documentoDAO).existsByNombreIgnoreCase("Doc1");
        verify(usuarioDAO).buscarUsuarioEntidadPorCorreo("correo@test.com");
        verify(tipoDocumentoDAO).findByNombreEntidad("PDF");
        verify(documentoDAO).saveEntity(any());
    }

    // ❌ DUPLICADO
    @Test
    @DisplayName("Crear Documento - Nombre duplicado")
    void createDocumento_Duplicado() {

        DocumentoCreateDTO dto = new DocumentoCreateDTO();
        dto.setNombre("Doc1");

        when(documentoDAO.existsByNombreIgnoreCase("Doc1")).thenReturn(true);

        assertThrows(BadRequestException.class,
                () -> documentoService.createDocumento(dto));
    }

    // ❌ USUARIO NO EXISTE
    @Test
    @DisplayName("Crear Documento - Usuario no existe")
    void createDocumento_UsuarioNotFound() {

        DocumentoCreateDTO dto = new DocumentoCreateDTO();
        dto.setNombre("Doc1");
        dto.setUsuarioCreador("correo@test.com");
        dto.setTipoDocumento("PDF");

        when(documentoDAO.existsByNombreIgnoreCase("Doc1")).thenReturn(false);
        when(usuarioDAO.buscarUsuarioEntidadPorCorreo("correo@test.com"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> documentoService.createDocumento(dto));
    }

    // ❌ TIPO NO EXISTE
    @Test
    @DisplayName("Crear Documento - Tipo no existe")
    void createDocumento_TipoNotFound() {

        DocumentoCreateDTO dto = new DocumentoCreateDTO();
        dto.setNombre("Doc1");
        dto.setUsuarioCreador("correo@test.com");
        dto.setTipoDocumento("PDF");

        when(documentoDAO.existsByNombreIgnoreCase("Doc1")).thenReturn(false);
        when(usuarioDAO.buscarUsuarioEntidadPorCorreo("correo@test.com"))
                .thenReturn(Optional.of(new Usuario()));
        when(tipoDocumentoDAO.findByNombreEntidad("PDF"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> documentoService.createDocumento(dto));
    }

    // ✅ GET ALL
    @Test
    @DisplayName("Obtener todos los documentos")
    void getAllDocumento_OK() {

        when(documentoDAO.findAll()).thenReturn(List.of(new DocumentoResponseDTO()));

        List<DocumentoResponseDTO> result = documentoService.getAllDocumento();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    // ✅ GET BY ID
    @Test
    @DisplayName("Obtener documento por ID")
    void getDocumentoById_OK() {

        when(documentoDAO.findById(1L))
                .thenReturn(Optional.of(new DocumentoResponseDTO()));

        DocumentoResponseDTO result = documentoService.getDocumentoById(1L);

        assertNotNull(result);
    }

    // ❌ ID NO EXISTE
    @Test
    @DisplayName("Obtener documento por ID no existe")
    void getDocumentoById_NotFound() {

        when(documentoDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> documentoService.getDocumentoById(1L));
    }

    // ✅ GET BY NOMBRE
    @Test
    @DisplayName("Obtener documento por nombre")
    void getDocumentoByNombre_OK() {

        when(documentoDAO.findByNombre("Doc1"))
                .thenReturn(Optional.of(new DocumentoResponseDTO()));

        DocumentoResponseDTO result = documentoService.getDocumentoByNombre("Doc1");

        assertNotNull(result);
    }

    // ❌ NOMBRE NO EXISTE
    @Test
    void getDocumentoByNombre_NotFound() {

        when(documentoDAO.findByNombre("Doc1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> documentoService.getDocumentoByNombre("Doc1"));
    }

    // ✅ POR USUARIO
    @Test
    @DisplayName("Obtener documentos por usuario")
    void getAllDocumentoByUsuario_OK() {

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);

        when(usuarioDAO.buscarUsuarioEntidadPorCorreo("correo@test.com"))
                .thenReturn(Optional.of(usuario));
        when(documentoDAO.findByUsuarioCreador(1L))
                .thenReturn(List.of(new DocumentoResponseDTO()));

        List<DocumentoResponseDTO> result = documentoService.getAllDocumentoByUsuarioCreador("correo@test.com");

        assertEquals(1, result.size());
    }

    // ❌ USUARIO NO EXISTE
    @Test
    void getAllDocumentoByUsuario_NotFound() {

        when(usuarioDAO.buscarUsuarioEntidadPorCorreo("correo@test.com"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> documentoService.getAllDocumentoByUsuarioCreador("correo@test.com"));
    }

    // ✅ UPDATE OK
    @Test
    @DisplayName("Actualizar documento OK")
    void updateDocumento_OK() {

        DocumentoUpdateDTO dto = new DocumentoUpdateDTO();
        dto.setNombre("Nuevo");
        dto.setDescripcion("Desc");

        when(documentoDAO.findById(1L))
                .thenReturn(Optional.of(new DocumentoResponseDTO()));
        when(documentoDAO.update(eq(1L), any()))
                .thenReturn(Optional.of(new DocumentoResponseDTO()));

        DocumentoResponseDTO result = documentoService.updateDocumento(1L, dto);

        assertNotNull(result);
    }

    // ❌ DOCUMENTO NO EXISTE
    @Test
    void updateDocumento_NotFound() {

        DocumentoUpdateDTO dto = new DocumentoUpdateDTO();

        when(documentoDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> documentoService.updateDocumento(1L, dto));
    }

    // ❌ NOMBRE VACÍO
    @Test
    void updateDocumento_NombreVacio() {

        DocumentoUpdateDTO dto = new DocumentoUpdateDTO();
        dto.setNombre("");
        dto.setDescripcion("Desc");

        when(documentoDAO.findById(1L))
                .thenReturn(Optional.of(new DocumentoResponseDTO()));

        assertThrows(BadRequestException.class,
                () -> documentoService.updateDocumento(1L, dto));
    }

    // ❌ DESCRIPCIÓN VACÍA
    @Test
    void updateDocumento_DescripcionVacia() {

        DocumentoUpdateDTO dto = new DocumentoUpdateDTO();
        dto.setNombre("Nombre");
        dto.setDescripcion("");

        when(documentoDAO.findById(1L))
                .thenReturn(Optional.of(new DocumentoResponseDTO()));

        assertThrows(BadRequestException.class,
                () -> documentoService.updateDocumento(1L, dto));
    }

    // ✅ GET ENTITY
    @Test
    @DisplayName("Obtener entidad documento")
    void getDocumentoEntity_OK() {

        when(documentoDAO.findDocumentoEntityById(1L))
                .thenReturn(Optional.of(new DocumentoEntity()));

        DocumentoEntity entity = documentoService.getDocumentoEntityById(1L);

        assertNotNull(entity);
    }

    // ❌ ENTITY NO EXISTE
    @Test
    void getDocumentoEntity_NotFound() {

        when(documentoDAO.findDocumentoEntityById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> documentoService.getDocumentoEntityById(1L));
    }
}