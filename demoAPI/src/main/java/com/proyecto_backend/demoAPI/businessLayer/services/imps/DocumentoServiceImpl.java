package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import com.proyecto_backend.demoAPI.businessLayer.dtos.*;
import com.proyecto_backend.demoAPI.businessLayer.services.IDocumentoService;
import com.proyecto_backend.demoAPI.exceptions.BadRequestException;
import com.proyecto_backend.demoAPI.exceptions.ResourceNotFoundException;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.DocumentoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.TipoDocumentoDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.UsuarioDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.DocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoDocumentoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;
import com.proyecto_backend.demoAPI.persistenceLayer.mappers.DocumentoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentoServiceImpl implements IDocumentoService{

    private final DocumentoDAO documentoDAO;
    private final UsuarioDAO usuarioDAO;
    private final TipoDocumentoDAO tipoDocumentoDAO;

    @Override
    @Transactional
    public DocumentoResponseDTO createDocumento(DocumentoCreateDTO dto) {

        // Validar duplicado de nombre
        if (documentoDAO.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new BadRequestException("Ya existe un documento con el nombre: " + dto.getNombre());
        }

        // Buscar usuario por correo
        Usuario usuarioCreador = usuarioDAO.buscarUsuarioEntidadPorCorreo(dto.getUsuarioCreador())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con correo: " + dto.getUsuarioCreador()));

        // Buscar tipo de documento por nombre
        TipoDocumentoEntity tipoDocumento = tipoDocumentoDAO.findByNombreEntidad(dto.getTipoDocumento())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de documento no encontrado: " + dto.getTipoDocumento()));

        // Crear la entidad documento con la informacion recolectada
        DocumentoEntity documento = new DocumentoEntity();
        documento.setNombre(dto.getNombre());
        documento.setDescripcion(dto.getDescripcion());
        documento.setUsuarioCreador(usuarioCreador);
        documento.setTipoDocumento(tipoDocumento);

        //Guardar documento en la base de datos SIN ARCHIVO
        DocumentoResponseDTO documentoRespuesta = documentoDAO.saveEntity(documento);
        return documentoRespuesta;
    }

    //Obtener todos los documentos
    @Override
    public List<DocumentoResponseDTO> getAllDocumento(){
        return documentoDAO.findAll();
    }

    //Obtener documento por ID
    @Override
    public DocumentoResponseDTO getDocumentoById(Long id){
        return documentoDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Documento no encontrado con id: " + id));
    }

    //Obtener documento por nombre
    public DocumentoResponseDTO getDocumentoByNombre(String nombre){
        return documentoDAO.findByNombre(nombre)
                .orElseThrow(() -> new ResourceNotFoundException("El documento con nombre: "+ nombre+" no existe"));
    }

    //Obtener todos los documentos creados por un usuario
    public List<DocumentoResponseDTO> getAllDocumentoByUsuarioCreador(String usuario){
        Usuario usuarioCreador= usuarioDAO.buscarUsuarioEntidadPorCorreo(usuario)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con correo:"+usuario+ "no existe"));

        return documentoDAO.findByUsuarioCreador(usuarioCreador.getIdUsuario());
    }

    //Obtener todos los documentos segun tipo de documento
    public List<DocumentoResponseDTO> getAllDocumentoByTipoDocumento(String tipoDocumento){
        TipoDocumentoEntity tipoDocumentoEntidad= tipoDocumentoDAO.findByNombreEntidad(tipoDocumento)
                .orElseThrow(() -> new ResourceNotFoundException("El tipo de documento:"+tipoDocumento+ "no existe"));

        return documentoDAO.findByTipoDocumento(tipoDocumentoEntidad.getId());
    }

    @Override
    //Obtener todos los documentos segun fecha de creacion
    public List<DocumentoResponseDTO> getAllDocumentoByFechaCreacion(LocalDate inicio, LocalDate fin){
        return documentoDAO.findByFechaCreacion(inicio,fin);
    }

    //Editar documento
    @Override
    @Transactional
    public DocumentoResponseDTO updateDocumento(Long id, DocumentoUpdateDTO dto) {

        // Verificar que el documento existe
        if (documentoDAO.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Documento no encontrado con id: " + id);
        }
        //Validar que nombre y descripción no estén vacíos
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new BadRequestException("El nombre del documento no puede estar vacío");
        }
        if (dto.getDescripcion() == null || dto.getDescripcion().isBlank()) {
            throw new BadRequestException("La descripción del documento no puede estar vacía");
        }

        return documentoDAO.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("Documento no encontrado con id: " + id));
    }
}
