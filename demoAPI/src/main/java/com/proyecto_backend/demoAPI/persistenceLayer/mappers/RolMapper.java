package com.proyecto_backend.demoAPI.persistenceLayer.mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PermisoDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RolCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RolDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RolUpdateDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Permiso;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Rol;

public class RolMapper {
    
    // Metodo para convertir un Rol --> RolDTO:
    public static RolDTO toDTO (Rol rol) {

        if (rol == null) {
            return null;
        }

        RolDTO dto = new RolDTO();
        dto.setIdRol(rol.getIdRol());
        dto.setNombre(rol.getNombre());
        dto.setDescripcion(rol.getDescripcion());
        Set<PermisoDTO> permisosDTO = rol.getPermisos().stream().map(PermisoMapper::toDTO).collect(Collectors.toSet());
        dto.setPermisos(permisosDTO);

        return dto;
    }

    // Metodo para convertir un RolCreateDTO --> Rol:
    public static Rol toEntity (RolCreateDTO dto, Set<Permiso> permisos) {

        Rol rol = new Rol();
        rol.setNombre(dto.getNombre());
        rol.setDescripcion(dto.getDescripcion());
        rol.setPermisos(permisos);

        return rol;
    }

    // Metodo para actualizar parcialmente un rol:
    public static void updateEntityFromDTO (RolUpdateDTO dto, Rol rol) {

        if (dto.getNombre() != null) {
            rol.setNombre(dto.getNombre()); 
        }
        if (dto.getDescripcion() != null) {
            rol.setDescripcion(dto.getDescripcion());
        }
        
    }

    // Metodo para convertir una lista de Rol --> lista de RolDTO:
    public static List<RolDTO> toDTOList (List<Rol> roles) {
        return roles.stream().map(RolMapper::toDTO).toList();
    }
}
