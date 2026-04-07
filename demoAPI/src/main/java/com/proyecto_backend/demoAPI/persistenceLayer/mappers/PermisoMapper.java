package com.proyecto_backend.demoAPI.persistenceLayer.mappers;

import java.util.List;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PermisoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PermisoDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PermisoUpdateDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Permiso;

public class PermisoMapper {
    
    // Metodo para convertir una clase PermisoCreateDTO -> Permiso:
    public static Permiso toEntity (PermisoCreateDTO dto) {

        Permiso permiso = new Permiso();

        permiso.setNombre(dto.getNombre());
        permiso.setDescripcion(dto.getDescripcion());

        return permiso;

    }

    // Metodo para convertir una clase Permiso -> PermisoDTO:
    public static PermisoDTO toDTO (Permiso permiso) {

        if (permiso == null) {
            return null;
        }

        PermisoDTO dto = new PermisoDTO();

        dto.setIdPermiso(permiso.getIdPermiso());
        dto.setNombre(permiso.getNombre());
        dto.setDescripcion(permiso.getDescripcion());

        return dto;

    }

    // Metodo para actualizar un Permiso:
    public static void updateEntityFromDTO (PermisoUpdateDTO dto, Permiso permiso) {

        if (dto.getNombre() != null) {
            permiso.setNombre(dto.getNombre());
        }
        if (dto.getDescripcion() != null) {
            permiso.setDescripcion(dto.getDescripcion());
        }

    }

    // Metodo para convertir una lista de Permiso -> PermisoDTO:
    public static List<PermisoDTO> toListDTO (List<Permiso> permisos) {

        return permisos.stream().map(PermisoMapper::toDTO).toList();

    }
}
