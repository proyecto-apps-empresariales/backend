package com.proyecto_backend.demoAPI.persistencia.mappers;

import java.util.List;

import com.proyecto_backend.demoAPI.persistencia.entidades.Organizacion;
import com.proyecto_backend.demoAPI.servicios.dtos.OrganizacionCreateDTO;
import com.proyecto_backend.demoAPI.servicios.dtos.OrganizacionDTO;

public class OrganizacionMapper {

    // Metodo para convertir un Organizacion --> OrganizacionDTO:
    public static OrganizacionDTO toDTO(Organizacion org) {
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

    // Metodo para convertir una lista de Organizacion --> lista de OrganizacionDTO:
    public static List<OrganizacionDTO> toDTOList (List<Organizacion> organizaciones) {
        return organizaciones.stream().map(OrganizacionMapper::toDTO).toList();
    }

}
