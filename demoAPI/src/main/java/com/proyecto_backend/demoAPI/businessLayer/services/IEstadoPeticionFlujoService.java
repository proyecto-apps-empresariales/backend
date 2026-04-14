package com.proyecto_backend.demoAPI.businessLayer.services;

import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.EstadoPeticionFlujoEntity;

import java.util.List;

public interface IEstadoPeticionFlujoService {

    public EstadoPeticionFlujoResponseDTO createEstado(EstadoPeticionFlujoCreateUpdateDTO createDTO);

    public EstadoPeticionFlujoResponseDTO getEstadoById(Long id);

    public EstadoPeticionFlujoResponseDTO getEstadoByNombre(String nombre);

    public List<EstadoPeticionFlujoResponseDTO> getAllEstados();

    public EstadoPeticionFlujoResponseDTO updateEstado(Long id, EstadoPeticionFlujoCreateUpdateDTO updateDTO);

    public void deleteEstado(Long id);

    public EstadoPeticionFlujoEntity getEstadoEntityById(Long id);

}
