package com.proyecto_backend.demoAPI.businessLayer.services;

import java.util.List;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PermisoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PermisoDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PermisoUpdateDTO;

public interface IPermisoService {
    
    // Metodo para guardar un permiso:
    PermisoDTO guardarPermiso (PermisoCreateDTO dto);

    // Metodo para retornar la lista de todos los permisos:
    List<PermisoDTO> listaPermisos ();
    
    // Metodo para buscar un permiso por id:
    PermisoDTO buscarPorId (Long idPermiso);
    
    // Metodo para buscar un permiso por nombre:
    PermisoDTO buscarPorNombre (String nombre);
    
    // Metodo para actualizar un permiso:
    PermisoDTO actualizarPermiso (PermisoUpdateDTO dto, Long idPermiso);
    
    // Metodo para eliminar un permiso:
    void eliminarPermiso (Long idPermiso);
    
}
