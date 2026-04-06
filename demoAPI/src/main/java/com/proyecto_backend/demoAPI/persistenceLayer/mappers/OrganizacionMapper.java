package com.proyecto_backend.demoAPI.persistenceLayer.mappers;

import java.util.List;

import com.proyecto_backend.demoAPI.businessLayer.dtos.OrganizacionCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.OrganizacionDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.OrganizacionUpdateDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Organizacion;

public class OrganizacionMapper {

    // Metodo para convertir un Organizacion --> OrganizacionDTO:
    public static OrganizacionDTO toDTO (Organizacion org) {
        if (org == null) {
            return null;
        }
        OrganizacionDTO dto = new OrganizacionDTO();
        dto.setIdOrganizacion(org.getIdOrganizacion());
        dto.setNombre(org.getNombre());
        dto.setDescripcion(org.getDescripcion());
        dto.setFechaCreacion(org.getFechaCreacion());
        return dto;
    }

    // Metodo para convertir un OrganizacionCreateDTO --> Organizacion:
    public static Organizacion toEntity (OrganizacionCreateDTO dto) {
        Organizacion org = new Organizacion();
        org.setNombre(dto.getNombre());
        org.setDescripcion(dto.getDescripcion());
        return org;
    }

    // Metodo para actualizar parcialmente una organizacion:
    public static void updateEntityFromDTO (OrganizacionUpdateDTO dto, Organizacion organizacion) {

        if (dto.getNombre() != null) {
            organizacion.setNombre(dto.getNombre());
        }
        if (dto.getDescripcion() != null) {
            organizacion.setDescripcion(dto.getDescripcion());
        }
        
    }

    // Metodo para convertir una lista de Organizacion --> lista de OrganizacionDTO:
    public static List<OrganizacionDTO> toDTOList (List<Organizacion> organizaciones) {
        return organizaciones.stream().map(OrganizacionMapper::toDTO).toList();
    }

}
