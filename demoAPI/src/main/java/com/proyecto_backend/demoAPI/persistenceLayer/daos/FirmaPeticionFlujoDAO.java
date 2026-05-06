package com.proyecto_backend.demoAPI.persistenceLayer.daos;

import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaPeticionFlujoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.FirmaPeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.PeticionFlujoEntity;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Usuario;
import com.proyecto_backend.demoAPI.persistenceLayer.mappers.FirmaPeticionFlujoMapper;
import com.proyecto_backend.demoAPI.persistenceLayer.repositories.IFirmaPeticionFlujoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FirmaPeticionFlujoDAO {

    private final IFirmaPeticionFlujoRepository firmaPeticionRepository;

    //Crear Firma de petición
    public FirmaPeticionFlujoResponseDTO save(FirmaPeticionFlujoCreateDTO createDto,
                                              Usuario usuario,
                                              PeticionFlujoEntity peticion) {

        FirmaPeticionFlujoEntity entity = FirmaPeticionFlujoMapper.toEntity(createDto, usuario, peticion);
        FirmaPeticionFlujoEntity savedEntity= firmaPeticionRepository.save(entity);

        return FirmaPeticionFlujoMapper.toDTO(savedEntity);
    }

    //Buscar una firma de petición por id
    public Optional<FirmaPeticionFlujoResponseDTO> findById(Long id) {

        return firmaPeticionRepository.findById(id)
                .map(FirmaPeticionFlujoMapper::toDTO);
    }

    //Resuelve la entidad
    public Optional<FirmaPeticionFlujoEntity> findPeticionEntityById (Long id) {

        return firmaPeticionRepository.findById(id);
    }

    //Buscar todas las firmas de peticiones
    public List<FirmaPeticionFlujoResponseDTO> findAll() {

        List<FirmaPeticionFlujoEntity> entities = firmaPeticionRepository.findAll();
        return FirmaPeticionFlujoMapper.toDTOList(entities);
    }

    //Buscar firmas de petición ID de usuario
    public List<FirmaPeticionFlujoResponseDTO> findByUsuarioId(Long id) {

        List<FirmaPeticionFlujoEntity> entities = firmaPeticionRepository.findAllByUsuarioIdUsuario(id);
        return FirmaPeticionFlujoMapper.toDTOList(entities);
    }

    //Buscar firmas de petición ID de petición
    public List<FirmaPeticionFlujoResponseDTO> findByPeticionId(Long id) {

        List<FirmaPeticionFlujoEntity> entities = firmaPeticionRepository.findAllByPeticionId(id);
        return FirmaPeticionFlujoMapper.toDTOList(entities);
    }

    //Eliminar una firma de una petición
    public boolean delete(Long id) {

        if (firmaPeticionRepository.existsById(id)) {
            firmaPeticionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //Verificar duplicidad de firma
    public boolean existsByUsuarioIdAndPeticionId(Long usuarioId, Long peticionId) {

        return firmaPeticionRepository.existsByUsuarioIdUsuarioAndPeticionId(usuarioId, peticionId);
    }

}
