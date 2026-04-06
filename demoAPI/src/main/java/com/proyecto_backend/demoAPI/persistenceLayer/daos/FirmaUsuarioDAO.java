package com.proyecto_backend.demoAPI.persistenceLayer.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioUpdateDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.FirmaUsuario;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;
import com.proyecto_backend.demoAPI.persistenceLayer.mappers.FirmaUsuarioMapper;
import com.proyecto_backend.demoAPI.persistenceLayer.repositories.IFirmaUsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FirmaUsuarioDAO {
    
    // Inyecta el repositorio de FirmaUsuario:
    private final IFirmaUsuarioRepository firmaUsuarioRepository;

    // Metodo para guardar una firma de usuario:
    public FirmaUsuarioDTO guardarFirmaUsuario (FirmaUsuarioCreateDTO dto) {

        FirmaUsuario firmaUsuario = FirmaUsuarioMapper.toEntity(dto);

        return FirmaUsuarioMapper.toDTO(firmaUsuarioRepository.save(firmaUsuario));

    }

    // Metodo para obtener la lista de todas las firmas:
    public List<FirmaUsuarioDTO> listaFirmas () {

        return FirmaUsuarioMapper.toDTOList(firmaUsuarioRepository.findAll());

    }

    // Metodo para obtener la lista de todas las firmas de un usuario:
    public List<FirmaUsuarioDTO> listaFirmasPorUsuario (Long idUsuario) {

        return FirmaUsuarioMapper.toDTOList(firmaUsuarioRepository.findByUsuario_IdUsuario(idUsuario));

    }

    // Metodo para obtener una firma por id:
    public Optional<FirmaUsuarioDTO> buscarFirmaPorId (Long idFirma) {

        return firmaUsuarioRepository.findById(idFirma).map(FirmaUsuarioMapper::toDTO);

    }

    // Metodo para buscar una firma por id y retornar la entidad (casos especiales):
    public Optional<FirmaUsuario> buscarFirmaEntityPorId (Long idFirma) {

        return firmaUsuarioRepository.findById(idFirma);

    }

    // Metodo para obtener una firma por archivoFirma:
    public Optional<FirmaUsuarioDTO> buscarFirmaPorArchivo (String archivoFirma) {

        return firmaUsuarioRepository.findByArchivoFirma(archivoFirma).map(FirmaUsuarioMapper::toDTO);

    }

    // Metodo para actualizar una firma:
    public Optional<FirmaUsuarioDTO> actualizarFirma (Long idFirma, FirmaUsuarioUpdateDTO dto, Usuario usuario) {

        return firmaUsuarioRepository.findById(idFirma).map(firma -> {
            FirmaUsuarioMapper.updateEntityFromDTO(dto, firma, usuario);
            return FirmaUsuarioMapper.toDTO(firmaUsuarioRepository.save(firma));
        });

    }

    // Metodo para eliminar una firma:
    public boolean eliminarFirma (Long idFirma) {

        if (firmaUsuarioRepository.existsById(idFirma)) {
            firmaUsuarioRepository.deleteById(idFirma);
            return true;
        }
        return false;

    }


}
