package com.proyecto_backend.demoAPI.businessLayer.services;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PlantillaDocumentoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PlantillaDocumentoResponseDTO;

import java.util.List;

public interface IPlantillaDocumentoService {
    public PlantillaDocumentoResponseDTO createPlantilla(PlantillaDocumentoCreateUpdateDTO dto);

    public PlantillaDocumentoResponseDTO updatePlantilla(Long id, PlantillaDocumentoCreateUpdateDTO dto);

    public PlantillaDocumentoResponseDTO getPlantillaById(Long id);

    public List<PlantillaDocumentoResponseDTO> getAllPlantillas();
}
