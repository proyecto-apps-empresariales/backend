package com.proyecto_backend.demoAPI.businessLayer.services;

import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.EstadoPeticionFlujoEntity;

import java.util.List;

public interface IEstadoPeticionFlujoService {

    EstadoPeticionFlujoResponseDTO createEstado(EstadoPeticionFlujoCreateUpdateDTO createDTO);

    EstadoPeticionFlujoResponseDTO getEstadoById(Long id);

    EstadoPeticionFlujoResponseDTO getEstadoByNombre(String nombre);

    List<EstadoPeticionFlujoResponseDTO> getAllEstados();

    EstadoPeticionFlujoResponseDTO updateEstado(Long id, EstadoPeticionFlujoCreateUpdateDTO updateDTO);

    void deleteEstado(Long id);

    EstadoPeticionFlujoEntity getEstadoEntityById(Long id);

}
