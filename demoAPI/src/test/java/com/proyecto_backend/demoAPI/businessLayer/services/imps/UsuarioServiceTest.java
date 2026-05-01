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

import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioUpdateContrasenaDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioUpdateDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.OrganizacionDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.RolDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.UsuarioDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Organizacion;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Rol;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;

@ExtendWith(MockitoExtension.class)
@DisplayName("Usuario - Unit Tests")
public class UsuarioServiceTest {

    // Simulamos el UsuarioDAO,OrganizacionDAO y RolDAO con Mockito:
    @Mock
    private UsuarioDAO usuarioDAO;
    @Mock
    private OrganizacionDAO organizacionDAO;
    @Mock
    private RolDAO rolDAO;

    // Inyectamos los DAOs en el UsuarioServiceImp:
    @InjectMocks
    private UsuarioServiceImp usuarioService;

    // Test para guardar un usuario con datos validos:
    @Test
    @DisplayName("Guardar Usuario - Datos Validos -> 201 CREATED")
    void guardarUsuario_Created() {

        // Creamos el DTO de entrada con datos validos:
        UsuarioCreateDTO dto = new UsuarioCreateDTO();
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setCorreo("juan@gmail.com");
        dto.setContrasena("123");
        dto.setCelular("311");
        dto.setIdOrganizacion(1L);
        dto.setIdRol(1L);

        // Simulamos el comportamiento del UsuarioDAO para la busqueda del correo del
        // DTO:
        when(usuarioDAO.buscarUsuarioPorCorreo("juan@gmail.com")).thenReturn(Optional.empty());
        // Simulamos el comportamiento del OrganizacionDAO para el id de organizacion
        // del DTO:
        when(organizacionDAO.buscarOrganizacionEntidadPorId(1L)).thenReturn(Optional.of(new Organizacion()));
        // Simulamos el comportamiento del RolDAO para el id de rol del DTO:
        when(rolDAO.buscarRolEntidadPorId(1L)).thenReturn(Optional.of(new Rol()));
        // Simulamos el comportamiento del UsuarioDAO para guardar el usuario con
        // cualquier parametro:
        when(usuarioDAO.guardarUsuario(any(), any(), any())).thenReturn(new UsuarioDTO());

        // Ejecutamos el metodo a probar:
        UsuarioDTO resultado = usuarioService.guardarUsuario(dto);

        // Verificamos que el resultado no sea nulo:
        assertNotNull(resultado);

        // Verificamos que se hayan llamado los metodos correspondientes del UsuarioDAO,
        // OrganizacionDAO y RolDAO:
        verify(usuarioDAO).buscarUsuarioPorCorreo("juan@gmail.com");
        verify(organizacionDAO).buscarOrganizacionEntidadPorId(1L);
        verify(rolDAO).buscarRolEntidadPorId(1L);
        verify(usuarioDAO).guardarUsuario(any(), any(), any());

    }

    // Test para guardar un usuario con datos nulos:
    @Test
    @DisplayName("Guardar Usuario - Datos Nulos -> 400 BAD_REQUEST")
    void guardarUsuario_BadRequest() {

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.guardarUsuario(null));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());

    }

    // Test para guardar un usuario con correo ya registrado:
    @Test
    @DisplayName("Guardar Usuario - Correo ya registrado -> 409 CONFLICT")
    void guardarUsuario_Conflict() {

        // Creamos el DTO de entrada con datos validos:
        UsuarioCreateDTO dto = new UsuarioCreateDTO();
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setCorreo("juan@gmail.com");
        dto.setContrasena("123");
        dto.setCelular("311");
        dto.setIdOrganizacion(1L);
        dto.setIdRol(1L);

        // Simulamos el comportamiento del UsuarioDAO para la busqueda del correo del
        // DTO, devolviendo un usuario existente:
        when(usuarioDAO.buscarUsuarioPorCorreo(dto.getCorreo())).thenReturn(Optional.of(new UsuarioDTO()));

        // Ejecutamos el metodo a probar y verificamos que se lance la excepcion
        // correspondiente:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.guardarUsuario(dto));

        // Verificamos que el codigo de estado de la excepcion sea 409 CONFLICT:
        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());

    }

    // Test para buscar un usuario por id con id valido:
    @Test
    @DisplayName("Buscar Usuario por ID - ID Valido -> 200 OK")
    void buscarUsuarioPorId_OK() {

        // Simulamos el comportamiento del UsuarioDAO para la busqueda de un usuario por
        // id, devolviendo un usuario existente:
        when(usuarioDAO.buscarUsuarioPorId(1L)).thenReturn(Optional.of(new UsuarioDTO()));

        // Ejecutamos el metodo a probar:
        UsuarioDTO resultado = usuarioService.buscarUsuarioPorId(1L);

        // Verificamos que el resultado no sea nulo:
        assertNotNull(resultado);

    }

    // Test para buscar un usuario por id con id nulo:
    @Test
    @DisplayName("Buscar Usuario por ID - ID Nulo -> 400 BAD_REQUEST")
    void buscarUsuarioPorId_BadRequest() {

        // Ejecutamos el metodo a probar y verificamos que se lance la excepcion
        // correspondiente:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.buscarUsuarioPorId(null));

        // Verificamos que el codigo de estado de la excepcion sea 400 BAD_REQUEST:
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());

    }

    // Test para buscar un usuario por id con id no existente:
    @Test
    @DisplayName("Buscar Usuario por ID - ID No Existente -> 404 NOT_FOUND")
    void buscarUsuarioPorId_NotFound() {

        // Simulamos el comportamiento del UsuarioDAO para la busqueda de un usuario por
        // id, devolviendo un Optional vacio:
        when(usuarioDAO.buscarUsuarioPorId(1L)).thenReturn(Optional.empty());

        // Ejecutamos el metodo a probar y verificamos que se lance la excepcion
        // correspondiente:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.buscarUsuarioPorId(1L));

        // Verificamos que el codigo de estado de la excepcion sea 404 NOT_FOUND:
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());

    }

    // Test para buscar un usuario por correo con correo valido:
    @Test
    @DisplayName("Buscar Usuario por Correo - Correo Valido -> 200 OK")
    void buscarUsuarioPorCorreo_OK() {

        // Simulamos el comportamiento del UsuarioDAO para la busqueda de un usuario por
        // correo, devolviendo un usuario existente:
        when(usuarioDAO.buscarUsuarioPorCorreo("juan@gmail.com")).thenReturn(Optional.of(new UsuarioDTO()));

        // Ejecutamos el metodo a probar:
        UsuarioDTO resultado = usuarioService.buscarUsuarioPorCorreo("juan@gmail.com");

        // Verificamos que el resultado no sea nulo:
        assertNotNull(resultado);
    }

    // Test para buscar un usuario por correo con correo nulo:
    @Test
    @DisplayName("Buscar Usuario por Correo - Correo Nulo -> 400 BAD_REQUEST")
    void buscarUsuarioPorCorreo_BadRequest() {

        // Ejecutamos el metodo a probar y verificamos que se lance la excepcion
        // correspondiente:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.buscarUsuarioPorCorreo(null));

        // Verificamos que el codigo de estado de la excepcion sea 400 BAD_REQUEST:
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());

    }

    // Test para buscar un usuario por correo con correo no existente:
    @Test
    @DisplayName("Buscar Usuario por Correo - Correo No Existente -> 404 NOT_FOUND")
    void buscarUsuarioPorCorreo_NotFound() {
    
        // Simulamos el comportamiento del UsuarioDAO para la busqueda de un usuario por
        // correo, devolviendo un Optional vacio:
        when(usuarioDAO.buscarUsuarioPorCorreo("juan@gmail.com")).thenReturn(Optional.empty());

        // Ejecutamos el metodo a probar y verificamos que se lance la excepcion
        // correspondiente:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.buscarUsuarioPorCorreo("juan@gmail.com"));

        // Verificamos que el codigo de estado de la excepcion sea 404 NOT_FOUND:
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        
    }

    // Test para buscar un usuario por correo y contrasena con datos validos:
    @Test
    @DisplayName("Buscar Usuario por Correo y Contrasena - Datos Validos -> 200 OK")
    void buscarUsuarioPorCorreoYContrasena_OK() {

        // Creamos el Usuario con datos validos:
        Usuario usuario = new Usuario();
        usuario.setContrasenaHash("123");

        // Simulamos el comportamiento del UsuarioDAO para la busqueda de un usuario por
        // correo, devolviendo un usuario existente:
        when(usuarioDAO.buscarUsuarioEntidadPorCorreo("juan@gmail.com")).thenReturn(Optional.of(usuario));

        // Simulamos el comportamiento del UsuarioDAO para la busqueda de un usuario por
        // correo, devolviendo un usuario existente:
        when(usuarioDAO.buscarUsuarioPorCorreo("juan@gmail.com")).thenReturn(Optional.of(new UsuarioDTO()));

        // Ejecutamos el metodo a probar:
        UsuarioDTO resultado = usuarioService.buscarUsuarioPorCorreoYContrasena("juan@gmail.com", "123");

        // Verificamos que el resultado no sea nulo:
        assertNotNull(resultado);

    }

    // Test para buscar un usuario por correo y contrasena con correo nulo:
    @Test
    @DisplayName("Buscar Usuario por Correo y Contrasena - Correo Nulo -> 400 BAD_REQUEST")
    void buscarUsuarioPorCorreoYContrasena_BadRequest() {

        // Ejecutamos el metodo a probar y verificamos que se lance la excepcion
        // correspondiente:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.buscarUsuarioPorCorreoYContrasena(null, "123"));

        // Verificamos que el codigo de estado de la excepcion sea 400 BAD_REQUEST:
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());

    }

    // Test para buscar un usuario por correo y contrasena con correo no existente:
    @Test
    @DisplayName("Buscar Usuario por Correo y Contrasena - Correo No Existente -> 404 NOT_FOUND")
    void buscarUsuarioPorCorreoYContrasena_NotFound() {

        // Simulamos el comportamiento del UsuarioDAO para la busqueda de un usuario por
        // correo, devolviendo un Optional vacio:
        when(usuarioDAO.buscarUsuarioEntidadPorCorreo("juan@gmail.com")).thenReturn(Optional.empty());

        // Ejecutamos el metodo a probar y verificamos que se lance la excepcion
        // correspondiente:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.buscarUsuarioPorCorreoYContrasena("juan@gmail.com", "123"));

        // Verificamos que el codigo de estado de la excepcion sea 404 NOT_FOUND:
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());

    }

    // Test para buscar un usuario por correo y contrasena con contrasena incorrecta:
    @Test
    @DisplayName("Buscar Usuario por Correo y Contrasena - Contrasena Incorrecta -> 401 UNAUTHORIZED")
    void buscarUsuarioPorCorreoYContrasena_Unauthorized() {

        // Creamos el Usuario con datos validos:
        Usuario usuario = new Usuario();
        usuario.setContrasenaHash("123");

        // Simulamos el comportamiento del UsuarioDAO para la busqueda de un usuario por
        // correo, devolviendo un usuario existente:
        when(usuarioDAO.buscarUsuarioEntidadPorCorreo("juan@gmail.com")).thenReturn(Optional.of(usuario));  

        // Ejecutamos el metodo a probar y verificamos que se lance la excepcion
        // correspondiente:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.buscarUsuarioPorCorreoYContrasena("juan@gmail.com", "456"));

        // Verificamos que el codigo de estado de la excepcion sea 401 UNAUTHORIZED:
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());

    }

    // Test para buscar un usuario por correo y contrasena con contrasena nula:
    @Test
    @DisplayName("Buscar Usuario por Correo y Contrasena - Contrasena Nula -> 400 BAD_REQUEST")
    void buscarUsuarioPorCorreoYContrasena_ContrasenaNula() {

        // Creamos el Usuario con datos validos:
        Usuario usuario = new Usuario();
        usuario.setContrasenaHash("123");

        // Simulamos el comportamiento del UsuarioDAO para la busqueda de un usuario por
        // correo, devolviendo un usuario existente:
        when(usuarioDAO.buscarUsuarioEntidadPorCorreo("juan@gmail.com")).thenReturn(Optional.of(usuario));

        // Ejecutamos el metodo a probar y verificamos que se lance la excepcion
        // correspondiente:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.buscarUsuarioPorCorreoYContrasena("juan@gmail.com", null));

        // Verificamos que el codigo de estado de la excepcion sea 400 BAD_REQUEST:
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());

    }

    // Test para obtener la lista de usuarios:
    @Test
    @DisplayName("Lista Usuarios - Exito -> 200 OK")
    void listaUsuarios_OK() {

        // Simulamos el comportamiento del UsuarioDAO para obtener la lista de usuarios,
        // devolviendo una lista con un usuario:
        when(usuarioDAO.listaUsuarios()).thenReturn(List.of(new UsuarioDTO()));

        // Ejecutamos el metodo a probar:
        List<UsuarioDTO> resultado = usuarioService.listaUsuarios();

        // Verificamos que el resultado no sea nulo:
        assertNotNull(resultado);

        // Verificamos que el resultado tenga un usuario:
        assertEquals(1, resultado.size());

    }

    // Test para obtener la lista de usuarios cuando no hay usuarios registrados:
    @Test
    @DisplayName("Lista Usuarios - No hay usuarios -> 200 OK")
    void listaUsuarios_Empty() {

        // Simulamos el comportamiento del UsuarioDAO para obtener la lista de usuarios,
        // devolviendo una lista vacia:
        when(usuarioDAO.listaUsuarios()).thenReturn(List.of());

        // Ejecutamos el metodo a probar:
        List<UsuarioDTO> resultado = usuarioService.listaUsuarios();

        // Verificamos que el resultado no sea nulo:
        assertNotNull(resultado);

        // Verificamos que el resultado este vacio:
        assertEquals(0, resultado.size());

    }

    // Test para actualizar un usuario con datos validos:
    @Test
    @DisplayName("Actualizar Usuario - Datos Validos -> 200 OK")
    void actualizarUsuario_OK() {

        // Creamos el DTO de entrada con datos validos:
        UsuarioUpdateDTO dto = new UsuarioUpdateDTO();
        dto.setNombre("juan alberto");
        dto.setApellido("perez gonzales");
        dto.setCelular("311442");
        dto.setIdOrganizacion(1L);
        dto.setIdRol(1L);

        // Creamos el Usuario con datos validos:
        Usuario usuario = new Usuario();

        // Simulamos el comportamiento del UsuarioDAO para la busqueda de un usuario por id, devolviendo un usuario existente:
        when(usuarioDAO.buscarUsuarioEntidadPorId(1L)).thenReturn(Optional.of(usuario));
        // Simulamos el comportamiento del OrganizacionDAO para el id de organizacion del DTO:
        when(organizacionDAO.buscarOrganizacionEntidadPorId(1L)).thenReturn(Optional.of(new Organizacion()));
        // Simulamos el comportamiento del RolDAO para el id de rol del DTO:
        when(rolDAO.buscarRolEntidadPorId(1L)).thenReturn(Optional.of(new Rol()));

        // Simulamos el comportamiento del UsuarioDAO para actualizar el usuario con cualquier parametro, devolviendo un usuario actualizado:
        when(usuarioDAO.actualizarUsuario(eq(1L), any(), any(), any())).thenReturn(Optional.of(new UsuarioDTO()));

        // Ejecutamos el metodo a probar:
        UsuarioDTO resultado = usuarioService.actualizarUsuario(dto, 1L);

        // Verificamos que el resultado no sea nulo:
        assertNotNull(resultado);

        // Verificamos que se hayan llamado los metodos correspondientes del UsuarioDAO, OrganizacionDAO y RolDAO:
        verify(usuarioDAO).buscarUsuarioEntidadPorId(1L);
        verify(organizacionDAO).buscarOrganizacionEntidadPorId(1L);
        verify(rolDAO).buscarRolEntidadPorId(1L);
        verify(usuarioDAO).actualizarUsuario(eq(1L), any(), any(), any());

    }

    // Test para actualizar un usuario con id nulo:
    @Test
    @DisplayName("Actualizar Usuario - ID Nulo -> 400 BAD_REQUEST")
    void actualizarUsuario_BadRequest() {

        // Creamos el DTO de entrada con datos validos:
        UsuarioUpdateDTO dto = new UsuarioUpdateDTO();
        dto.setNombre("juan alberto");
        dto.setApellido("perez gonzales");
        dto.setCelular("311442");
        dto.setIdOrganizacion(1L);
        dto.setIdRol(1L);

        // Ejecutamos el metodo a probar y verificamos que se lance la excepcion correspondiente:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.actualizarUsuario(dto, null));

        // Verificamos que el codigo de estado de la excepcion sea 400 BAD_REQUEST:
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());

    }

    // Test para actualizar un usuario con id no existente:
    @Test
    @DisplayName("Actualizar Usuario - ID No Existente -> 404 NOT_FOUND")
    void actualizarUsuario_NotFound() {

        // Creamos el DTO de entrada con datos validos:
        UsuarioUpdateDTO dto = new UsuarioUpdateDTO();
        dto.setNombre("juan alberto");
        dto.setApellido("perez gonzales");
        dto.setCelular("311442");
        dto.setIdOrganizacion(1L);
        dto.setIdRol(1L);

        // Simulamos el comportamiento del UsuarioDAO para la busqueda de un usuario por id, devolviendo un Optional vacio:
        when(usuarioDAO.buscarUsuarioEntidadPorId(1L)).thenReturn(Optional.empty());

        // Ejecutamos el metodo a probar y verificamos que se lance la excepcion correspondiente:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.actualizarUsuario(dto, 1L));

        // Verificamos que el codigo de estado de la excepcion sea 404 NOT_FOUND:
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());

    }

    // Test para actualizar un usuario con datos nulos:
    @Test
    @DisplayName("Actualizar Usuario - Datos Nulos -> 400 BAD_REQUEST")
    void actualizarUsuario_DatosNulos() {

        // Ejecutamos el metodo a probar y verificamos que se lance la excepcion correspondiente:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.actualizarUsuario(null, 1L));

        // Verificamos que el codigo de estado de la excepcion sea 400 BAD_REQUEST:
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());

    }

    // Test para actualizar un usuario con id de organizacion no existente:
    @Test
    @DisplayName("Actualizar Usuario - ID Organizacion No Existente -> 404 NOT_FOUND")
    void actualizarUsuario_OrganizacionNotFound() {

        // Creamos el DTO de entrada con datos validos:
        UsuarioUpdateDTO dto = new UsuarioUpdateDTO();
        dto.setNombre("juan alberto");
        dto.setApellido("perez gonzales");
        dto.setCelular("311442");
        dto.setIdOrganizacion(1L);
        dto.setIdRol(1L);

        // Creamos el Usuario con datos validos:
        Usuario usuario = new Usuario();

        // Simulamos el comportamiento del UsuarioDAO para la busqueda de un usuario por id, devolviendo un usuario existente:
        when(usuarioDAO.buscarUsuarioEntidadPorId(1L)).thenReturn(Optional.of(usuario));
        // Simulamos el comportamiento del OrganizacionDAO para el id de organizacion del DTO, devolviendo un Optional vacio:
        when(organizacionDAO.buscarOrganizacionEntidadPorId(1L)).thenReturn(Optional.empty());

        // Ejecutamos el metodo a probar y verificamos que se lance la excepcion correspondiente:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.actualizarUsuario(dto, 1L));

        // Verificamos que el codigo de estado de la excepcion sea 404 NOT_FOUND:
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());

    }

    // Test para actualizar un usuario con id de rol no existente:
    @Test
    @DisplayName("Actualizar Usuario - ID Rol No Existente -> 404 NOT_FOUND")
    void actualizarUsuario_RolNotFound() {

        // Creamos el DTO de entrada con datos validos:
        UsuarioUpdateDTO dto = new UsuarioUpdateDTO();
        dto.setNombre("juan alberto");
        dto.setApellido("perez gonzales");
        dto.setCelular("311442");
        dto.setIdOrganizacion(1L);
        dto.setIdRol(1L);

        // Creamos el Usuario con datos validos:
        Usuario usuario = new Usuario();

        // Simulamos el comportamiento del UsuarioDAO para la busqueda de un usuario por id, devolviendo un usuario existente:
        when(usuarioDAO.buscarUsuarioEntidadPorId(1L)).thenReturn(Optional.of(usuario));
        // Simulamos el comportamiento del OrganizacionDAO para el id de organizacion del DTO, devolviendo un Optional con una organizacion:
        when(organizacionDAO.buscarOrganizacionEntidadPorId(1L)).thenReturn(Optional.of(new Organizacion()));
        // Simulamos el comportamiento del RolDAO para el id de rol del DTO, devolviendo un Optional vacio:
        when(rolDAO.buscarRolEntidadPorId(1L)).thenReturn(Optional.empty());

        // Ejecutamos el metodo a probar y verificamos que se lance la excepcion correspondiente:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.actualizarUsuario(dto, 1L));

        // Verificamos que el codigo de estado de la excepcion sea 404 NOT_FOUND:
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());

    }

    // Test para actualizar la contrasena de un usuario con datos validos:
    @Test
    @DisplayName("Actualizar Contrasena - Datos Validos -> 200 OK")
    void actualizarContrasena_OK() {

        // Creamos el Usuario con datos validos:
        Usuario usuario = new Usuario();
        usuario.setContrasenaHash("123");

        // Creamos el DTO de entrada con datos validos:
        UsuarioUpdateContrasenaDTO dto = new UsuarioUpdateContrasenaDTO();
        dto.setIdUsuario(1L);
        dto.setContrasenaActual("123");
        dto.setContrasenaNueva("456");
        dto.setContrasenaConfirmacion("456");

        // Simulamos el comportamiento del UsuarioDAO para la busqueda de un usuario por id, devolviendo un usuario existente:
        when(usuarioDAO.buscarUsuarioEntidadPorId(1L)).thenReturn(Optional.of(usuario));
        // Simulamos el comportamiento del UsuarioDAO para actualizar la contrasena de un usuario con cualquier parametro, devolviendo un usuario actualizado:
        when(usuarioDAO.actualizarContrasena(1L, "456")).thenReturn(Optional.of(new UsuarioDTO()));

        // Ejecutamos el metodo a probar:
        UsuarioDTO resultado = usuarioService.actualizarContrasena(dto);

        // Verificamos que el resultado no sea nulo:
        assertNotNull(resultado);
    
        // Verificamos que se hayan llamado los metodos correspondientes del UsuarioDAO:
        verify(usuarioDAO).buscarUsuarioEntidadPorId(1L);
        verify(usuarioDAO).actualizarContrasena(1L, "456");
    
    }

    // Test para actualizar la contrasena de un usuario con id de usuario nulo:
    @Test
    @DisplayName("Actualizar Contrasena - ID Usuario Nulo -> 400 BAD_REQUEST")
    void actualizarContrasena_IdUsuarioNulo() {

        // Creamos el DTO de entrada con datos validos:
        UsuarioUpdateContrasenaDTO dto = new UsuarioUpdateContrasenaDTO();
        dto.setIdUsuario(null);
        dto.setContrasenaActual("123");
        dto.setContrasenaNueva("456");
        dto.setContrasenaConfirmacion("456");

        // Ejecutamos el metodo a probar y verificamos que se lance la excepcion correspondiente:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.actualizarContrasena(dto));

        // Verificamos que el codigo de estado de la excepcion sea 400 BAD_REQUEST:
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());

    }

    // Test para actualizar la contrasena de un usuario con usuario no existente:
    @Test
    @DisplayName("Actualizar Contrasena - Usuario No Existente -> 404 NOT_FOUND")
    void actualizarContrasena_UsuarioNotFound() {

        // Creamos el DTO de entrada con datos validos:
        UsuarioUpdateContrasenaDTO dto = new UsuarioUpdateContrasenaDTO();
        dto.setIdUsuario(1L);
        dto.setContrasenaActual("123");
        dto.setContrasenaNueva("456");
        dto.setContrasenaConfirmacion("456");

        // Simulamos el comportamiento del UsuarioDAO para la busqueda de un usuario por id, devolviendo un Optional vacio:
        when(usuarioDAO.buscarUsuarioEntidadPorId(1L)).thenReturn(Optional.empty());

        // Ejecutamos el metodo a probar y verificamos que se lance la excepcion correspondiente:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.actualizarContrasena(dto));

        // Verificamos que el codigo de estado de la excepcion sea 404 NOT_FOUND:
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());

    }

    // Test para actualizar la contrasena de un usuario con contrasena actual incorrecta:
    @Test
    @DisplayName("Actualizar Contrasena - Contrasena Actual Incorrecta -> 401 UNAUTHORIZED")
    void actualizarContrasena_ContrasenaActualIncorrecta() {

        // Creamos el Usuario con datos validos:
        Usuario usuario = new Usuario();
        usuario.setContrasenaHash("123");

        // Creamos el DTO de entrada con datos validos:
        UsuarioUpdateContrasenaDTO dto = new UsuarioUpdateContrasenaDTO();
        dto.setIdUsuario(1L);
        dto.setContrasenaActual("456");
        dto.setContrasenaNueva("789");
        dto.setContrasenaConfirmacion("789");

        // Simulamos el comportamiento del UsuarioDAO para la busqueda de un usuario por id, devolviendo un usuario existente:
        when(usuarioDAO.buscarUsuarioEntidadPorId(1L)).thenReturn(Optional.of(usuario));

        // Ejecutamos el metodo a probar y verificamos que se lance la excepcion correspondiente:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.actualizarContrasena(dto));

        // Verificamos que el codigo de estado de la excepcion sea 401 UNAUTHORIZED:
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());

    }

    // Test para actualizar la contrasena de un usuario con contrasena nueva y confirmacion no coincidentes:
    @Test
    @DisplayName("Actualizar Contrasena - Contrasena Nueva y Confirmacion No Coinciden -> 400 BAD_REQUEST")
    void actualizarContrasena_ContrasenaNuevaConfirmacionNoCoinciden() {

        // Creamos el Usuario con datos validos:
        Usuario usuario = new Usuario();
        usuario.setContrasenaHash("123");

        // Creamos el DTO de entrada con datos validos:
        UsuarioUpdateContrasenaDTO dto = new UsuarioUpdateContrasenaDTO();
        dto.setIdUsuario(1L);
        dto.setContrasenaActual("123");
        dto.setContrasenaNueva("456");
        dto.setContrasenaConfirmacion("789");

        // Simulamos el comportamiento del UsuarioDAO para la busqueda de un usuario por id, devolviendo un usuario existente:
        when(usuarioDAO.buscarUsuarioEntidadPorId(1L)).thenReturn(Optional.of(usuario));

        // Ejecutamos el metodo a probar y verificamos que se lance la excepcion correspondiente:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.actualizarContrasena(dto));

        // Verificamos que el codigo de estado de la excepcion sea 400 BAD_REQUEST:
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());

    }

    // Test para actualizar la contrasena de un usuario con la contrasena incorrecta a la actual:
    @Test
    @DisplayName("Actualizar Contrasena - Contrasena Incorrecta -> 401 UNAUTHORIZED")
    void actualizarContrasena_ContrasenaIncorrecta() {

        // Creamos el Usuario con datos validos:
        Usuario usuario = new Usuario();
        usuario.setContrasenaHash("123");

        // Creamos el DTO de entrada con datos validos:
        UsuarioUpdateContrasenaDTO dto = new UsuarioUpdateContrasenaDTO();
        dto.setIdUsuario(1L);
        dto.setContrasenaActual("456");
        dto.setContrasenaNueva("789");
        dto.setContrasenaConfirmacion("789");

        // Simulamos el comportamiento del UsuarioDAO para la busqueda de un usuario por id, devolviendo un usuario existente:
        when(usuarioDAO.buscarUsuarioEntidadPorId(1L)).thenReturn(Optional.of(usuario));

        // Ejecutamos el metodo a probar y verificamos que se lance la excepcion correspondiente:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.actualizarContrasena(dto));

        // Verificamos que el codigo de estado de la excepcion sea 401 UNAUTHORIZED:
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());

    }

    // Test para eliminar un usuario con id valido:
    @Test
    @DisplayName("Eliminar Usuario - ID Valido -> 204 NO_CONTENT")
    void eliminarUsuario_OK() {

        // Simulamos el comportamiento del UsuarioDAO para eliminar un usuario por id, devolviendo true:
        when(usuarioDAO.eliminarUsuario(1L)).thenReturn(true);

        // Ejecutamos el metodo a probar:
        usuarioService.eliminarUsuario(1L);

        // Verificamos que se haya llamado el metodo correspondiente del UsuarioDAO:
        verify(usuarioDAO).eliminarUsuario(1L);

    }

    // Test para eliminar un usuario con id nulo:
    @Test
    @DisplayName("Eliminar Usuario - ID Nulo -> 400 BAD_REQUEST")
    void eliminarUsuario_BadRequest() {

        // Ejecutamos el metodo a probar y verificamos que se lance la excepcion correspondiente:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.eliminarUsuario(null));

        // Verificamos que el codigo de estado de la excepcion sea 400 BAD_REQUEST:
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());

    }

    // Test para eliminar un usuario con id no existente:
    @Test
    @DisplayName("Eliminar Usuario - ID No Existente -> 404 NOT_FOUND")
    void eliminarUsuario_NotFound() {

        // Simulamos el comportamiento del UsuarioDAO para eliminar un usuario por id, devolviendo false:
        when(usuarioDAO.eliminarUsuario(1L)).thenReturn(false);

        // Ejecutamos el metodo a probar y verificamos que se lance la excepcion correspondiente:
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.eliminarUsuario(1L));

        // Verificamos que el codigo de estado de la excepcion sea 404 NOT_FOUND:
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());

    }

}  