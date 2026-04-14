package com.proyecto_backend.demoAPI.businessLayer.services;

import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoPeticionFlujoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoPeticionFlujoResponseDTO;

import java.util.List;

public interface ITipoPeticionFlujoService {

    public TipoPeticionFlujoResponseDTO createTipoPeticionFlujo(TipoPeticionFlujoCreateUpdateDTO createDTO);

    public TipoPeticionFlujoResponseDTO getTipoPeticionFlujoById(Long id);

    public TipoPeticionFlujoResponseDTO getTipoPeticionFlujoByNombre(String nombre);

    public List<TipoPeticionFlujoResponseDTO> getAlTipoPeticionFlujos();

    public TipoPeticionFlujoResponseDTO updateTipoPeticionFlujo(Long id, TipoPeticionFlujoCreateUpdateDTO updateDTO);

    public void deleteTipoPeticionFlujo(Long id);
}
