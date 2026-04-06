package com.proyecto_backend.demoAPI.persistenceLayer.mappers;

import java.util.List;

import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioUpdateDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.FirmaUsuario;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;

public class FirmaUsuarioMapper {
    
    // Metodo para convertir un FirmaUsuario --> FirmaUsuarioDTO:
    public static FirmaUsuarioDTO toDTO (FirmaUsuario firma) {

        if (firma == null) {
            return null;
        }

        Long idUsuario = firma.getUsuario() == null ? null : firma.getUsuario().getIdUsuario();
        String nombreUsuario = firma.getUsuario() == null ? null : firma.getUsuario().getNombre();
        String correoUsuario = firma.getUsuario() == null ? null : firma.getUsuario().getCorreo();

        FirmaUsuarioDTO dto = new FirmaUsuarioDTO();

        dto.setIdFirma(firma.getIdFirma());
        dto.setArchivoFirma(firma.getArchivoFirma());
        dto.setFecha(firma.getFecha());
        dto.setDescripcion(firma.getDescripcion());
        dto.setIdUsuario(idUsuario);
        dto.setNombreUsuario(nombreUsuario);
        dto.setCorreoUsuario(correoUsuario);

        return dto;
    }

    // Metodo para convertir un FirmaUsuarioCreate --> FirmaUsuario:
    public static FirmaUsuario toEntity (FirmaUsuarioCreateDTO dto) {

        FirmaUsuario firma = new FirmaUsuario();

        firma.setArchivoFirma(dto.getArchivoFirma());
        firma.setDescripcion(dto.getDescripcion());

        return firma;
    }

    // Metodo para actualizar parcialmente una firmaUsuario: 
    public static void updateEntityFromDTO (FirmaUsuarioUpdateDTO dto, FirmaUsuario firma, Usuario usuario) {

        if (dto.getArchivoFirma() != null) {
            firma.setArchivoFirma(dto.getArchivoFirma());
        }
        if (dto.getDescripcion() != null) {
            firma.setDescripcion(dto.getDescripcion());
        }
        if (usuario != null) {
            firma.setUsuario(usuario);
        }

    }

    // Metodo para convertir una lista de FirmaUsuario --> FirmaUsuarioDTO:
    public static List<FirmaUsuarioDTO> toDTOList (List<FirmaUsuario> firmas) {
        return firmas.stream().map(FirmaUsuarioMapper::toDTO).toList();
    }
}
