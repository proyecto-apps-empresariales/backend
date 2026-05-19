package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioUpdateContrasenaDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IUsuarioService;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.OrganizacionDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.RolDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.UsuarioDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Organizacion;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Rol;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioServiceImp implements IUsuarioService {

    // Creamos las instancias de los repositorios correspondientes:
    private final UsuarioDAO usuarioDAO;
    private final OrganizacionDAO organizacionDAO;
    private final RolDAO rolDAO;

    // Metodo para guardar un usuario por medio de un UsuarioDTO:
    @Override
    public UsuarioDTO guardarUsuario (UsuarioCreateDTO dto) {

        if (dto == null) { // 400 BAD_REQUEST: Datos No validos
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos no valido");
        }

        if (usuarioDAO.buscarUsuarioPorCorreo(dto.getCorreo()).isPresent()) { // 409 CONFLICT: Dato ya existente
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Correo ya registrado");
        }

        Organizacion organizacion = buscarOrganizacion(dto.getIdOrganizacion());
        Rol rol = buscarRol(dto.getIdRol());

        return usuarioDAO.guardarUsuario(dto, organizacion, rol);

    }

    // Metodo para obtener la lista de todos los usuarios:
    @Override
    public List<UsuarioDTO> listaUsuarios () {

        return usuarioDAO.listaUsuarios();

    }

    // Metodo para obtener un usuario por medio de su id:
    @Override
    public UsuarioDTO buscarUsuarioPorId (Long idUsuario) {

        if (idUsuario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }

        return usuarioDAO.buscarUsuarioPorId(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

    }

    // Metodo para obtener un usuario por medio de su correo:
    @Override
    public UsuarioDTO buscarUsuarioPorCorreo (String correo) {

        if (correo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Correo no valido");
        }

        return usuarioDAO.buscarUsuarioPorCorreo(correo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

    }

    // Metodo para obtener un usuario por medio de su correo y contrasena:
    @Override
    public UsuarioDTO buscarUsuarioPorCorreoYContrasena (String correo, String contrasena) {

        if (correo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Correo no valido");
        }

        Usuario usuario = buscarUsuarioCorreo(correo);

        if (contrasena == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contraseña no valida");
        }

        if (!usuario.getContrasenaHash().equals(contrasena)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Contraseña incorrecta");
        }

        if (!usuario.isEstaActivo()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario inactivo");
        }

        return usuarioDAO.buscarUsuarioPorCorreo(correo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

    }

    // Metodo para obtener la lista de usuarios por Organizacion:
    @Override
    public List<UsuarioDTO> listaUsuariosPorOrganizacion (Long idOrganizacion) {

        buscarOrganizacion(idOrganizacion);

        return usuarioDAO.buscarUsuariosPorOrganizacion(idOrganizacion);

    }

    // Metodo para actualizar un usuario:
    @Override
    public UsuarioDTO actualizarUsuario (UsuarioUpdateDTO dto, Long id) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos no validos");
        }

        buscarUsuarioEntityById(id);

        Organizacion organizacion = dto.getIdOrganizacion() != null ? buscarOrganizacion(dto.getIdOrganizacion())
                : null;
        Rol rol = dto.getIdRol() != null ? buscarRol(dto.getIdRol()) : null;

        return usuarioDAO.actualizarUsuario(id, dto, organizacion, rol)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

    }

    // Metodo para actualizar la contrasena de un usuario:
    @Override
    public UsuarioDTO actualizarContrasena (UsuarioUpdateContrasenaDTO dto) {

        Usuario usuario = buscarUsuarioEntityById(dto.getIdUsuario());

        validarContrasenas(dto);

        if (!usuario.getContrasenaHash().equals(dto.getContrasenaActual())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Contraseña incorrecta");
        }

        return usuarioDAO.actualizarContrasena(dto.getIdUsuario(), dto.getContrasenaNueva())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

    }

    // Metodo para eliminar un usuario:
    @Override
    public void eliminarUsuario (Long idUsuario) {

        if (idUsuario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }

        boolean eliminado = usuarioDAO.eliminarUsuario(idUsuario);

        if (eliminado == false) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }

    }

    // Metodo privado para buscar una organizacion por ID:
    private Organizacion buscarOrganizacion (Long idOrganizacion) {

        if (idOrganizacion == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valida");
        }

        return organizacionDAO.buscarOrganizacionEntidadPorId(idOrganizacion)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Organizacion no encontrada"));

    }

    // Metodo privado para buscar un rol por ID:
    private Rol buscarRol (Long idRol) {

        if (idRol == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }

        return rolDAO.buscarRolEntidadPorId(idRol)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado"));

    }

    // Metodo privado para buscar un usuario por ID:
    @Override
    public Usuario buscarUsuarioEntityById(Long idUsuario) {

        if (idUsuario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }

        return usuarioDAO.buscarUsuarioEntidadPorId(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

    }

    // Metodo privado para buscar un usuario por correo:
    private Usuario buscarUsuarioCorreo (String correo) {

        if (correo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Correo no valido");
        }

        return usuarioDAO.buscarUsuarioEntidadPorCorreo(correo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

    }

    // Metodo privado para validar contrasenas de un usuario:
    private void validarContrasenas (UsuarioUpdateContrasenaDTO dto) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos no validos");
        }
        if (dto.getContrasenaActual() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contraseña no valida");
        }
        if (dto.getContrasenaNueva() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contraseña no valida");
        }
        if (dto.getContrasenaConfirmacion() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contraseña no valida");
        }
        if (!dto.getContrasenaNueva().equals(dto.getContrasenaConfirmacion())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Las contraseñas no coinciden");
        }

    }

}
