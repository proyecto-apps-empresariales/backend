package com.proyecto_backend.demoAPI.persistenceLayer.mappers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PlantillaDocumentoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PlantillaDocumentoResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.PlantillaDocumentoEntity;

import java.util.List;

public class PlantillaDocumentoMapper {

    //El constructor privado evita que se inyecte ya que es una clase utilitaria
    private PlantillaDocumentoMapper(){}

    //Convertir PlantillaDocumentoEntity a DTO - Enviar informacion a la capa de presentacion
    public static PlantillaDocumentoResponseDTO toDTO(PlantillaDocumentoEntity entity){
        if (entity == null) {
            return null;
        }

        PlantillaDocumentoResponseDTO dto= new PlantillaDocumentoResponseDTO();
        dto.setId(entity.getId());
        dto.setArchivoUrl(entity.getArchivoUrl());
        dto.setTipoDocumento(entity.getTipoDocumento().getNombre());
        dto.setDescripcion(entity.getDescripcion());

        return dto;
    }

    //Convertir DTO a Entity - Creacion/Actualizacion de tipos de documento
    public static PlantillaDocumentoEntity toEntity(PlantillaDocumentoCreateUpdateDTO dto){
        if (dto == null) return null;

        PlantillaDocumentoEntity entity = new PlantillaDocumentoEntity();

        entity.setArchivoUrl(dto.getArchivoUrl());
        entity.setDescripcion(dto.getDescripcion());

        return entity;
    }

    //Mappear dto a entidad existente para actualizarla ->
    // Recibe info nueva en dto y la entidad encontrada en el servicio para ser actualizado
    public static void updateEntityFromDTO(PlantillaDocumentoCreateUpdateDTO dto, PlantillaDocumentoEntity entity) {

        if (dto == null || entity == null) {
            return;
        }

        if (dto.getArchivoUrl() != null) entity.setArchivoUrl(dto.getArchivoUrl());
        if (dto.getDescripcion() != null) entity.setDescripcion(dto.getDescripcion());
    }

    //Convertir lista de entidades a listaDto
    public static List<PlantillaDocumentoResponseDTO> toDTOList(List<PlantillaDocumentoEntity> list) {

        if (list == null) return List.of();

        return list.stream().map(PlantillaDocumentoMapper::toDTO).toList();
    }
}
