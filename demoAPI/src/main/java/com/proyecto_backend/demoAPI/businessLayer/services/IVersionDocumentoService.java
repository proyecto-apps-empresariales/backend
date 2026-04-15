package com.proyecto_backend.demoAPI.businessLayer.services;

import com.proyecto_backend.demoAPI.businessLayer.dtos.VersionDocumentoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.VersionDocumentoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.VersionDocumentoUpdateDTO;

import java.util.List;

public interface IVersionDocumentoService {

    public VersionDocumentoResponseDTO createVersion(VersionDocumentoCreateDTO dto);

    public VersionDocumentoResponseDTO updateVersion(Long id,VersionDocumentoUpdateDTO dto);

    public VersionDocumentoResponseDTO getVersionById(Long id);

    public List<VersionDocumentoResponseDTO> getVersionesByDocumento(Long idDocumento);

    public void deleteVersion(Long id);
}
