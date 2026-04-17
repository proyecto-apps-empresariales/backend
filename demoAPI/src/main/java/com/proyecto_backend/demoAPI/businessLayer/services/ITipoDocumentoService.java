package com.proyecto_backend.demoAPI.businessLayer.services;

import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoDocumentoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoDocumentoResponseDTO;

import java.util.List;

public interface ITipoDocumentoService {

    public TipoDocumentoResponseDTO createTipoDocumento(TipoDocumentoCreateUpdateDTO createDTO);

    public TipoDocumentoResponseDTO updateTipoDocumento(Long id, TipoDocumentoCreateUpdateDTO updateDTO);

    public TipoDocumentoResponseDTO getTipoDocumentoByID(Long id);

    public List<TipoDocumentoResponseDTO> getAllTipoDocumento();

    public void deleteTipoDocumento(Long id);
}
