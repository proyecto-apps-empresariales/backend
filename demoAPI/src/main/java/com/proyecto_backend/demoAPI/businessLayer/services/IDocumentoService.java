package com.proyecto_backend.demoAPI.businessLayer.services;

import com.proyecto_backend.demoAPI.businessLayer.dtos.DocumentoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.DocumentoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.DocumentoUpdateDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.DocumentoEntity;

import java.time.LocalDate;
import java.util.List;

public interface IDocumentoService {

    public DocumentoResponseDTO createDocumento(DocumentoCreateDTO createDTO);

    public DocumentoResponseDTO updateDocumento(Long id, DocumentoUpdateDTO updateDTO);

    public DocumentoResponseDTO getDocumentoByNombre(String nombre);

    public List<DocumentoResponseDTO> getAllDocumentoByUsuarioCreador(String usuario);

    public List<DocumentoResponseDTO> getAllDocumentoByTipoDocumento(String tipoDocumento);

    public List<DocumentoResponseDTO> getAllDocumentoByFechaCreacion(LocalDate inicio, LocalDate fin);

    public DocumentoResponseDTO getDocumentoById(Long id);

    public List<DocumentoResponseDTO> getAllDocumento();

    //Busca por ID y Resuelve la entidad
    public DocumentoEntity getDocumentoEntityById(Long idUsuario);
}
