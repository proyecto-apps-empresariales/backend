package com.proyecto_backend.demoAPI.businessLayer.services;

import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.EstadoPeticionFlujoResponseDTO;

import java.util.List;
import java.util.Optional;

public interface IEstadoPeticionFlujoService {

    public EstadoPeticionFlujoResponseDTO createEstado(EstadoPeticionFlujoCreateUpdateDTO createDTO);

    public EstadoPeticionFlujoResponseDTO getEstadoById(Long id);

    public EstadoPeticionFlujoResponseDTO getEstadoByNombre(String nombre);

    public List<EstadoPeticionFlujoResponseDTO> getAllEstados();

    public EstadoPeticionFlujoResponseDTO updateEstado(Long id, EstadoPeticionFlujoCreateUpdateDTO updateDTO);

    public void deleteEstado(Long id);


}
