package com.proyecto_backend.demoAPI.persistenceLayer.mappers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoDocumentoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoDocumentoResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.RequerimientoDocumentoEntity;

import java.util.List;

public class RequerimientoDocumentoMapper {

    //El constructor privado evita que se inyecte ya que es una clase utilitaria
    private RequerimientoDocumentoMapper(){}

    //Convertir RequerimientoDocumentoEntity a DTO - Enviar informacion a la capa de presentacion
    public static RequerimientoDocumentoResponseDTO toDTO(RequerimientoDocumentoEntity entity){
        if (entity == null) {
            return null;
        }

        RequerimientoDocumentoResponseDTO dto= new RequerimientoDocumentoResponseDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());

        return dto;
    }

    //Convertir DTO a Entity - Creacion/Actualizacion de tipos de documento
    public static RequerimientoDocumentoEntity toEntity(RequerimientoDocumentoCreateUpdateDTO dto){
        if (dto == null) return null;

        RequerimientoDocumentoEntity entity = new RequerimientoDocumentoEntity();

        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());

        return entity;
    }

    //Mappear dto a entidad existente para actualizarla ->
    // Recibe info nueva en dto y la entidad encontrada en el servicio para ser actualizado
    public static void updateEntityFromDTO(RequerimientoDocumentoCreateUpdateDTO dto, RequerimientoDocumentoEntity entity) {

        if (dto == null || entity == null) {
            return;
        }

        if (dto.getNombre() != null) entity.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) entity.setDescripcion(dto.getDescripcion());
    }

    //Convertir lista de entidades a listaDto
    public static List<RequerimientoDocumentoResponseDTO> toDTOList(List<RequerimientoDocumentoEntity> list) {

        if (list == null) return List.of();

        return list.stream().map(RequerimientoDocumentoMapper::toDTO).toList();
    }
}
