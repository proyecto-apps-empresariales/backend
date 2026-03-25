package com.proyecto_backend.demoAPI.persistencia.mappers;

import java.util.List;

import com.proyecto_backend.demoAPI.persistencia.entidades.Usuario;
import com.proyecto_backend.demoAPI.servicios.dtos.UsuarioCreateDTO;
import com.proyecto_backend.demoAPI.servicios.dtos.UsuarioDTO;

public class UsuarioMapper {

    // Metodo para convertir un Usuario --> UsuarioDTO:
    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        Long idOrganizacion = usuario.getOrganizacion() == null ? null : usuario.getOrganizacion().getIdOrganizacion();
        Long idRol = usuario.getRol() == null ? null : usuario.getRol().getIdRol();
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setCorreo(usuario.getCorreo());
        dto.setCelular(usuario.getCelular());
        dto.setFechaCreacion(usuario.getFechaCreacion());
        dto.setEstaActivo(usuario.getEstaActivo());
        dto.setIdOrganizacion(idOrganizacion);
        dto.setIdRol(idRol);
        return dto;
    }

    // Metodo para convertir un UsuarioCreateDTO --> Usuario:
    public static Usuario toEntity(UsuarioCreateDTO dto) {

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setContrasenaHash(dto.getContrasena());
        usuario.setCelular(dto.getCelular());

        return usuario;

    }

    // Metodo para convertir una lista de Usuario --> lista de UsuarioDTO:
    public static List<UsuarioDTO> toDTOList (List<Usuario> usuarios) {
        return usuarios.stream().map(UsuarioMapper::toDTO).toList();
    }

}
