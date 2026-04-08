package com.proyecto_backend.demoAPI.businessLayer.services.imps;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PermisoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PermisoDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PermisoUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IPermisoService;
import com.proyecto_backend.demoAPI.persistenceLayer.daos.PermisoDAO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermisoServiceImp implements IPermisoService {

    // Inyectamos el permisoDAO:
    private final PermisoDAO permisoDAO;

    // Metodo para guardar un permiso:
    @Override
    public PermisoDTO guardarPermiso(PermisoCreateDTO dto) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos no validos");
        }
        if (permisoDAO.buscarPorNombre(dto.getNombre()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nombre ya registrado");
        }

        return permisoDAO.guardarPermiso(dto);

    }

    // Metodo para retornar la lista de todos los permisos:
    @Override
    public List<PermisoDTO> listaPermisos() {

        return permisoDAO.listaPermisos();

    }

    // Metodo para buscar un permiso por id:
    @Override
    public PermisoDTO buscarPorId(Long idPermiso) {

        if (idPermiso == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }

        return permisoDAO.buscarPorId(idPermiso)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Permiso no encontrado"));

    }

    // Metodo para buscar un permiso por nombre:
    @Override
    public PermisoDTO buscarPorNombre(String nombre) {

        if (nombre == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nombre no valido");
        }

        return permisoDAO.buscarPorNombre(nombre)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Permiso no encontrado"));

    }

    // Metodo para actualizar un permiso:
    @Override
    public PermisoDTO actualizarPermiso(PermisoUpdateDTO dto, Long idPermiso) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos no validos");
        }
        if (idPermiso == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }

        return permisoDAO.actualizarPermiso(dto, idPermiso)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Permiso no encontrado"));

    }

    // Metodo para eliminar un permiso:
    @Override
    public void eliminarPermiso(Long idPermiso) {
        
        if (idPermiso == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no valido");
        }
        
        boolean eliminado = permisoDAO.eliminarPermiso(idPermiso);

        if (eliminado == false) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Permiso no encontrado");
        }
    
    }

}
