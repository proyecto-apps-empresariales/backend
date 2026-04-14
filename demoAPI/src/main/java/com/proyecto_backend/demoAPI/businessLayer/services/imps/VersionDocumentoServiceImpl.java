package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import com.proyecto_backend.demoAPI.businessLayer.dtos.VersionDocumentoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.VersionDocumentoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.VersionDocumentoUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IVersionDocumentoService;
import com.proyecto_backend.demoAPI.exceptions.BadRequestException;
import com.proyecto_backend.demoAPI.exceptions.ResourceNotFoundException;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.DocumentoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.UsuarioDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.VersionDocumentoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.DocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.VersionDocumentoEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VersionDocumentoServiceImpl implements IVersionDocumentoService {

    public final VersionDocumentoDAO versionDocumentoDAO;
    public final DocumentoDAO documentoDAO;
    public final UsuarioDAO usuarioDAO;

    @Override
    @Transactional
    public VersionDocumentoResponseDTO createVersion(VersionDocumentoCreateDTO dto) {

        //Buscar ENTIDAD documento por nombre
        DocumentoEntity documento = documentoDAO.findByNombreEntity(dto.getDocumento())
                .orElseThrow(() -> new ResourceNotFoundException("Documento no encontrado: " + dto.getDocumento()));

        //Buscar ENTIDAD usuario por correo
        Usuario usuarioActualizador = usuarioDAO.buscarUsuarioEntidadPorCorreo(dto.getUsuarioActualizador())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + dto.getUsuarioActualizador()));

        // Contar versiones existentes y generar nombre automático
        long totalVersiones = versionDocumentoDAO.countByDocumentoId(documento.getId());
        String nombreVersionAuto = documento.getNombre() + " - v" + (totalVersiones + 1);

        // Validar que ese nombre generado no exista
        if (versionDocumentoDAO.existsByNombreIgnoreCare(nombreVersionAuto)) {
            throw new BadRequestException("Error generando versión, nombre duplicado: " + nombreVersionAuto);
        }

        //Construir entidad
        //las relaciones y archivoUrl las asignamos aquí manualmente
        VersionDocumentoEntity entity = new VersionDocumentoEntity();
        entity.setDocumento(documento);
        entity.setUsuarioActualizador(usuarioActualizador);
        entity.setNombre(nombreVersionAuto);
        entity.setArchivoUrl(dto.getArchivoUrl());
        entity.setDescripcion(dto.getDescripcion());
        entity.setFechaActualizacion(dto.getFechaActualizacion());

        //Guardar y retornar
        return versionDocumentoDAO.saveEntity(entity);
    }

    //Obtener todas las versiones de un documento
    @Override
    @Transactional
    public List<VersionDocumentoResponseDTO> getVersionesByDocumento(Long idDocumento) {

        documentoDAO.findById(idDocumento)
                .orElseThrow(() -> new ResourceNotFoundException("Documento no encontrado con id: " + idDocumento));

        return versionDocumentoDAO.findByDocumentoId(idDocumento);
    }

    //Obtener version por ID
    @Override
    @Transactional
    public VersionDocumentoResponseDTO getVersionById(Long id) {

        return versionDocumentoDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Versión no encontrada con id: " + id));
    }

    //Actualizar nombre o descripcion de version
    @Override
    @Transactional
    public VersionDocumentoResponseDTO updateVersion(Long id, VersionDocumentoUpdateDTO dto) {

        //Validar campos
        if (dto.getDescripcion() == null || dto.getDescripcion().isBlank()) {
            throw new BadRequestException("La descripcion de la versión no puede estar vacío");
        }

        //Actualizar — el DAO busca por id, aplica updateEntityFromDTO y guarda
        return versionDocumentoDAO.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("Versión no encontrada con id: " + id));
    }
}
