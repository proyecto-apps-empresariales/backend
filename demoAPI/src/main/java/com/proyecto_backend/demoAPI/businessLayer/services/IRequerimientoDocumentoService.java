package com.proyecto_backend.demoAPI.businessLayer.services;

import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoDocumentoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoDocumentoResponseDTO;

import java.util.List;

public interface IRequerimientoDocumentoService {
    RequerimientoDocumentoResponseDTO createRequerimiento(RequerimientoDocumentoCreateUpdateDTO dto);
    RequerimientoDocumentoResponseDTO updateRequerimiento(Long id, RequerimientoDocumentoCreateUpdateDTO dto);
    RequerimientoDocumentoResponseDTO getRequerimientoById(Long id);
    List<RequerimientoDocumentoResponseDTO> getAllRequerimientos();
}
