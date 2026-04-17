package com.proyecto_backend.demoAPI.businessLayer.services;

import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaPeticionFlujoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoPeticionCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoPeticionResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.RequerimientoPeticionEntity;

import java.util.List;

public interface IFirmaPeticionFlujoService {

    FirmaPeticionFlujoResponseDTO createFirmaPeticion(FirmaPeticionFlujoCreateDTO createDTO);

    FirmaPeticionFlujoResponseDTO getFirmaPeticionById(Long id);

    List<FirmaPeticionFlujoResponseDTO> getAllFirmaPeticion();

    List<FirmaPeticionFlujoResponseDTO> getAllFirmaPeticionByUsuarioId(Long id);

    List<FirmaPeticionFlujoResponseDTO> getAllByPeticionId(Long id);

    void deleteFirmaPeticion(Long id);

}
