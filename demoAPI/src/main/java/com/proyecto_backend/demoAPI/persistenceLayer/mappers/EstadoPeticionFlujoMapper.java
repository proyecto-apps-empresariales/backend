package com.proyecto_backend.demoAPI.persistenceLayer.mappers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.EstadoPeticionFlujoEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

public final class EstadoPeticionFlujoMapper {

    //El constructor privado evita que se inyecte ya que es una clase utilitaria
    private EstadoPeticionFlujoMapper() {
    }

    //Convertir EstadoPeticionEntity a DTO
    public static EstadoPeticionFlujoResponseDTO toDTO(EstadoPeticionFlujoEntity entity) {

        if (entity == null) {
            return null;
        }

        EstadoPeticionFlujoResponseDTO dto = new EstadoPeticionFlujoResponseDTO();

        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());

        return dto;
    }

    //Convertir EstadoPeticionDTO a entidad
    public static EstadoPeticionFlujoEntity toEntity(EstadoPeticionFlujoCreateUpdateDTO dto) {

        if (dto == null) return null;

        EstadoPeticionFlujoEntity entity = new EstadoPeticionFlujoEntity();

        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());

        return entity;
    }

    //Mappear dto a entidad existente para actualizarla ->
    // Recibe info nueva en dto y la entidad encontrada en el servicio para ser actualizado
    public static void updateEntityFromDTO(EstadoPeticionFlujoCreateUpdateDTO dto, EstadoPeticionFlujoEntity entity) {

        if (dto == null || entity == null) {
            return;
        }

        if (dto.getNombre() != null) entity.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) entity.setDescripcion(dto.getDescripcion());
    }

    //Convertir lista de entidades a listaDto
    public static List<EstadoPeticionFlujoResponseDTO> toDTOList(List<EstadoPeticionFlujoEntity> list) {

        if (list == null) return List.of();

        return list.stream().map(EstadoPeticionFlujoMapper::toDTO).toList();
    }

}
