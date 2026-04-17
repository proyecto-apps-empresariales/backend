package com.proyecto_backend.demoAPI.businessLayer.services;

import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoPeticionCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoPeticionResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.RequerimientoPeticionEntity;

import java.util.List;

public interface  IRequerimientoPeticionService {

    RequerimientoPeticionResponseDTO createRequerimiento(RequerimientoPeticionCreateUpdateDTO createDTO);

    RequerimientoPeticionResponseDTO getRequerimientoById(Long id);

    RequerimientoPeticionResponseDTO getRequerimientoByNombre(String nombre);

    List<RequerimientoPeticionResponseDTO> getAllRequerimientos();

    RequerimientoPeticionResponseDTO updateRequerimiento(Long id, RequerimientoPeticionCreateUpdateDTO updateDTO);

    void deleteRequerimiento(Long id);

    List<RequerimientoPeticionEntity> getAllByNombreIn(List<String> nombres);

}
