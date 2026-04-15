package com.proyecto_backend.demoAPI.persistenceLayer.mappers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.*;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.DocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.VersionDocumentoEntity;

import java.util.List;

public class VersionDocumentoMapper {

    //El constructor privado evita que se inyecte ya que es una clase utilitaria
    private VersionDocumentoMapper(){}

    //Convertir VersionDocumentoEntity a DTO - Enviar informacion a la capa de presentacion
    public static VersionDocumentoResponseDTO toDTO(VersionDocumentoEntity entity){
        if (entity == null) {
            return null;
        }

        VersionDocumentoResponseDTO dto= new VersionDocumentoResponseDTO();
        dto.setId(entity.getId());
        dto.setUsuarioActualizador(entity.getUsuarioActualizador().getNombre());
        dto.setDocumento(entity.getDocumento().getNombre());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setFechaActualizacion(entity.getFechaActualizacion());
        dto.setArchivoUrl(entity.getArchivoUrl());

        return dto;
    }

    //Convertir DTO a Entity - Creacion/Actualizacion de version de documento
    public static VersionDocumentoEntity toEntity(VersionDocumentoCreateDTO dto){
        if (dto == null) return null;

        VersionDocumentoEntity entity = new VersionDocumentoEntity();
        entity.setDescripcion(dto.getDescripcion());
        return entity;
    }

    //Mappear dto a entidad existente para actualizarla ->
    // Recibe info nueva en dto y la entidad encontrada en el servicio para ser actualizado
    public static void updateEntityFromDTO(VersionDocumentoUpdateDTO dto, VersionDocumentoEntity entity) {

        if (dto == null || entity == null) {
            return;
        }

        if (dto.getDescripcion() != null) entity.setDescripcion(dto.getDescripcion());
    }

    //Convertir lista de entidades a listaDto
    public static List<VersionDocumentoResponseDTO> toDTOList(List<VersionDocumentoEntity> list) {

        if (list == null) return List.of();

        return list.stream().map(VersionDocumentoMapper::toDTO).toList();
    }
}
