package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.proyecto_backend.demoAPI.businessLayer.dtos.OrganizacionCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.OrganizacionDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.OrganizacionUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IOrganizacionService;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.OrganizacionDAO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrganizacionServiceImp implements IOrganizacionService {

    // Creamos las instancias de los repositorios correspondientes:
    private final OrganizacionDAO organizacionDAO;

    // Metodo para guardar una organizacion por medio de una OrganizacionCreateDTO:
    @Override
    public OrganizacionDTO guardarOrganizacion (OrganizacionCreateDTO dto) {

        if (dto == null) { // 400 BAD_REQUEST: Datos No validos
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos no validos");
        }
        if (organizacionDAO.buscarOrganizacionPorNombre(dto.getNombre()).isPresent()) { // 409 CONFLICT: Dato ya existente
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nombre ya registrado");
        }

        return organizacionDAO.guardarOrganizacion(dto);

    }

    // Metodo para obtener la lista de todos las organizaciones:
    @Override
    public List<OrganizacionDTO> listaOrganizaciones () {

        return organizacionDAO.listaOrganizaciones();

    }

    // Metodo para obtener una organizacion por medio de su id:
    @Override
    public OrganizacionDTO buscarOrganizacionPorId (Long idOrganizacion) {

        if (idOrganizacion == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }

        return organizacionDAO.buscarOrganizacionPorId(idOrganizacion)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Organizacion no encontrada"));

    }

    // Metodo para obtener una organizacion por medio de su nombre:
    @Override
    public OrganizacionDTO buscarOrganizacionPorNombre (String nombreOrganizacion) {

        if (nombreOrganizacion == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nombre no valido");
        }

        return organizacionDAO.buscarOrganizacionPorNombre(nombreOrganizacion)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Organizacion no encontrada"));

    }

    // Metodo para actualizar una organizacion:
    @Override
    public OrganizacionDTO actualizarOrganizacion (OrganizacionUpdateDTO dto, Long idOrganizacion) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos no validos");
        }
        if (idOrganizacion == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }

        return organizacionDAO.actualizarOrganizacion(dto, idOrganizacion)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Organizacion no encontrada"));

    }

    // Metodo para eliminar una organizacion:
    @Override
    public void eliminarOrganizacion (Long idOrganizacion) {

        if (idOrganizacion == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }

        boolean eliminado = organizacionDAO.eliminarOrganizacion(idOrganizacion);

        if (eliminado == false) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Organizacion no encontrada");
        }

    }
    
}
