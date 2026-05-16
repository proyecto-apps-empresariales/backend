package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IDocumentoService;
import com.proyecto_backend.demoAPI.businessLayer.services.IEstadoPeticionFlujoService;
import com.proyecto_backend.demoAPI.businessLayer.services.ITipoPeticionFlujoService;
import com.proyecto_backend.demoAPI.businessLayer.services.IUsuarioService;
import com.proyecto_backend.demoAPI.exceptions.BadRequestException;
import com.proyecto_backend.demoAPI.exceptions.ConflictException;
import com.proyecto_backend.demoAPI.exceptions.ResourceNotFoundException;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.PeticionFlujoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.DocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.EstadoPeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoPeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PeticionFlujoServiceTest {

    @Mock
    private PeticionFlujoDAO dao;
    @Mock private IUsuarioService usuarioService;
    @Mock private IEstadoPeticionFlujoService estadoService;
    @Mock private ITipoPeticionFlujoService tipoService;
    @Mock private IDocumentoService documentoService;

    @InjectMocks
    private PeticionFlujoServiceImpl service;

    // ENTIDADES MOCK
    @Mock private Usuario remitente;
    @Mock private Usuario destinatario;
    @Mock private DocumentoEntity documento;
    @Mock private TipoPeticionFlujoEntity tipoPeticion;
    @Mock private EstadoPeticionFlujoEntity estado;

    private PeticionFlujoCreateDTO createDTO(){
        return new PeticionFlujoCreateDTO(
                1L,2L,3L,4L,
                LocalDate.now(),"descripcion","Nombre Test"
        );
    }

    private PeticionFlujoUpdateDTO updateDTO(){
        return new PeticionFlujoUpdateDTO(
                2L,3L,4L,
                LocalDate.now(),"descripcion","Nombre Update"
        );
    }

    private PeticionFlujoResponseDTO responseDTO(){
        return new PeticionFlujoResponseDTO(
                1L,"Ana","Sara","doc.pdf","Tipo",
                "CREADO",null,LocalDate.now(),
                "descripcion","nombre"
        );
    }

    // ================= CREATE =================

    @Test
    void createPeticion_ok(){
        when(dao.existsByNombreIgnoreCase(any())).thenReturn(false);
        when(usuarioService.buscarUsuarioEntityById(1L)).thenReturn(remitente);
        when(usuarioService.buscarUsuarioEntityById(2L)).thenReturn(destinatario);
        when(documentoService.getDocumentoEntityById(3L)).thenReturn(documento);
        when(tipoService.getTipoPeticionEntityById(4L)).thenReturn(tipoPeticion);
        when(estadoService.getEstadoEntityById(1L)).thenReturn(estado);
        when(dao.save(any(),any(),any(),any(),any(),any(),any())).thenReturn(responseDTO());

        PeticionFlujoResponseDTO result = service.createPeticion(createDTO());

        assertNotNull(result);
        verify(dao).save(any(),any(),any(),any(),any(),any(),any());
    }

    @Test
    void createPeticion_conflictNombre(){
        when(dao.existsByNombreIgnoreCase(any())).thenReturn(true);
        assertThrows(ConflictException.class, () -> service.createPeticion(createDTO()));
    }

    // ================= GET BY ID =================

    @Test
    void getById_ok(){
        when(dao.findById(1L)).thenReturn(Optional.of(responseDTO()));
        assertNotNull(service.getPeticionById(1L));
    }

    @Test
    void getById_null(){
        assertThrows(BadRequestException.class, () -> service.getPeticionById(null));
    }

    @Test
    void getById_notFound(){
        when(dao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getPeticionById(1L));
    }

    // ================= GET BY NOMBRE =================

    @Test
    void getByNombre_ok(){
        when(dao.findByNombre("nombre")).thenReturn(Optional.of(responseDTO()));
        assertNotNull(service.getPeticionByNombre("nombre"));
    }

    @Test
    void getByNombre_blank(){
        assertThrows(BadRequestException.class, () -> service.getPeticionByNombre(" "));
    }

    // ================= GET ALL =================

    @Test
    void getAll_ok(){
        when(dao.findAll()).thenReturn(List.of(responseDTO()));
        assertFalse(service.getAllPeticiones().isEmpty());
    }

    // ================= UPDATE =================

    @Test
    void update_ok(){
        when(dao.existsByNombreIgnoreCaseAndIdNot(any(),any())).thenReturn(false);
        when(usuarioService.buscarUsuarioEntityById(any())).thenReturn(destinatario);
        when(documentoService.getDocumentoEntityById(any())).thenReturn(documento);
        when(tipoService.getTipoPeticionEntityById(any())).thenReturn(tipoPeticion);
        when(dao.update(any(),any(),any(),any(),any(),any())).thenReturn(Optional.of(responseDTO()));

        PeticionFlujoResponseDTO result = service.updatePeticion(1L, updateDTO());
        assertNotNull(result);
    }

    @Test
    void update_nullId(){
        assertThrows(BadRequestException.class, () -> service.updatePeticion(null, updateDTO()));
    }

    @Test
    void update_conflictNombre(){
        when(dao.existsByNombreIgnoreCaseAndIdNot(any(),any())).thenReturn(true);
        assertThrows(ConflictException.class, () -> service.updatePeticion(1L, updateDTO()));
    }

    // ================= DELETE =================

    @Test
    void delete_ok(){
        when(dao.delete(1L)).thenReturn(true);
        service.deletePeticion(1L);
        verify(dao).delete(1L);
    }

    @Test
    void delete_notFound(){
        when(dao.delete(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.deletePeticion(1L));
    }

    // ================= CAMBIOS DE ESTADO =================

    @Test
    void enviarRevision_ok(){
        when(estadoService.getEstadoEntityById(2L)).thenReturn(estado);
        when(dao.cambiarEstado(1L,1L,estado)).thenReturn(Optional.of(responseDTO()));

        assertNotNull(service.enviarRevision(1L));
    }

    @Test
    void aprobarPeticion_ok(){
        when(estadoService.getEstadoEntityById(3L)).thenReturn(estado);
        when(dao.cambiarEstado(1L,2L,estado)).thenReturn(Optional.of(responseDTO()));

        assertNotNull(service.aprobarPeticion(1L));
    }

    @Test
    void rechazarPeticion_ok(){
        when(estadoService.getEstadoEntityById(4L)).thenReturn(estado);
        when(dao.cambiarEstado(1L,2L,estado)).thenReturn(Optional.of(responseDTO()));

        assertNotNull(service.rechazarPeticion(1L));
    }

    @Test
    void firmarPeticion_ok(){
        when(estadoService.getEstadoEntityById(5L)).thenReturn(estado);
        when(dao.cambiarEstado(1L,3L,estado)).thenReturn(Optional.of(responseDTO()));

        assertNotNull(service.firmarPeticion(1L));
    }

    @Test
    void finalizarPeticion_ok(){
        when(estadoService.getEstadoEntityById(6L)).thenReturn(estado);
        when(dao.cambiarEstado(1L,5L,estado)).thenReturn(Optional.of(responseDTO()));

        assertNotNull(service.finalizarPeticion(1L));
    }

}
