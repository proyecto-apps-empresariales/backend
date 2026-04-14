package com.proyecto_backend.demoAPI.businessLayer.services;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.imps.PeticionFlujoServiceImpl;

import java.util.List;

public interface IPeticionFlujoService {

    public PeticionFlujoResponseDTO createPeticion(PeticionFlujoCreateDTO createDTO);

    public PeticionFlujoResponseDTO getPeticionById(Long id);

    public PeticionFlujoResponseDTO getPeticionByNombre(String nombre);

    public List<PeticionFlujoResponseDTO> getAllPeticiones();

    public List<PeticionFlujoResponseDTO> getAllPeticionesByRemitenteId(Long id);

    public List<PeticionFlujoResponseDTO> getAllPeticionesByDestinatarioId(Long id);

    public PeticionFlujoResponseDTO updatePeticion(Long id, PeticionFlujoUpdateDTO updateDTO);

    public void deletePeticion(Long id);

}
