package com.proyecto_backend.demoAPI.persistencia.mappers;

import java.util.List;

import com.proyecto_backend.demoAPI.persistencia.entidades.FirmaUsuario;
import com.proyecto_backend.demoAPI.servicios.dtos.FirmaUsuarioCreateDTO;
import com.proyecto_backend.demoAPI.servicios.dtos.FirmaUsuarioDTO;

public class FirmaUsuarioMapper {
    
    // Metodo para convertir un FirmaUsuario --> FirmaUsuarioDTO:
    public static FirmaUsuarioDTO toDTO (FirmaUsuario firma) {

        if (firma == null) {
            return null;
        }

        Long idUsuario = firma.getUsuario() == null ? null : firma.getUsuario().getIdUsuario();

        FirmaUsuarioDTO dto = new FirmaUsuarioDTO();

        dto.setIdFirma(firma.getIdFirma());
        dto.setArchivoFirma(firma.getArchivoFirma());
        dto.setFecha(firma.getFecha());
        dto.setDescripcion(firma.getDescripcion());
        dto.setIdUsuario(idUsuario);

        return dto;
    }

    // Metodo para convertir un FirmaUsuarioCreate --> FirmaUsuario:
    public static FirmaUsuario toEntity (FirmaUsuarioCreateDTO dto) {

        FirmaUsuario firma = new FirmaUsuario();

        firma.setArchivoFirma(dto.getArchivoFirma());
        firma.setDescripcion(dto.getDescripcion());

        return firma;
    }

    // Metodo para convertir una lista de FirmaUsuario --> FirmaUsuarioDTO:
    public static List<FirmaUsuarioDTO> toDTOList (List<FirmaUsuario> firmas) {
        return firmas.stream().map(FirmaUsuarioMapper::toDTO).toList();
    }
}
