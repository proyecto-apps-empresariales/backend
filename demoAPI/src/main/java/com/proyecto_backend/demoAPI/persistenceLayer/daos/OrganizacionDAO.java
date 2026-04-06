package com.proyecto_backend.demoAPI.persistenceLayer.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.proyecto_backend.demoAPI.businessLayer.dtos.OrganizacionCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.OrganizacionDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.OrganizacionUpdateDTO;
import com.proyecto_backend.demoAPI.persistenceLayer.entities.Organizacion;
import com.proyecto_backend.demoAPI.persistenceLayer.mappers.OrganizacionMapper;
import com.proyecto_backend.demoAPI.persistenceLayer.repositories.IOrganizacionRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrganizacionDAO {
    
    // Inyectamos el repositorio de Organizacion:
    private final IOrganizacionRepository organizacionRepository;

    // Metodo para guardar una organizacion por medio de una OrganizacionCreateDTO:
    public OrganizacionDTO guardarOrganizacion (OrganizacionCreateDTO dto) {

        Organizacion organizacion = OrganizacionMapper.toEntity(dto);

        return OrganizacionMapper.toDTO(organizacionRepository.save(organizacion));
    }

    // Metodo para obtener la lista de todos las organizaciones:
    public List<OrganizacionDTO> listaOrganizaciones () {

        return OrganizacionMapper.toDTOList(organizacionRepository.findAll());

    }

    // Metodo para obtener una organizacion por medio de su id:
    public Optional<OrganizacionDTO> buscarOrganizacionPorId (Long idOrganizacion) {

        return organizacionRepository.findById(idOrganizacion).map(OrganizacionMapper::toDTO);

    }

    // Metodo para buscar una organizacion por id y retornar la entidad (casos especiales):
    public Optional<Organizacion> buscarOrganizacionEntidadPorId (Long idOrganizacion) {

        return organizacionRepository.findById(idOrganizacion);
        
    }

    // Metodo para buscar una organizacion por nombre:
    public Optional<OrganizacionDTO> buscarOrganizacionPorNombre (String nombreOrganizacion) {

        return organizacionRepository.findByNombre(nombreOrganizacion).map(OrganizacionMapper::toDTO);

    }

    // Metodo para actualizar una organizacion:
    public Optional<OrganizacionDTO> actualizarOrganizacion (OrganizacionUpdateDTO dto, Long idOrganizacion) {
        
        return organizacionRepository.findById(idOrganizacion).map(organizacion -> {
            OrganizacionMapper.updateEntityFromDTO(dto, organizacion);
            return OrganizacionMapper.toDTO(organizacionRepository.save(organizacion));
        });

    }

    // Metodo para eliminar una organizacion:
    public boolean eliminarOrganizacion (Long idOrganizacion) {
        
        if (organizacionRepository.existsById(idOrganizacion)) {
            organizacionRepository.deleteById(idOrganizacion);
            return true;
        }
        return false;
        
    }
    
    


}
