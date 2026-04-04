package com.proyecto_backend.demoAPI.persistenceLayer.mappers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoUpdateDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.EstadoPeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.PeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoPeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;

import java.time.LocalDate;
import java.util.List;

public class PeticionFlujoMapper {

    //Convertir la entidad a responseDTO
    //NO se validan nulls ya que, en caso de ser nulo, el mapper debe explotar para debugear
    public static PeticionFlujoResponseDTO toDTO(PeticionFlujoEntity entity) {

        if (entity == null) {
            return null;
        }

        PeticionFlujoResponseDTO dto = new PeticionFlujoResponseDTO();

        dto.setId(entity.getId());
        dto.setRemitente(entity.getRemitente().getNombre() + " " + entity.getRemitente().getApellido());
        dto.setDestinatario(entity.getDestinatario().getNombre() + " " + entity.getDestinatario().getApellido());
        dto.setTipoPeticion(entity.getTipoPeticion().getNombre());
        dto.setEstado(entity.getEstado().getNombre());
        dto.setFechaInicio(entity.getFechaInicio());
        dto.setFechaFin(entity.getFechaFin());
        dto.setDescripcion(entity.getDescripcion());
        dto.setNombre(entity.getNombre());

        return dto;
    }

    public static PeticionFlujoEntity toEntity(
            PeticionFlujoCreateDTO dto,
            Usuario remitente,
            Usuario destinatario,
            TipoPeticionFlujoEntity tipoPeticion,
            EstadoPeticionFlujoEntity estado,
            LocalDate fechaFin) {

        if (dto == null) return null;

        PeticionFlujoEntity entity = new PeticionFlujoEntity();

        entity.setRemitente(remitente);
        entity.setDestinatario(destinatario);
        entity.setTipoPeticion(tipoPeticion);
        entity.setEstado(estado);
        entity.setFechaFin(fechaFin);

        entity.setDescripcion(dto.getDescripcion());
        entity.setNombre(dto.getNombre());

        return entity;
    }

    //Actualizar la Entidad a partir del DTO
    //El servicio debe validar la existencia de los id enviados en el DTO
    //Los debe buscar y enviar las entidades a este metodo
    //Se valida el nulo para que, en caso de que algo venga null, no sobreescriba el atributo en la entidad
    //Solo en update se validan nulls ya que puede ser un patch
    public static void updateEntityFromDto(PeticionFlujoUpdateDTO dto,
                                           Usuario destinatario, TipoPeticionFlujoEntity tipoPeticion, EstadoPeticionFlujoEntity estado,
                                           LocalDate fechaFin,
                                           PeticionFlujoEntity entity) {

        if (dto == null || entity == null) return;

        if (destinatario != null) entity.setDestinatario(destinatario);
        if (tipoPeticion != null) entity.setTipoPeticion(tipoPeticion);
        if (estado != null) entity.setEstado(estado);
        if (fechaFin != null) entity.setFechaFin(fechaFin);

        if (dto.getDescripcion() != null) entity.setDescripcion(dto.getDescripcion());
        if (dto.getNombre() != null) entity.setNombre(dto.getNombre());

    }

    public static List<PeticionFlujoResponseDTO> toDTOList(List<PeticionFlujoEntity> list) {

        if (list == null) return List.of();

        return list.stream().map(PeticionFlujoMapper::toDTO).toList();

    }

}





