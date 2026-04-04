package com.proyecto_backend.demoAPI.businessLayer.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioUpdateDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Organizacion;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Rol;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;
import com.proyecto_backend.demoAPI.persistenceLayer.mappers.UsuarioMapper;
import com.proyecto_backend.demoAPI.persistenceLayer.repositories.OrganizacionRepository;
import com.proyecto_backend.demoAPI.persistenceLayer.repositories.RolRepository;
import com.proyecto_backend.demoAPI.persistenceLayer.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    // Creamos las instancias de los repositorios correspondientes:
    private final UsuarioRepository usuarioRepository;
    private final OrganizacionRepository organizacionRepository;
    private final RolRepository rolRepository;

    // Metodo para guardar un usuario por medio de un UsuarioDTO:
    @Transactional
    public UsuarioDTO guardarUsuario(UsuarioCreateDTO dto) {

        if (dto == null) { // 400 BAD_REQUEST: Datos No validos
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos no valido");
        }

        if (usuarioRepository.findByCorreo(dto.getCorreo()).isPresent()) { // 409 CONFLICT: Dato ya existente
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Correo ya registrado");
        }

        Organizacion organizacion = buscarOrganizacion(dto.getIdOrganizacion());
        Rol rol = buscarRol(dto.getIdRol());

        Usuario usuarioNuevo = UsuarioMapper.toEntity(dto);

        usuarioNuevo.setFechaCreacion(LocalDate.now());
        usuarioNuevo.setEstaActivo(true);
        usuarioNuevo.setOrganizacion(organizacion);
        usuarioNuevo.setRol(rol);

        return UsuarioMapper.toDTO(usuarioRepository.save(usuarioNuevo));
    }

    // Metodo para obtener la lista de todos los usuarios:
    public List<UsuarioDTO> listaUsuarios() {

        return UsuarioMapper.toDTOList(usuarioRepository.findAll());
    }

    // Metodo para obtener un usuario por medio de su id:
    public UsuarioDTO buscarUsuarioPorId (Long idUsuario) {

        return UsuarioMapper.toDTO(buscarUsuarioId(idUsuario));
    }

    // Metodo para obtener un usuario por medio de su correo:
    public UsuarioDTO buscarUsuarioPorCorreo(String correo) {

        return UsuarioMapper.toDTO(buscarUsuarioCorreo(correo));
    }

    // Metodo para obtener la lista de usuarios por Organizacion:
    public List<UsuarioDTO> listaUsuariosPorOrganizacion(Long idOrganizacion) {

        buscarOrganizacion(idOrganizacion);

        return UsuarioMapper.toDTOList(usuarioRepository.findByOrganizacion_IdOrganizacion(idOrganizacion));
    }

    // Metodo para actualizar un usuario:
    @Transactional
    public UsuarioDTO actualizarUsuario (UsuarioUpdateDTO dto, Long id) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos no validos");
        }

        Usuario usuario = buscarUsuarioId(id);

        if (dto.getNombre() != null) {
            usuario.setNombre(dto.getNombre());
        }
        if (dto.getApellido() != null) {
            usuario.setApellido(dto.getApellido());
        }
        if (dto.getContrasena() != null) {
            usuario.setContrasenaHash(dto.getContrasena());
        }
        if (dto.getCelular() != null) {
            usuario.setCelular(dto.getCelular());
        }
        if (dto.getEstaActivo() != null) {
            usuario.setEstaActivo(dto.getEstaActivo());
        }
        if (dto.getIdOrganizacion() != null) {
            usuario.setOrganizacion(buscarOrganizacion(dto.getIdOrganizacion()));
        }
        if (dto.getIdRol() != null) {
            usuario.setRol(buscarRol(dto.getIdRol()));
        }

        return UsuarioMapper.toDTO(usuarioRepository.save(usuario));
    }

    // Metodo para eliminar un usuario:
    @Transactional
    public void eliminarUsuario(Long idUsuario) {
        
        usuarioRepository.delete(buscarUsuarioId(idUsuario));
    }

    // Metodo privado para buscar una organizacion por ID:
    private Organizacion buscarOrganizacion(Long idOrganizacion) {

        if (idOrganizacion == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valida");
        }

        return organizacionRepository.findById(idOrganizacion)  // 404 NOT_FOUND: Recurso no existe
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Organizacion no encontrado"));
    }

    // Metodo privado para buscar un rol por ID:
    private Rol buscarRol(Long idRol) {

        if (idRol == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }

        return rolRepository.findById(idRol)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado"));
    }

    // Metodo privado para buscar un usuario por ID:
    private Usuario buscarUsuarioId(Long idUsuario) {

        if (idUsuario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }

        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    // Metodo privado para buscar un usuario por correo:
    private Usuario buscarUsuarioCorreo(String correo) {

        if (correo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Correo no valido");
        }
        
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }
}
