package com.proyecto_backend.demoAPI.persistenceLayer.mappers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaPeticionFlujoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.*;

import java.time.LocalDateTime;
import java.util.List;

public final class FirmaPeticionFlujoMapper {

    //El constructor privado evita que se inyecte ya que es una clase utilitaria
    private FirmaPeticionFlujoMapper() {
    }

    public static FirmaPeticionFlujoResponseDTO toDTO(FirmaPeticionFlujoEntity entity) {

        if (entity == null) {
            return null;
        }

        FirmaPeticionFlujoResponseDTO dto = new FirmaPeticionFlujoResponseDTO();

        dto.setId(entity.getId());
        dto.setPeticion(entity.getPeticion().getNombre());
        dto.setFechaFirma(entity.getFechaFirma());
        dto.setObservacion(entity.getObservacion());
        dto.setUsuarioFirmador(entity.getUsuario().getNombre());

        return dto;
    }

    public static FirmaPeticionFlujoEntity toEntity(
            FirmaPeticionFlujoCreateDTO dto,
            Usuario usuario,
            PeticionFlujoEntity peticion) {

        if (dto == null) return null;

        FirmaPeticionFlujoEntity entity = new FirmaPeticionFlujoEntity();

        entity.setUsuario(usuario);
        entity.setPeticion(peticion);

        entity.setObservacion(dto.getObservacion());

        return entity;
    }

    public static List<FirmaPeticionFlujoResponseDTO> toDTOList(List<FirmaPeticionFlujoEntity> list) {

        if (list == null) return List.of();

        return list.stream().map(FirmaPeticionFlujoMapper::toDTO).toList();

    }

}
