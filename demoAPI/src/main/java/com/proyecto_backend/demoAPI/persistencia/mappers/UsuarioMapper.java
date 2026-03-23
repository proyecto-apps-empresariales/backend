package com.proyecto_backend.demoAPI.persistencia.mappers;

import com.proyecto_backend.demoAPI.persistencia.entidades.Usuario;
import com.proyecto_backend.demoAPI.servicios.dtos.UsuarioCreateDTO;
import com.proyecto_backend.demoAPI.servicios.dtos.UsuarioDTO;

public class UsuarioMapper {

    // Metodo para convertir un Usuario --> UsuarioDTO:
    public static UsuarioDTO usuarioToDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        Long idOrganizacion = usuario.getOrganizacion() == null ? null : usuario.getOrganizacion().getIdOrganizacion();
        Long idRol = usuario.getRol() == null ? null : usuario.getRol().getIdRol();
        UsuarioDTO dto = new UsuarioDTO(usuario.getIdUsuario(), usuario.getNombre(), usuario.getApellido(),
                usuario.getCorreo(), usuario.getCelular(), usuario.getFechaCreacion(), usuario.getEstaActivo(),
                idOrganizacion, idRol);
        return dto;
    }

    // Metodo para crear un usuario con un UsuarioCreateDTO:
    public static Usuario dtoToEntity(UsuarioCreateDTO dto) {

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setContrasenaHash(dto.getContrasena());
        usuario.setCelular(dto.getCelular());

        return usuario;

    }

}
