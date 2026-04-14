package com.proyecto_backend.demoAPI.businessLayer.services;

import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.HistorialPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.HistorialPeticionFlujoEntity;

import java.util.List;

public interface IHistorialPeticionFlujoService {

    public HistorialPeticionFlujoResponseDTO createHistorial(HistorialPeticionFlujoEntity entity);

    public HistorialPeticionFlujoResponseDTO getHistorialById(Long id);

    public List<HistorialPeticionFlujoResponseDTO> getHistorialCompletoByPeticionId(Long id);

    public List<HistorialPeticionFlujoResponseDTO> getHistorialCompletoByPeticionNombre(String nombre);

}
