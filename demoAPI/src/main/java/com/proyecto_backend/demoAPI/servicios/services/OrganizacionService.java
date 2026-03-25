package com.proyecto_backend.demoAPI.servicios.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.proyecto_backend.demoAPI.persistencia.entidades.Organizacion;
import com.proyecto_backend.demoAPI.persistencia.mappers.OrganizacionMapper;
import com.proyecto_backend.demoAPI.persistencia.repositorios.OrganizacionRepository;
import com.proyecto_backend.demoAPI.servicios.dtos.OrganizacionCreateDTO;
import com.proyecto_backend.demoAPI.servicios.dtos.OrganizacionDTO;
import com.proyecto_backend.demoAPI.servicios.dtos.OrganizacionUpdateDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrganizacionService { 

    // Creamos las instancias de los repositorios correspondientes:
    private final OrganizacionRepository organizacionRepository;

    // Metodo para guardar una organizacion por medio de una OrganizacionCreateDTO:
    @Transactional
    public OrganizacionDTO guardarOrganizacion (OrganizacionCreateDTO dto) {

        if (dto == null) { // 400 BAD_REQUEST: Datos No validos
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos no validos");
        }
        if (organizacionRepository.findByNombre(dto.getNombre()).isPresent()) { // 409 CONFLICT: Dato ya existente
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nombre ya registrado");
        }

        Organizacion org = OrganizacionMapper.toEntity(dto);
        org.setFechaCreacion(LocalDate.now());

        return OrganizacionMapper.toDTO(organizacionRepository.save(org));
    }

    // Metodo para obtener la lista de todos las organizaciones:
    public List<OrganizacionDTO> listaOrganizaciones () {

        return OrganizacionMapper.toDTOList(organizacionRepository.findAll());
    }

    // Metodo para obtener una organizacion por medio de su id:
    public OrganizacionDTO buscarOrganizacionPorId (Long idOrganizacion) {

        return OrganizacionMapper.toDTO(buscarOrganizacionId(idOrganizacion));
    }

    // Metodo para obtener una organizacion por medio de su nombre:
    public OrganizacionDTO buscarOrganizacionPorNombre (String nombreOrganizacion) {

        return OrganizacionMapper.toDTO(buscarOrganizacionNombre(nombreOrganizacion));
    }

    // Metodo para actualizar una organizacion:
    @Transactional
    public OrganizacionDTO actualizarOrganizacion (OrganizacionUpdateDTO dto, Long idOrganizacion) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos no validos");
        }

        Organizacion org = buscarOrganizacionId(idOrganizacion);

        if (dto.getNombre() != null) {
            org.setNombre(dto.getNombre());
        }
        if (dto.getDescripcion() != null) {
            org.setDescripcion(dto.getDescripcion());
        }

        return OrganizacionMapper.toDTO(organizacionRepository.save(org));
    }

    // Metodo para eliminar una organizacion:
    @Transactional
    public void eliminarOrganizacion (Long idOrganizacion) {

        organizacionRepository.delete(buscarOrganizacionId(idOrganizacion));
    }

    // Metodo privado para buscar una organizacion por ID:
    private Organizacion buscarOrganizacionId (Long idOrganizacion) {

        if (idOrganizacion == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }
        return organizacionRepository.findById(idOrganizacion)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Organizacion no encontrada"));
    }

    // Metodo para buscar una organizacion por nombre:
    private Organizacion buscarOrganizacionNombre (String nombre) {

        if (nombre == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nombre no valido");
        }
        return organizacionRepository.findByNombre(nombre)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Organizacion no encontrada"));
    }

}
