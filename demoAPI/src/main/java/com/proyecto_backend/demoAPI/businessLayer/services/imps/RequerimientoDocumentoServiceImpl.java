package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoDocumentoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IRequerimientoDocumentoService;
import com.proyecto_backend.demoAPI.exceptions.ResourceNotFoundException;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.RequerimientoDocumentoDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequerimientoDocumentoServiceImpl implements IRequerimientoDocumentoService {

    private final RequerimientoDocumentoDAO requerimientoDocumentoDAO;

    public RequerimientoDocumentoResponseDTO getRequerimientoById(Long id){
        return requerimientoDocumentoDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La plantilla no se encontro"));
    }

    public List<RequerimientoDocumentoResponseDTO> getAllRequerimientos(){
        return requerimientoDocumentoDAO.findAll();
    }
}
