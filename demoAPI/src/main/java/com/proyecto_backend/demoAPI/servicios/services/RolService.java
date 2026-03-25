package com.proyecto_backend.demoAPI.servicios.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.proyecto_backend.demoAPI.persistencia.entidades.Rol;
import com.proyecto_backend.demoAPI.persistencia.mappers.RolMapper;
import com.proyecto_backend.demoAPI.persistencia.repositorios.RolRepository;
import com.proyecto_backend.demoAPI.servicios.dtos.RolCreateDTO;
import com.proyecto_backend.demoAPI.servicios.dtos.RolDTO;
import com.proyecto_backend.demoAPI.servicios.dtos.RolUpdateDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RolService {

    // Creamos las instancias de los repositorios correspondientes:
    private final RolRepository rolRepository;

    // Metodo para guardar un rol por medio de un RolCreateDTO:
    @Transactional
    public RolDTO guardarRol(RolCreateDTO dto) {

        if (dto == null) { // 400 BAD_REQUEST: Datos No validos
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos no validos");
        }
        if (rolRepository.findByNombre(dto.getNombre()).isPresent()) { // 409 CONFLICT: Dato ya existente
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nombre ya registrado");
        }

        Rol rol = RolMapper.toEntity(dto);

        return RolMapper.toDTO(rolRepository.save(rol));
    }

    // Metodo para obtener la lista de todos los roles:
    public List<RolDTO> listaRoles() {

        return RolMapper.toDTOList(rolRepository.findAll());
    }

    // Metodo para obtener un rol por medio de su id:
    public RolDTO buscarRolPorId (Long idRol) {

        return RolMapper.toDTO(buscarRolId(idRol));
    }

    // Metodo para obtener un rol por medio de su nombre:
    public RolDTO buscarRolPorNombre (String nombre) {
        
        return RolMapper.toDTO(buscarRolNombre(nombre));
    }

    // Metodo para actualizar un rol:
    @Transactional
    public RolDTO actualizarRol (RolUpdateDTO dto, Long idRol) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }
        Rol rol = buscarRolId(idRol);

        if (dto.getNombre() != null) {
            rol.setNombre(dto.getNombre());
        }
        if (dto.getDescripcion() != null) {
            rol.setDescripcion(dto.getDescripcion());
        }

        return RolMapper.toDTO(rolRepository.save(rol));
    }

    // Metodo para eliminar un rol:
    @Transactional
    public void eliminarRol (Long idRol) {

        rolRepository.delete(buscarRolId(idRol));
    }

    // Metodo privado para buscar un rol por id:
    private Rol buscarRolId(Long idRol) {
        if (idRol == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }
        return rolRepository.findById(idRol)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado"));
    }

    // Metodo privado para buscar un rol por nombre:
    private Rol buscarRolNombre(String nombre) {
        if (nombre == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }
        return rolRepository.findByNombre(nombre)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado"));
    }

}
