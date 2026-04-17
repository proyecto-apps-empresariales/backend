package com.proyecto_backend.demoAPI.businessLayer.services;

import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.HistorialPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.HistorialPeticionFlujoEntity;

import java.util.List;

public interface IHistorialPeticionFlujoService {

    HistorialPeticionFlujoResponseDTO createHistorial(HistorialPeticionFlujoEntity entity);

    HistorialPeticionFlujoResponseDTO getHistorialById(Long id);

    List<HistorialPeticionFlujoResponseDTO> getHistorialCompletoByPeticionId(Long id);

    List<HistorialPeticionFlujoResponseDTO> getHistorialCompletoByPeticionNombre(String nombre);

}
