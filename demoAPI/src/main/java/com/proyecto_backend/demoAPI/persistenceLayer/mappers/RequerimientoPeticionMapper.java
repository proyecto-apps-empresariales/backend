package com.proyecto_backend.demoAPI.persistenceLayer.mappers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoPeticionCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoPeticionResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.RequerimientoPeticionEntity;

import java.util.List;

public class RequerimientoPeticionMapper {

    //Convertir entity a responseDTO
    public static RequerimientoPeticionResponseDTO toDTO(RequerimientoPeticionEntity entity) {

        if (entity == null) return null;

        RequerimientoPeticionResponseDTO dto = new RequerimientoPeticionResponseDTO();

        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());

        return dto;
    }

    public static RequerimientoPeticionEntity toEntity(RequerimientoPeticionCreateUpdateDTO dto) {

        if (dto == null) return null;

        RequerimientoPeticionEntity entity = new RequerimientoPeticionEntity();

        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());

        return entity;
    }

    public static void updateEntotyFromDTO(RequerimientoPeticionCreateUpdateDTO dto, RequerimientoPeticionEntity entity) {

        if (dto == null || entity == null) return;

        if (dto.getNombre() != null) entity.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) entity.setDescripcion(dto.getDescripcion());

    }

    public static List<RequerimientoPeticionResponseDTO> toDTOList(List<RequerimientoPeticionEntity> list) {

        if (list == null) return List.of();

        return list.stream().map(RequerimientoPeticionMapper::toDTO).toList();
    }

}
