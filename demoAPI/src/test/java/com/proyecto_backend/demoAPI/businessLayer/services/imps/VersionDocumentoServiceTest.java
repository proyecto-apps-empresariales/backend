package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.proyecto_backend.demoAPI.businessLayer.dtos.*;
import com.proyecto_backend.demoAPI.exceptions.BadRequestException;
import com.proyecto_backend.demoAPI.exceptions.ResourceNotFoundException;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.DocumentoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.UsuarioDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.VersionDocumentoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.DocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("VersionDocumento - Unit Tests")
public class VersionDocumentoServiceTest {

    @Mock
    private VersionDocumentoDAO versionDocumentoDAO;

    @Mock
    private DocumentoDAO documentoDAO;

    @Mock
    private UsuarioDAO usuarioDAO;

    @InjectMocks
    private VersionDocumentoServiceImpl service;

    // ✅ CREATE VERSION OK
    @Test
    @DisplayName("Crear versión - OK")
    void createVersion_OK() {

        VersionDocumentoCreateDTO dto = new VersionDocumentoCreateDTO();
        dto.setDocumento("Doc1");
        dto.setUsuarioActualizador("user@test.com");
        dto.setArchivoUrl("url");
        dto.setDescripcion("desc");
        dto.setFechaActualizacion(LocalDate.now());

        DocumentoEntity documento = new DocumentoEntity();
        documento.setId(1L);
        documento.setNombre("Doc1");

        Usuario usuario = new Usuario();

        when(documentoDAO.findByNombreEntity("Doc1"))
                .thenReturn(Optional.of(documento));
        when(usuarioDAO.buscarUsuarioEntidadPorCorreo("user@test.com"))
                .thenReturn(Optional.of(usuario));
        when(versionDocumentoDAO.countByDocumentoId(1L))
                .thenReturn(1L);
        when(versionDocumentoDAO.existsByNombreIgnoreCare(any()))
                .thenReturn(false);
        when(versionDocumentoDAO.saveEntity(any()))
                .thenReturn(new VersionDocumentoResponseDTO());

        VersionDocumentoResponseDTO result = service.createVersion(dto);

        assertNotNull(result);

        verify(documentoDAO).findByNombreEntity("Doc1");
        verify(usuarioDAO).buscarUsuarioEntidadPorCorreo("user@test.com");
        verify(versionDocumentoDAO).countByDocumentoId(1L);
        verify(versionDocumentoDAO).saveEntity(any());
    }

    // ❌ DOCUMENTO NO EXISTE
    @Test
    @DisplayName("Crear versión - Documento no existe")
    void createVersion_DocumentoNotFound() {

        VersionDocumentoCreateDTO dto = new VersionDocumentoCreateDTO();
        dto.setDocumento("Doc1");

        when(documentoDAO.findByNombreEntity("Doc1"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.createVersion(dto));
    }

    // ❌ USUARIO NO EXISTE
    @Test
    @DisplayName("Crear versión - Usuario no existe")
    void createVersion_UsuarioNotFound() {

        VersionDocumentoCreateDTO dto = new VersionDocumentoCreateDTO();
        dto.setDocumento("Doc1");
        dto.setUsuarioActualizador("user@test.com");

        DocumentoEntity documento = new DocumentoEntity();
        documento.setId(1L);

        when(documentoDAO.findByNombreEntity("Doc1"))
                .thenReturn(Optional.of(documento));
        when(usuarioDAO.buscarUsuarioEntidadPorCorreo("user@test.com"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.createVersion(dto));
    }

    // ❌ NOMBRE DUPLICADO
    @Test
    @DisplayName("Crear versión - Nombre duplicado")
    void createVersion_Duplicado() {

        VersionDocumentoCreateDTO dto = new VersionDocumentoCreateDTO();
        dto.setDocumento("Doc1");
        dto.setUsuarioActualizador("user@test.com");

        DocumentoEntity documento = new DocumentoEntity();
        documento.setId(1L);
        documento.setNombre("Doc1");

        when(documentoDAO.findByNombreEntity("Doc1"))
                .thenReturn(Optional.of(documento));
        when(usuarioDAO.buscarUsuarioEntidadPorCorreo("user@test.com"))
                .thenReturn(Optional.of(new Usuario()));
        when(versionDocumentoDAO.countByDocumentoId(1L))
                .thenReturn(1L);
        when(versionDocumentoDAO.existsByNombreIgnoreCare(any()))
                .thenReturn(true);

        assertThrows(BadRequestException.class,
                () -> service.createVersion(dto));
    }

    // ✅ GET VERSIONES POR DOCUMENTO
    @Test
    @DisplayName("Obtener versiones por documento - OK")
    void getVersionesByDocumento_OK() {

        when(documentoDAO.findById(1L))
                .thenReturn(Optional.of(new DocumentoResponseDTO ()));
        when(versionDocumentoDAO.findByDocumentoId(1L))
                .thenReturn(List.of(new VersionDocumentoResponseDTO()));

        List<VersionDocumentoResponseDTO> result = service.getVersionesByDocumento(1L);

        assertEquals(1, result.size());
    }

    // ❌ DOCUMENTO NO EXISTE
    @Test
    void getVersionesByDocumento_NotFound() {

        when(documentoDAO.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getVersionesByDocumento(1L));
    }

    // ✅ GET VERSION BY ID
    @Test
    @DisplayName("Obtener versión por ID - OK")
    void getVersionById_OK() {

        when(versionDocumentoDAO.findById(1L))
                .thenReturn(Optional.of(new VersionDocumentoResponseDTO()));

        VersionDocumentoResponseDTO result = service.getVersionById(1L);

        assertNotNull(result);
    }

    // ❌ VERSION NO EXISTE
    @Test
    void getVersionById_NotFound() {

        when(versionDocumentoDAO.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getVersionById(1L));
    }

    // ✅ UPDATE OK
    @Test
    @DisplayName("Actualizar versión - OK")
    void updateVersion_OK() {

        VersionDocumentoUpdateDTO dto = new VersionDocumentoUpdateDTO();
        dto.setDescripcion("Nueva desc");

        when(versionDocumentoDAO.update(eq(1L), any()))
                .thenReturn(Optional.of(new VersionDocumentoResponseDTO()));

        VersionDocumentoResponseDTO result = service.updateVersion(1L, dto);

        assertNotNull(result);
    }

    // ❌ DESCRIPCIÓN VACÍA
    @Test
    void updateVersion_DescripcionVacia() {

        VersionDocumentoUpdateDTO dto = new VersionDocumentoUpdateDTO();
        dto.setDescripcion("");

        assertThrows(BadRequestException.class,
                () -> service.updateVersion(1L, dto));
    }

    // ❌ VERSION NO EXISTE
    @Test
    void updateVersion_NotFound() {

        VersionDocumentoUpdateDTO dto = new VersionDocumentoUpdateDTO();
        dto.setDescripcion("ok");

        when(versionDocumentoDAO.update(eq(1L), any()))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.updateVersion(1L, dto));
    }

    // ✅ DELETE OK
    @Test
    @DisplayName("Eliminar versión - OK")
    void deleteVersion_OK() {

        when(versionDocumentoDAO.deleteById(1L)).thenReturn(true);

        service.deleteVersion(1L);

        verify(versionDocumentoDAO).deleteById(1L);
    }

    // ❌ ID NULO
    @Test
    void deleteVersion_IdNull() {

        assertThrows(BadRequestException.class,
                () -> service.deleteVersion(null));
    }

    // ❌ NO EXISTE
    @Test
    void deleteVersion_NotFound() {

        when(versionDocumentoDAO.deleteById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> service.deleteVersion(1L));
    }
}