package com.proyecto_backend.demoAPI.businessLayer.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioUpdateDTO;
import com.proyecto_backend.demoAPI.persistanceLayer.mappers.FirmaUsuarioMapper;
import com.proyecto_backend.demoAPI.persistanceLayer.entidades.Usuario;
import com.proyecto_backend.demoAPI.persistanceLayer.entidades.FirmaUsuario;
import com.proyecto_backend.demoAPI.persistanceLayer.repositorios.FirmaUsuarioRepository;
import com.proyecto_backend.demoAPI.persistanceLayer.repositorios.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FirmaUsuarioService {

    // Creamos las instancias de los repositorios correspondientes:
    private final FirmaUsuarioRepository firmaUsuarioRepository;
    private final UsuarioRepository usuarioRepository;

    // Metodo para guardar una FirmaUsuario por medio de un FirmaUsuarioCreateDTO:
    @Transactional
    public FirmaUsuarioDTO guardarFirmaUsuario(FirmaUsuarioCreateDTO dto) {

        if (dto == null) { // 400 BAD_REQUEST: Datos No validos
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos no validos");
        }
        if (firmaUsuarioRepository.findByArchivoFirma(dto.getArchivoFirma()).isPresent()) { // 409 CONFLICT: Dato ya
                                                                                            // existente
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Archivo firma ya existe");
        }

        Usuario usuario = buscarUsuarioId(dto.getIdUsuario());

        FirmaUsuario firma = FirmaUsuarioMapper.toEntity(dto);
        firma.setFecha(LocalDate.now());
        firma.setUsuario(usuario);

        return FirmaUsuarioMapper.toDTO(firmaUsuarioRepository.save(firma));

    }

    // Metodo para obtener la lista de todas las firmas:
    public List<FirmaUsuarioDTO> listaFirmas() {

        return FirmaUsuarioMapper.toDTOList(firmaUsuarioRepository.findAll());
    }

    // Metodo para obtener la lista de todas las firmas de un usuario:
    public List<FirmaUsuarioDTO> listaFirmasPorUsuario(Long idUsuario) {

        buscarUsuarioId(idUsuario);

        return FirmaUsuarioMapper.toDTOList(firmaUsuarioRepository.findByUsuario_IdUsuario(idUsuario));
    }

    // Metodo para obtener una firma por id:
    public FirmaUsuarioDTO buscarFirmaPorIdFirma (Long idFirma) {

        return FirmaUsuarioMapper.toDTO(buscarFirmaId(idFirma));
    }

    // Metodo para obtener una firma por archivoFirma:
    public FirmaUsuarioDTO buscarFirmaPorArchivoFirma(String archivoFirma) {

        return FirmaUsuarioMapper.toDTO(buscarFirmaArchivoFirma(archivoFirma));
    }

    // Metodo para actualizar una firma:
    @Transactional
    public FirmaUsuarioDTO actualizarFirmaUsuario (FirmaUsuarioUpdateDTO dto, Long idFirma) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos no validos");
        }

        FirmaUsuario firma = buscarFirmaId(idFirma);

        if (dto.getArchivoFirma() != null) {
            firma.setArchivoFirma(dto.getArchivoFirma());   
        }
        if (dto.getDescripcion() != null) {
            firma.setDescripcion(dto.getDescripcion());   
        }
        if (dto.getIdUsuario() != null) {
            firma.setUsuario(buscarUsuarioId(dto.getIdUsuario()));   
        }

        return FirmaUsuarioMapper.toDTO(firmaUsuarioRepository.save(firma));
    }

    // Metodo para eliminar una firma:
    @Transactional
    public void eliminarFirmaUsuario (Long idFirma) {

        firmaUsuarioRepository.delete(buscarFirmaId(idFirma));
    }


    // Metodo privado para buscar una firma por id:
    private FirmaUsuario buscarFirmaId (Long idFirma) {

        if (idFirma == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id no valido");
        }
        return firmaUsuarioRepository.findById(idFirma)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FirmaUsuario no encontrado"));
    }

    // Metodo privado para buscar un usuario por id:
    private Usuario buscarUsuarioId (Long idUsuario) {

        if (idUsuario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id de usuario no valido");
        }
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    // Metodo privado para buscar una firma por archivoFirma:
    private FirmaUsuario buscarFirmaArchivoFirma (String archivoFirma) {

        if (archivoFirma == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos no validos");
        }
        return firmaUsuarioRepository.findByArchivoFirma(archivoFirma)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FirmaUsuario no encontrado"));
    }

}
