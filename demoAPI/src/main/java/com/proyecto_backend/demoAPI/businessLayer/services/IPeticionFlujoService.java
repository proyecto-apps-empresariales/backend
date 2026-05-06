package com.proyecto_backend.demoAPI.businessLayer.services;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PeticionFlujoUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.imps.PeticionFlujoServiceImpl;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.PeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;

import java.util.List;

public interface IPeticionFlujoService {

    PeticionFlujoResponseDTO createPeticion(PeticionFlujoCreateDTO createDTO);

    PeticionFlujoResponseDTO getPeticionById(Long id);

    PeticionFlujoResponseDTO getPeticionByNombre(String nombre);

    List<PeticionFlujoResponseDTO> getAllPeticiones();

    List<PeticionFlujoResponseDTO> getAllPeticionesByRemitenteId(Long id);

    List<PeticionFlujoResponseDTO> getAllPeticionesByDestinatarioId(Long id);

    PeticionFlujoResponseDTO updatePeticion(Long id, PeticionFlujoUpdateDTO updateDTO);

    void deletePeticion(Long id);

    PeticionFlujoResponseDTO enviarRevision(Long id);

    PeticionFlujoResponseDTO aprobarPeticion(Long id);

    PeticionFlujoResponseDTO rechazarPeticion(Long id);

    PeticionFlujoResponseDTO firmarPeticion(Long id);

    PeticionFlujoResponseDTO finalizarPeticion(Long id);

    public PeticionFlujoEntity getPeticionEntityById(Long id);

}
