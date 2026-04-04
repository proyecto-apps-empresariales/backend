package com.proyecto_backend.demoAPI.persistenceLayer.mappers;

import java.util.List;

import com.proyecto_backend.demoAPI.businessLayer.dtos.RolCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RolDTO;
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

        return dto;
    }

    // Metodo para convertir un RolCreateDTO --> Rol:
    public static Rol toEntity (RolCreateDTO dto) {

        Rol rol = new Rol();
        rol.setNombre(dto.getNombre());
        rol.setDescripcion(dto.getDescripcion());

        return rol;
    }

    // Metodo para convertir una lista de Rol --> lista de RolDTO:
    public static List<RolDTO> toDTOList (List<Rol> roles) {
        return roles.stream().map(RolMapper::toDTO).toList();
    }
}
