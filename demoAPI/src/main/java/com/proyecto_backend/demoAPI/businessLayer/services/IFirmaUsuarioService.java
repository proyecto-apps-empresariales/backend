package com.proyecto_backend.demoAPI.businessLayer.services;

import java.util.List;

import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioUpdateDTO;

public interface IFirmaUsuarioService {

    // Metodo para guardar una FirmaUsuario por medio de un FirmaUsuarioCreateDTO:
    FirmaUsuarioDTO guardarFirmaUsuario(FirmaUsuarioCreateDTO dto);

    // Metodo para obtener la lista de todas las firmas:
    List<FirmaUsuarioDTO> listaFirmas();

    // Metodo para obtener la lista de todas las firmas de un usuario:
    List<FirmaUsuarioDTO> listaFirmasPorUsuario(Long idUsuario);

    // Metodo para obtener una firma por id:
    FirmaUsuarioDTO buscarFirmaPorIdFirma(Long idFirma);

    // Metodo para obtener una firma por archivoFirma:
    FirmaUsuarioDTO buscarFirmaPorArchivoFirma(String archivoFirma);

    // Metodo para actualizar una firma:
    FirmaUsuarioDTO actualizarFirmaUsuario(FirmaUsuarioUpdateDTO dto, Long idFirma);

    // Metodo para eliminar una firma:
    void eliminarFirmaUsuario(Long idFirma); 
    
}
