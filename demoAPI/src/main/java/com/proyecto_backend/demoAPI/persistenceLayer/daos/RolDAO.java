package com.proyecto_backend.demoAPI.persistenceLayer.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.proyecto_backend.demoAPI.businessLayer.dtos.RolCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RolDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RolUpdateDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Rol;
import com.proyecto_backend.demoAPI.persistenceLayer.mappers.RolMapper;
import com.proyecto_backend.demoAPI.persistenceLayer.repositories.IRolRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RolDAO {

    // Inyectamos el repositorio de Rol:
    private final IRolRepository rolRepository;

    // Metodo para guardar un rol por medio de un RolCreateDTO:
    public RolDTO guardarRol (RolCreateDTO dto) {

        Rol rol = RolMapper.toEntity(dto);

        return RolMapper.toDTO(rolRepository.save(rol));
         
    }

    // Metodo para obtener una lista de todos los roles:
    public List<RolDTO> listaRoles () {

        return RolMapper.toDTOList(rolRepository.findAll());

    }

    // Metodo para obtener un rol por medio de su id:
    public Optional<RolDTO> buscarRolPorId (Long idRol) {

        return rolRepository.findById(idRol).map(RolMapper::toDTO);

    }

    // Metodo para buscar una organizacion por id y retornar la entidad (casos especiales):
    public Optional<Rol> buscarRolEntidadPorId (Long idRol) {

        return rolRepository.findById(idRol);
        
    }

    // Metodo para obtener un rol por medio de su nombre:
    public Optional<RolDTO> buscarRolPorNombre (String nombreRol) {

        return rolRepository.findByNombre(nombreRol).map(RolMapper::toDTO);

    }

    // Metodo para actualizar un rol:
    public Optional<RolDTO> actualizarRol (RolUpdateDTO dto, Long idRol) {

        return rolRepository.findById(idRol).map(rol -> {
            RolMapper.updateEntityFromDTO(dto, rol);
            return RolMapper.toDTO(rolRepository.save(rol));
        });

    }

    // Metodo para eliminar un rol:
    public boolean eliminarRol (Long idRol) {

        if (rolRepository.existsById(idRol)) {
            rolRepository.deleteById(idRol);    
            return true;
        }
        return false;
        
    }
    
}
