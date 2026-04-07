package com.proyecto_backend.demoAPI.persistenceLayer.mappers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.DocumentoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.DocumentoUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.DocumentoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoDocumentoResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.DocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoDocumentoEntity;

import java.util.List;

public class DocumentoMapper {

    //El constructor privado evita que se inyecte ya que es una clase utilitaria
    private DocumentoMapper(){}

    //Convertir DocumentoEntity a DTO - Enviar informacion a la capa de presentacion
    public static DocumentoResponseDTO toDTO(DocumentoEntity entity){
        if (entity == null) {
            return null;
        }

        DocumentoResponseDTO dto= new DocumentoResponseDTO();
        dto.setId(entity.getId());
        dto.setUsuarioCreador(entity.getUsuarioCreador().getNombre());
        dto.setTipoDocumento(entity.getTipoDocumento().getNombre());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setFechaCreacion(entity.getFechaCreacion());

        return dto;
    }

    //Convertir DTO a Entity - Creacion/Actualizacion de tipos de documento
    public static DocumentoEntity toEntity(DocumentoCreateDTO dto){
        if (dto == null) return null;

        DocumentoEntity entity = new DocumentoEntity();
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        return entity;
    }

    //Mappear dto a entidad existente para actualizarla ->
    // Recibe info nueva en dto y la entidad encontrada en el servicio para ser actualizado
    public static void updateEntityFromDTO(DocumentoUpdateDTO dto, DocumentoEntity entity) {

        if (dto == null || entity == null) {
            return;
        }

        if (dto.getNombre() != null) entity.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) entity.setDescripcion(dto.getDescripcion());
    }

    //Convertir lista de entidades a listaDto
    public static List<DocumentoResponseDTO> toDTOList(List<DocumentoEntity> list) {

        if (list == null) return List.of();

        return list.stream().map(DocumentoMapper::toDTO).toList();
    }
}
