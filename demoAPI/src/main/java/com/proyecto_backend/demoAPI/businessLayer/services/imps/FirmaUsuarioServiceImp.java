package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import com.proyecto_backend.demoAPI.persistenceLayer.daos.FirmaUsuarioDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.UsuarioDAO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IFirmaUsuarioService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FirmaUsuarioServiceImp implements IFirmaUsuarioService {

    private final UsuarioDAO usuarioDAO;
    private final FirmaUsuarioDAO firmaUsuarioDAO;

    // Metodo para guardar una FirmaUsuario por medio de un FirmaUsuarioCreateDTO:
    @Override
    public FirmaUsuarioDTO guardarFirmaUsuario (FirmaUsuarioCreateDTO dto) {

        if (dto == null) { // 400 BAD_REQUEST: Datos No validos
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos no validos");
        }
        if (firmaUsuarioDAO.buscarFirmaPorArchivo(dto.getArchivoFirma()).isPresent()) { // 409 CONFLICT: Dato ya existente
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nombre ya registrado");
        }

        return firmaUsuarioDAO.guardarFirmaUsuario(dto);

    }

    // Metodo para obtener la lista de todas las firmas:
    @Override
    public List<FirmaUsuarioDTO> listaFirmas () {

        return firmaUsuarioDAO.listaFirmas();

    }

    // Metodo para obtener la lista de todas las firmas de un usuario:
    @Override
    public List<FirmaUsuarioDTO> listaFirmasPorUsuario (Long idUsuario) {

        if (idUsuario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }

        return firmaUsuarioDAO.listaFirmasPorUsuario(idUsuario);

    }

    // Metodo para obtener una firma por id:
    @Override
    public FirmaUsuarioDTO buscarFirmaPorIdFirma (Long idFirma) {

        if (idFirma == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }

        return firmaUsuarioDAO.buscarFirmaPorId(idFirma)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Firma no encontrada"));

    }

    // Metodo para obtener una firma por archivoFirma:
    @Override
    public FirmaUsuarioDTO buscarFirmaPorArchivoFirma (String archivoFirma) {

        if (archivoFirma == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Archivo no valido");
        }

        return firmaUsuarioDAO.buscarFirmaPorArchivo(archivoFirma)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Firma no encontrada"));

    }

    // Metodo para actualizar una firma:
    @Override
    public FirmaUsuarioDTO actualizarFirmaUsuario (FirmaUsuarioUpdateDTO dto, Long idFirma) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos no validos");
        }
        if (idFirma == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }

        Usuario usuario = buscarUsuarioId(dto.getIdUsuario());

        return firmaUsuarioDAO.actualizarFirma(idFirma, dto, usuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Firma no encontrada"));

    }

    // Metodo para eliminar una firma:
    @Override
    public void eliminarFirmaUsuario (Long idFirma) {

        if (idFirma == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }

        boolean eliminado = firmaUsuarioDAO.eliminarFirma(idFirma);

        if (eliminado == false) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Firma no encontrada");
        }

    }

    // Metodo privado para buscar un usuario por id y retornar la entidad (casos especiales):
    private Usuario buscarUsuarioId (Long idUsuario) {

        if (idUsuario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }

        return usuarioDAO.buscarUsuarioEntidadPorId(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

    }
}