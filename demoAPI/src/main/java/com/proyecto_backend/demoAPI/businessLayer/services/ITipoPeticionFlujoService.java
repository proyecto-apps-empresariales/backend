package com.proyecto_backend.demoAPI.businessLayer.services;

import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoPeticionFlujoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoPeticionFlujoEntity;

import java.util.List;

public interface ITipoPeticionFlujoService {

    TipoPeticionFlujoResponseDTO createTipoPeticionFlujo(TipoPeticionFlujoCreateUpdateDTO createDTO);

    TipoPeticionFlujoResponseDTO getTipoPeticionFlujoById(Long id);

    TipoPeticionFlujoResponseDTO getTipoPeticionFlujoByNombre(String nombre);

    List<TipoPeticionFlujoResponseDTO> getAllTipoPeticionFlujos();

    TipoPeticionFlujoResponseDTO updateTipoPeticionFlujo(Long id, TipoPeticionFlujoCreateUpdateDTO updateDTO);

    void deleteTipoPeticionFlujo(Long id);

    TipoPeticionFlujoEntity getTipoPeticionEntityById(Long id);

}
