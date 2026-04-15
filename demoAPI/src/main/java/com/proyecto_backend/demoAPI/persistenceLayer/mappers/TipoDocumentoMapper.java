package com.proyecto_backend.demoAPI.persistenceLayer.mappers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoDocumentoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoDocumentoResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.EstadoPeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.RequerimientoDocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.RequerimientoPeticionEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoDocumentoEntity;

import java.util.ArrayList;
import java.util.List;

public final class TipoDocumentoMapper {

    //El constructor privado evita que se inyecte ya que es una clase utilitaria
    private TipoDocumentoMapper(){}

    //Convertir TipoDocumentoEntity a DTO - Enviar informacion a la capa de presentacion
    public static TipoDocumentoResponseDTO toDTO(TipoDocumentoEntity entity){
        if (entity == null) {
            return null;
        }

        TipoDocumentoResponseDTO dto= new TipoDocumentoResponseDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        if(entity.getRequerimientos()!=null){
            dto.setRequerimientos(
                    entity.getRequerimientos()
                            .stream()
                            .map(RequerimientoDocumentoMapper::toDTO)
                            .toList()
            );
        }else {
            dto.setRequerimientos(new ArrayList<>());
        }

        return dto;
    }

    //Convertir DTO a Entity - Creacion/Actualizacion de tipos de documento
    public static TipoDocumentoEntity toEntity(TipoDocumentoCreateUpdateDTO dto){
        if (dto == null) return null;

        TipoDocumentoEntity entity = new TipoDocumentoEntity();

        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());

        return entity;
    }

    //Mappear dto a entidad existente para actualizarla ->
    // Recibe info nueva en dto y la entidad encontrada en el servicio para ser actualizado
    public static void updateEntityFromDTO(TipoDocumentoCreateUpdateDTO dto, TipoDocumentoEntity entity) {

        if (dto == null || entity == null) {
            return;
        }

        if (dto.getNombre() != null) entity.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) entity.setDescripcion(dto.getDescripcion());
    }

    //Convertir lista de entidades a listaDto
    public static List<TipoDocumentoResponseDTO> toDTOList(List<TipoDocumentoEntity> list) {
        if (list == null) return new ArrayList<>();
        return new ArrayList<>(list.stream().map(TipoDocumentoMapper::toDTO).toList());
    }
}
