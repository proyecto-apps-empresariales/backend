package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.proyecto_backend.demoAPI.businessLayer.dtos.OrganizacionCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.OrganizacionDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.OrganizacionUpdateDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.OrganizacionDAO;

@ExtendWith(MockitoExtension.class)
@DisplayName("Organizacion - Unit Tests")
public class OrganizacionServiceTest {

    // Simulamos el comportamiento del OrganizacionDAO utilizando Mockito:
    @Mock
    private OrganizacionDAO organizacionDAO;

    // Inyectamos el OrganizacionServiceImp con el OrganizacionDAO simulado:
    @InjectMocks
    private OrganizacionServiceImp organizacionService;

    // Test para guardar una organizacion con datos validos:
    @Test
    @DisplayName("Guardar Organizacion - Datos Validos -> 200 OK")
    void guardarOrganizacion_OK() {

        // Creamos el DTO de entrada con datos validos:
        OrganizacionCreateDTO dto = new OrganizacionCreateDTO();
        dto.setNombre("Organizacion");
        dto.setDescripcion("Organizacion para guardar");

        // Simulamos el comportamiento del OrganizacionDAO para buscar una organizacion
        // por nombre, devolviendo un Optional vacio (indicando que no existe una
        // organizacion con ese nombre):
        when(organizacionDAO.buscarOrganizacionPorNombre("Organizacion")).thenReturn(Optional.empty());

        // Simulamos el comportamiento del OrganizacionDAO para guardar una organizacion
        // con cualquier parametro, devolviendo una OrganizacionDTO con los datos de la
        // organizacion guardada:
        when(organizacionDAO.guardarOrganizacion(dto)).thenReturn(new OrganizacionDTO());

        // Ejecutamos el metodo a probar:
        OrganizacionDTO resultado = organizacionService.guardarOrganizacion(dto);

        // Verificamos que el resultado no sea nulo:
        assertNotNull(resultado);

        // Verificamos que se hayan llamado los metodos correspondientes del
        // OrganizacionDAO:
        verify(organizacionDAO).buscarOrganizacionPorNombre("Organizacion");
        verify(organizacionDAO).guardarOrganizacion(dto);

    }

    // Test para guardar una organizacion con un nombre ya existente:
    @Test
    @DisplayName("Guardar Organizacion - Nombre Existente -> 409 CONFLICT")
    void guardarOrganizacion_NombreExistente() {

        // Creamos el DTO de entrada con datos validos:
        OrganizacionCreateDTO dto = new OrganizacionCreateDTO();
        dto.setNombre("Organizacion");
        dto.setDescripcion("Organizacion para guardar");

        // Simulamos el comportamiento del OrganizacionDAO para buscar una organizacion
        // por nombre, devolviendo un Optional con una OrganizacionDTO (indicando que ya
        // existe una organizacion con ese nombre):
        when(organizacionDAO.buscarOrganizacionPorNombre("Organizacion"))
                .thenReturn(Optional.of(new OrganizacionDTO()));

        // Ejecutamos el metodo a probar y verificamos que se lance una
        // ResponseStatusException con el status 409 CONFLICT:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> organizacionService.guardarOrganizacion(dto));

        // Verificamos que el status de la excepcion sea 409 CONFLICT:
        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());

    }

    // Test para guardar una organizacion con datos nulos:
    @Test
    @DisplayName("Guardar Organizacion - Datos Nulos -> 400 BAD_REQUEST")
    void guardarOrganizacion_DatosNulos() {

        // Ejecutamos el metodo a probar con un DTO nulo y verificamos que se lance
        // una ResponseStatusException con el status 400 BAD_REQUEST:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> organizacionService.guardarOrganizacion(null));

        // Verificamos que el status de la excepcion sea 400 BAD_REQUEST:
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());

    }

    // Test para obtener la lista de todas las organizaciones:
    @Test
    @DisplayName("Lista Organizaciones - 200 OK")
    void listaOrganizaciones_OK() {

        // Simulamos el comportamiento del OrganizacionDAO para obtener la lista de
        // organizaciones, devolviendo una lista con una OrganizacionDTO:
        when(organizacionDAO.listaOrganizaciones()).thenReturn(List.of(new OrganizacionDTO()));

        // Ejecutamos el metodo a probar:
        List<OrganizacionDTO> resultado = organizacionService.listaOrganizaciones();

        // Verificamos que el resultado no sea nulo y tenga un elemento:
        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        // Verificamos que se haya llamado el metodo correspondiente del
        // OrganizacionDAO:
        verify(organizacionDAO).listaOrganizaciones();

    }

    // Test para obtener una organizacion por id con un id valido:
    @Test
    @DisplayName("Buscar Organizacion por ID - ID Valido -> 200 OK")
    void buscarOrganizacionPorId_OK() {

        // Simulamos el comportamiento del OrganizacionDAO para buscar una organizacion
        // por id, devolviendo un Optional con una OrganizacionDTO (indicando que
        // existe una organizacion con ese id):
        when(organizacionDAO.buscarOrganizacionPorId(1L)).thenReturn(Optional.of(new OrganizacionDTO()));

        // Ejecutamos el metodo a probar:
        OrganizacionDTO resultado = organizacionService.buscarOrganizacionPorId(1L);

        // Verificamos que el resultado no sea nulo:
        assertNotNull(resultado);

        // Verificamos que se haya llamado el metodo correspondiente del
        // OrganizacionDAO:
        verify(organizacionDAO).buscarOrganizacionPorId(1L);

    }

    // Test para obtener una organizacion por id con un id nulo:
    @Test
    @DisplayName("Buscar Organizacion por ID - ID Nulo -> 400 BAD_REQUEST")
    void buscarOrganizacionPorId_IdNulo() {

        // Ejecutamos el metodo a probar con un id nulo y verificamos que se lance
        // una ResponseStatusException con el status 400 BAD_REQUEST:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> organizacionService.buscarOrganizacionPorId(null));

        // Verificamos que el status de la excepcion sea 400 BAD_REQUEST:
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());

    }

    // Test para obtener una organizacion por id con un id no existente:
    @Test
    @DisplayName("Buscar Organizacion por ID - ID No Existente -> 404 NOT_FOUND")
    void buscarOrganizacionPorId_IdNoExistente() {

        // Simulamos el comportamiento del OrganizacionDAO para buscar una organizacion
        // por id, devolviendo un Optional vacio (indicando que no existe una
        // organizacion con ese id):
        when(organizacionDAO.buscarOrganizacionPorId(1L)).thenReturn(Optional.empty());

        // Ejecutamos el metodo a probar con un id no existente y verificamos que se
        // lance una ResponseStatusException con el status 404 NOT_FOUND:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> organizacionService.buscarOrganizacionPorId(1L));

        // Verificamos que el status de la excepcion sea 404 NOT_FOUND:
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());

    }

    // Test para obtener una organizacion por nombre con un nombre valido:
    @Test
    @DisplayName("Buscar Organizacion por Nombre - Nombre Valido -> 200 OK")
    void buscarOrganizacionPorNombre_OK() {

        // Simulamos el comportamiento del OrganizacionDAO para buscar una organizacion
        // por nombre, devolviendo un Optional con una OrganizacionDTO (indicando que
        // existe una organizacion con ese nombre):
        when(organizacionDAO.buscarOrganizacionPorNombre("Organizacion"))
                .thenReturn(Optional.of(new OrganizacionDTO()));

        // Ejecutamos el metodo a probar:
        OrganizacionDTO resultado = organizacionService.buscarOrganizacionPorNombre("Organizacion");

        // Verificamos que el resultado no sea nulo:
        assertNotNull(resultado);

        // Verificamos que se haya llamado el metodo correspondiente del
        // OrganizacionDAO:
        verify(organizacionDAO).buscarOrganizacionPorNombre("Organizacion");

    }

    // Test para obtener una organizacion por nombre con un nombre nulo:
    @Test
    @DisplayName("Buscar Organizacion por Nombre - Nombre Nulo -> 400 BAD_REQUEST")
    void buscarOrganizacionPorNombre_NombreNulo() {

        // Ejecutamos el metodo a probar con un nombre nulo y verificamos que se
        // lance una ResponseStatusException con el status 400 BAD_REQUEST:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> organizacionService.buscarOrganizacionPorNombre(null));

        // Verificamos que el status de la excepcion sea 400 BAD_REQUEST:
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());

    }

    // Test para obtener una organizacion por nombre con un nombre no existente:
    @Test
    @DisplayName("Buscar Organizacion por Nombre - Nombre No Existente -> 404 NOT_FOUND")
    void buscarOrganizacionPorNombre_NombreNoExistente() {

        // Simulamos el comportamiento del OrganizacionDAO para buscar una organizacion
        // por nombre, devolviendo un Optional vacio (indicando que no existe una
        // organizacion con ese nombre):
        when(organizacionDAO.buscarOrganizacionPorNombre("Organizacion"))
                .thenReturn(Optional.empty());

        // Ejecutamos el metodo a probar con un nombre no existente y verificamos que
        // se lance una ResponseStatusException con el status 404 NOT_FOUND:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> organizacionService.buscarOrganizacionPorNombre("Organizacion"));

        // Verificamos que el status de la excepcion sea 404 NOT_FOUND:
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());

    }

    // Test para actualizar una organizacion con datos validos:
    @Test
    @DisplayName("Actualizar Organizacion - Datos Validos -> 200 OK")
    void actualizarOrganizacion_OK() {

        // Creamos el DTO de entrada con datos validos:
        OrganizacionUpdateDTO dto = new OrganizacionUpdateDTO();
        dto.setNombre("Organizacion Actualizada");
        dto.setDescripcion("Organizacion actualizada para guardar");

        // Simulamos el comportamiento del OrganizacionDAO para buscar una organizacion
        // por id, devolviendo un Optional con una OrganizacionDTO (indicando que
        // existe una organizacion con ese id):
        when(organizacionDAO.buscarOrganizacionPorNombre("Organizacion Actualizada")).thenReturn(Optional.empty());

        // Simulamos el comportamiento del OrganizacionDAO para actualizar una
        // organizacion con cualquier parametro, devolviendo un Optional con una
        // OrganizacionDTO (indicando que se actualizo la organizacion):
        when(organizacionDAO.actualizarOrganizacion(dto, 1L)).thenReturn(Optional.of(new OrganizacionDTO()));

        // Ejecutamos el metodo a probar:
        OrganizacionDTO resultado = organizacionService.actualizarOrganizacion(dto, 1L);

        // Verificamos que el resultado no sea nulo:
        assertNotNull(resultado);

        // Verificamos que se hayan llamado los metodos correspondientes del
        // OrganizacionDAO:
        verify(organizacionDAO).actualizarOrganizacion(dto, 1L);

    }

    // Test para actualizar una organizacion con datos nulos:
    @Test
    @DisplayName("Actualizar Organizacion - Datos Nulos -> 400 BAD_REQUEST")
    void actualizarOrganizacion_DatosNulos() {

        // Ejecutamos el metodo a probar con datos nulos y verificamos que se
        // lance una ResponseStatusException con el status 400 BAD_REQUEST:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> organizacionService.actualizarOrganizacion(null, 1L));

        // Verificamos que el status de la excepcion sea 400 BAD_REQUEST:
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());

    }

    // Test para actualizar una organizacion con un nombre ya existente:
    @Test
    @DisplayName("Actualizar Organizacion - Nombre Existente -> 409 CONFLICT")
    void actualizarOrganizacion_NombreExistente() {

        // Creamos el DTO de entrada con datos validos:
        OrganizacionUpdateDTO dto = new OrganizacionUpdateDTO();
        dto.setNombre("Organizacion Actualizada");
        dto.setDescripcion("Organizacion actualizada para guardar");

        // Simulamos el comportamiento del OrganizacionDAO para buscar una organizacion
        // por nombre, devolviendo un Optional con una OrganizacionDTO (indicando que ya
        // existe una organizacion con ese nombre):
        when(organizacionDAO.buscarOrganizacionPorNombre("Organizacion Actualizada"))
                .thenReturn(Optional.of(new OrganizacionDTO()));

        // Ejecutamos el metodo a probar y verificamos que se lance una
        // ResponseStatusException con el status 409 CONFLICT:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> organizacionService.actualizarOrganizacion(dto, 1L));

        // Verificamos que el status de la excepcion sea 409 CONFLICT:
        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());

    }

    // Test para actualizar una organizacion con un id no existente:
    @Test
    @DisplayName("Actualizar Organizacion - ID No Existente -> 404 NOT_FOUND")
    void actualizarOrganizacion_IdNoExistente() {

        // Creamos el DTO de entrada con datos validos:
        OrganizacionUpdateDTO dto = new OrganizacionUpdateDTO();
        dto.setNombre("Organizacion Actualizada");
        dto.setDescripcion("Organizacion actualizada para guardar");

        // Simulamos el comportamiento del OrganizacionDAO para buscar una organizacion
        // por nombre, devolviendo un Optional vacio (indicando que no existe una
        // organizacion con ese nombre):
        when(organizacionDAO.buscarOrganizacionPorNombre("Organizacion Actualizada")).thenReturn(Optional.empty());

        // Simulamos el comportamiento del OrganizacionDAO para actualizar una
        // organizacion con cualquier parametro, devolviendo un Optional vacio
        // (indicando que no se encontro la organizacion a actualizar):
        when(organizacionDAO.actualizarOrganizacion(dto, 1L)).thenReturn(Optional.empty());

        // Ejecutamos el metodo a probar y verificamos que se lance una
        // ResponseStatusException con el status 404 NOT_FOUND:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> organizacionService.actualizarOrganizacion(dto, 1L));

        // Verificamos que el status de la excepcion sea 404 NOT_FOUND:
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());

    }

    // Test para actualizar una organizacion con un id nulo:
    @Test
    @DisplayName("Actualizar Organizacion - ID Nulo -> 400 BAD_REQUEST")
    void actualizarOrganizacion_IdNulo() {

        // Creamos el DTO de entrada con datos validos:
        OrganizacionUpdateDTO dto = new OrganizacionUpdateDTO();
        dto.setNombre("Organizacion Actualizada");
        dto.setDescripcion("Organizacion actualizada para guardar");

        // Ejecutamos el metodo a probar con un id nulo y verificamos que se
        // lance una ResponseStatusException con el status 400 BAD_REQUEST:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> organizacionService.actualizarOrganizacion(dto, null));

        // Verificamos que el status de la excepcion sea 400 BAD_REQUEST:
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());

    }

    // Test para eliminar una organizacion con un id valido:
    @Test 
    @DisplayName("Eliminar Organizacion - ID Valido -> 204 NO CONTENT")
    void eliminarOrganizacion_OK() {

        // Simulamos el comportamiento del OrganizacionDAO para eliminar una
        // organizacion por id, devolviendo true (indicando que se elimino la
        // organizacion):
        when(organizacionDAO.eliminarOrganizacion(1L)).thenReturn(true);

        // Ejecutamos el metodo a probar:
        organizacionService.eliminarOrganizacion(1L);

        // Verificamos que se haya llamado el metodo correspondiente del
        // OrganizacionDAO:
        verify(organizacionDAO).eliminarOrganizacion(1L);

    }

    // Test para eliminar una organizacion con un id no existente:
    @Test
    @DisplayName("Eliminar Organizacion - ID No Existente -> 404 NOT_FOUND")
    void eliminarOrganizacion_IdNoExistente() {

        // Simulamos el comportamiento del OrganizacionDAO para eliminar una
        // organizacion por id, devolviendo false (indicando que no se encontro la
        // organizacion a eliminar):
        when(organizacionDAO.eliminarOrganizacion(1L)).thenReturn(false);

        // Ejecutamos el metodo a probar con un id no existente y verificamos que se
        // lance una ResponseStatusException con el status 404 NOT_FOUND:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> organizacionService.eliminarOrganizacion(1L));

        // Verificamos que el status de la excepcion sea 404 NOT_FOUND:
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());

    }

    // Test para eliminar una organizacion con un id nulo:
    @Test
    @DisplayName("Eliminar Organizacion - ID Nulo -> 400 BAD_REQUEST")
    void eliminarOrganizacion_IdNulo() {

        // Ejecutamos el metodo a probar con un id nulo y verificamos que se
        // lance una ResponseStatusException con el status 400 BAD_REQUEST:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> organizacionService.eliminarOrganizacion(null));

        // Verificamos que el status de la excepcion sea 400 BAD_REQUEST:
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());

    }
    
}