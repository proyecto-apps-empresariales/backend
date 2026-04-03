package com.proyecto_backend.demoAPI.persistanceLayer.mappers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoPeticionFlujoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.persistanceLayer.entidades.RequerimientoPeticionEntity;
import com.proyecto_backend.demoAPI.persistanceLayer.entidades.TipoPeticionFlujoEntity;

import java.util.List;

public class TipoPeticionFlujoMapper {

    //Convertir de entidad a responseDTO
    public static TipoPeticionFlujoResponseDTO toDTO (TipoPeticionFlujoEntity entity) {

        if (entity == null) return null;

        TipoPeticionFlujoResponseDTO dto = new TipoPeticionFlujoResponseDTO();

        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setInstruccionesPdf(entity.getInstruccionesPdf());
        dto.setRequerimientos(
                entity.getRequerimientos()
                        .stream()
                        .map(RequerimientoPeticionEntity::getNombre)
                        .toList()
        );

        return dto;
    }

    public static TipoPeticionFlujoEntity toEntity(TipoPeticionFlujoCreateUpdateDTO dto, List<RequerimientoPeticionEntity> list) {

        if (dto == null) return null;

        TipoPeticionFlujoEntity entity = new TipoPeticionFlujoEntity();

        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setInstruccionesPdf(dto.getInstruccionesPdf());
        entity.setRequerimientos(list);

        return entity;
    }

    public static void updateEntityFromDTO (TipoPeticionFlujoCreateUpdateDTO dto, List<RequerimientoPeticionEntity> list, TipoPeticionFlujoEntity entity) {

        if (dto == null || entity == null) return;

        if (dto.getNombre() != null) entity.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) entity.setDescripcion(dto.getDescripcion());
        if (dto.getInstruccionesPdf() != null) entity.setInstruccionesPdf(dto.getInstruccionesPdf());

        if (list  != null) entity.setRequerimientos(list);

    }

    public static List<TipoPeticionFlujoResponseDTO> toDTOList(List<TipoPeticionFlujoEntity> list) {

        if (list == null) return List.of();

        return list.stream().map(TipoPeticionFlujoMapper::toDTO).toList();

    }

}
