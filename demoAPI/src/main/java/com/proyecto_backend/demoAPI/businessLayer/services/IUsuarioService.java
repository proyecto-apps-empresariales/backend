package com.proyecto_backend.demoAPI.businessLayer.services;

import java.util.List;

import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioUpdateContrasenaDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioUpdateDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;

public interface IUsuarioService {

    // Metodo para guardar un usuario por medio de un UsuarioDTO: 
    UsuarioDTO guardarUsuario (UsuarioCreateDTO dto);

    // Metodo para obtener la lista de todos los usuarios:
    List<UsuarioDTO> listaUsuarios ();

    // Metodo para obtener un usuario por medio de su id:
    UsuarioDTO buscarUsuarioPorId (Long idUsuario);

    // Metodo para obtener un usuario por su medio de su correo:
    UsuarioDTO buscarUsuarioPorCorreo (String correo);

    // Metodo para obtener un usuario por medio de su correo y contrasena:
    UsuarioDTO buscarUsuarioPorCorreoYContrasena (String correo, String contrasena);

    // Metodo para obtener la lista de usuarios por Organizacion:
    List<UsuarioDTO> listaUsuariosPorOrganizacion(Long idOrganizacion);
    
    // Metodo para actualizar un usuario:
    UsuarioDTO actualizarUsuario (UsuarioUpdateDTO dto, Long id);

    // Metodo para actualizar la contrasena de un usuario:
    UsuarioDTO actualizarContrasena (UsuarioUpdateContrasenaDTO dto);

    // Metodo para eliminar un usuario:
    void eliminarUsuario(Long idUsuario);

    //Busca por ID y Resuelve la entidad
    public Usuario buscarUsuarioEntityById(Long idUsuario);

}

