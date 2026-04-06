package com.proyecto_backend.demoAPI.persistenceLayer.mappers;

import java.util.List;

import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioUpdateDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Organizacion;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Rol;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;

public class UsuarioMapper {

    // Metodo para convertir un Usuario --> UsuarioDTO:
    public static UsuarioDTO toDTO (Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        Long idOrganizacion = usuario.getOrganizacion() == null ? null : usuario.getOrganizacion().getIdOrganizacion();
        Long idRol = usuario.getRol() == null ? null : usuario.getRol().getIdRol();
        String nombreOrganizacion = usuario.getOrganizacion() == null ? null : usuario.getOrganizacion().getNombre();
        String nombreRol = usuario.getRol() == null ? null : usuario.getRol().getNombre();
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setCorreo(usuario.getCorreo());
        dto.setCelular(usuario.getCelular());
        dto.setFechaCreacion(usuario.getFechaCreacion());
        dto.setEstaActivo(usuario.isEstaActivo());
        dto.setIdOrganizacion(idOrganizacion);
        dto.setNombreOrganizacion(nombreOrganizacion);
        dto.setIdRol(idRol);
        dto.setNombreRol(nombreRol);
        return dto;
    }

    // Metodo para convertir un UsuarioCreateDTO --> Usuario:
    public static Usuario toEntity (UsuarioCreateDTO dto) {

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setContrasenaHash(dto.getContrasena());
        usuario.setCelular(dto.getCelular());

        return usuario;

    }

    // Metodo para actualizar parcialmente una organizacion:
    public static void updateEntityFromDTO (UsuarioUpdateDTO dto, Usuario usuario, Organizacion organizacion, Rol rol) {

        if (dto.getNombre() != null) {
            usuario.setNombre(dto.getNombre());
        }
        if (dto.getApellido() != null) {
            usuario.setApellido(dto.getApellido());
        }
        if (dto.getCelular() != null) {
            usuario.setCelular(dto.getCelular());
        }
        if (dto.getEstaActivo() != null) {
            usuario.setEstaActivo(dto.getEstaActivo());
        }
        if (organizacion != null) {
            usuario.setOrganizacion(organizacion);
        }
        if (rol != null) {
            usuario.setRol(rol);
        }
        
    }

    // Metodo para convertir una lista de Usuario --> lista de UsuarioDTO:
    public static List<UsuarioDTO> toDTOList (List<Usuario> usuarios) {
        return usuarios.stream().map(UsuarioMapper::toDTO).toList();
    }

}
