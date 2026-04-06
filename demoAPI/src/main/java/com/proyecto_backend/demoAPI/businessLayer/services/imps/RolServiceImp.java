package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.proyecto_backend.demoAPI.businessLayer.dtos.RolCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RolDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RolUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IRolService;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.RolDAO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RolServiceImp implements IRolService {

    // Inyectamos el RolDAO:
    private final RolDAO rolDAO;

    // Metodo para guardar un rol por medio de un RolCreateDTO:
    @Override
    public RolDTO guardarRol(RolCreateDTO dto) {

        if (dto == null) { // 400 BAD_REQUEST: Datos No validos
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos no validos");
        }
        if (rolDAO.buscarRolPorNombre(dto.getNombre()).isPresent()) { // 409 CONFLICT: Dato ya existente
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nombre ya registrado");
        }

        return rolDAO.guardarRol(dto);

    }

    // Metodo para obtener la lista de todos los roles:
    @Override
    public List<RolDTO> listaRoles () {

        return rolDAO.listaRoles();

    }

    // Metodo para obtener un rol por medio de su id:
    @Override
    public RolDTO buscarRolPorId (Long idRol) {

        if (idRol == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }

        return rolDAO.buscarRolPorId(idRol)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado"));

    }

    // Metodo para obtener un rol por medio de su nombre:
    @Override
    public RolDTO buscarRolPorNombre (String nombreRol) {

        if (nombreRol == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nombre no valido");
        }
        
        return rolDAO.buscarRolPorNombre(nombreRol)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado"));

    }

    // Metodo para actualizar un rol:
    @Override
    public RolDTO actualizarRol (RolUpdateDTO dto, Long idRol) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos no validos");
        }
        if (idRol == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }
        
        return rolDAO.actualizarRol(dto, idRol)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado"));

    }

    // Metodo para eliminar un rol:
    @Override
    public void eliminarRol (Long idRol) {

        if (idRol == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }

        boolean eliminado = rolDAO.eliminarRol(idRol);

        if (eliminado == false) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado");
        }

    }

}
