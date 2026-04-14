package com.proyecto_backend.demoAPI.businessLayer.services;

import com.proyecto_backend.demoAPI.businessLayer.dtos.DocumentoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.DocumentoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.DocumentoUpdateDTO;

import java.util.List;

public interface IDocumentoService {

    public DocumentoResponseDTO createDocumento(DocumentoCreateDTO createDTO);

    public DocumentoResponseDTO updateDocumento(Long id, DocumentoUpdateDTO updateDTO);

    public DocumentoResponseDTO getDocumentoById(Long id);

    public List<DocumentoResponseDTO> getAllDocumento();
}
