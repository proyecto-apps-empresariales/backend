package com.proyecto_backend.demoAPI.persistenceLayer.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.UsuarioUpdateDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Organizacion;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Rol;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;
import com.proyecto_backend.demoAPI.persistenceLayer.mappers.UsuarioMapper;
import com.proyecto_backend.demoAPI.persistenceLayer.repositories.IUsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuarioDAO {
    
    // Creamos las instancias de los repositorios:
    private final IUsuarioRepository usuarioRepository;
    
    // Metodo para guardar un usuario por medio de un UsuarioDTO:
    public UsuarioDTO guardarUsuario (UsuarioCreateDTO dto, Organizacion organizacion, Rol rol) {

        Usuario usuario = UsuarioMapper.toEntity(dto);

        usuario.setEstaActivo(true);
        usuario.setOrganizacion(organizacion);
        usuario.setRol(rol);

        return UsuarioMapper.toDTO(usuarioRepository.save(usuario));
        
    }

    // Metodo para obtener la lista de todos los usuarios:
    public List<UsuarioDTO> listaUsuarios () {

        return UsuarioMapper.toDTOList(usuarioRepository.findAll());
    
    }

    // Metodo para obtener un usuario por medio de su id:
    public Optional<UsuarioDTO> buscarUsuarioPorId (Long idUsuario) {

        return usuarioRepository.findById(idUsuario).map(UsuarioMapper::toDTO);

    }

    // Metodo para buscar un usuario por id y retornar la entidad (casos especiales):
    public Optional<Usuario> buscarUsuarioEntidadPorId (Long idUsuario) {

        return usuarioRepository.findById(idUsuario);
        
    }

    // Metodo para buscar un usuario por correo:
    public Optional<UsuarioDTO> buscarUsuarioPorCorreo (String correoUsuario) {

        return usuarioRepository.findByCorreo(correoUsuario).map(UsuarioMapper::toDTO);

    }


    // Metodo para buscar un usuario por correo y retornar la entidad (casos especiales):
    public Optional<Usuario> buscarUsuarioEntidadPorCorreo (String correoUsuario) {

        return usuarioRepository.findByCorreo(correoUsuario);

    }

    // Metodo para obtener la lista de usuarios por Organizacion:
    public List<UsuarioDTO> buscarUsuariosPorOrganizacion (Long idOrganizacion) {

        return UsuarioMapper.toDTOList(usuarioRepository.findByOrganizacion_IdOrganizacion(idOrganizacion));
    
    }

    // Metodo para actualizar un usuario:
    public Optional<UsuarioDTO> actualizarUsuario (Long idUsuario, UsuarioUpdateDTO dto, Organizacion organizacion, Rol rol) {

        return usuarioRepository.findById(idUsuario).map(usuario -> {
            UsuarioMapper.updateEntityFromDTO(dto, usuario, organizacion, rol);
            return UsuarioMapper.toDTO(usuarioRepository.save(usuario));
        });

    }

    // Metodo para actualizar la contrasena de un usuario:
    public Optional<UsuarioDTO> actualizarContrasena (Long idUsuario, String contrasenaHash) {

        return usuarioRepository.findById(idUsuario).map(usuario -> {
            usuario.setContrasenaHash(contrasenaHash);
            return UsuarioMapper.toDTO(usuarioRepository.save(usuario));
        });

    }

    // Metodo para eliminar un usuario:
    public boolean eliminarUsuario (Long idUsuario) {

        if (usuarioRepository.existsById(idUsuario)) {
            usuarioRepository.deleteById(idUsuario);
            return true;
        }
        return false;

    }


}
