package com.proyecto_backend.demoAPI.businessLayer.services;

import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoPeticionCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoPeticionResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.RequerimientoPeticionEntity;

import java.util.List;

public interface  IRequerimientoPeticionService {

    public RequerimientoPeticionResponseDTO createRequerimiento(RequerimientoPeticionCreateUpdateDTO createDTO);

    public RequerimientoPeticionResponseDTO getRequerimientoById(Long id);

    public RequerimientoPeticionResponseDTO getRequerimientoByNombre(String nombre);

    public List<RequerimientoPeticionResponseDTO> getAllRequerimientos();

    public RequerimientoPeticionResponseDTO updateRequerimiento(Long id, RequerimientoPeticionCreateUpdateDTO updateDTO);

    public void deleteRequerimiento(Long id);

    public List<RequerimientoPeticionEntity> getAllByNombreIn(List<String> nombres);


}
