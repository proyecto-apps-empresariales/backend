package com.proyecto_backend.demoAPI.persistenceLayer.mappers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.HistorialPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.HistorialPeticionFlujoEntity;

import java.util.List;

public class HistorialPeticionFlujoMapper {

    //Convierte la entidad a DTO -> Respuesta
    public static HistorialPeticionFlujoResponseDTO toDTO(HistorialPeticionFlujoEntity entity) {
        if (entity == null) return null;

        HistorialPeticionFlujoResponseDTO dto = new HistorialPeticionFlujoResponseDTO();

        dto.setId(entity.getId());
        dto.setPeticion(entity.getPeticion().getId());
        dto.setUsuarioEditor(entity.getUsuarioEditor().getNombre());
        dto.setFecha(entity.getFecha());
        dto.setDescripcion(entity.getDescripcion());

        return dto;
    }

    //Convierte la lista de HistorialEntity a ReponseEntity
    public static List<HistorialPeticionFlujoResponseDTO> toDTOList(List<HistorialPeticionFlujoEntity> list) {
        if (list == null) return List.of();

        return list.stream().map(HistorialPeticionFlujoMapper::toDTO).toList();
    }
}
