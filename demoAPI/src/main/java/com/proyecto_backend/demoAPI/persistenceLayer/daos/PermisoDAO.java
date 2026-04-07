package com.proyecto_backend.demoAPI.persistenceLayer.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PermisoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PermisoDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PermisoUpdateDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Permiso;
import com.proyecto_backend.demoAPI.persistenceLayer.mappers.PermisoMapper;
import com.proyecto_backend.demoAPI.persistenceLayer.repositories.IPermisoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@Transactional
@RequiredArgsConstructor
public class PermisoDAO {
    
    // Inyectamos el repositorio PermisoRepository:
    private final IPermisoRepository permisoRepository;

    // Metodo para guardar un permiso:
    public PermisoDTO guardarPermiso (PermisoCreateDTO dto) {
        
        Permiso permiso = PermisoMapper.toEntity(dto);

        return PermisoMapper.toDTO(permisoRepository.save(permiso));

    }

    // Metodo para retornar la lista de todos los permisos:
    public List<PermisoDTO> listaPermisos () {

        return PermisoMapper.toListDTO(permisoRepository.findAll());

    }

    // Metodo para buscar un permiso por id:
    public Optional<PermisoDTO> buscarPorId (Long idPermiso) {

        return permisoRepository.findById(idPermiso).map(PermisoMapper::toDTO);

    }

    // Metodo para buscar un permiso por nombre:
    public Optional<PermisoDTO> buscarPorNombre (String nombre) {

        return permisoRepository.findByNombre(nombre).map(PermisoMapper::toDTO);

    }

    // Metodo para actualizar un permiso:
    public Optional<PermisoDTO> actualizarPermiso (PermisoUpdateDTO dto, Long idPermiso) {

        return permisoRepository.findById(idPermiso).map(permiso -> {
            PermisoMapper.updateEntityFromDTO(dto, permiso);
            return PermisoMapper.toDTO(permisoRepository.save(permiso));
        });

    }

    // Metodo para eliminar un permiso:
    public boolean eliminarPermiso (Long idPermiso) {

        if (permisoRepository.existsById(idPermiso)) {
            permisoRepository.deleteById(idPermiso);    
            return true;
        }
        return false;

    }
    
}
