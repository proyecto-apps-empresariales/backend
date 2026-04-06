package com.proyecto_backend.demoAPI.businessLayer.services;

import java.util.List;

import com.proyecto_backend.demoAPI.businessLayer.dtos.RolCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RolDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RolUpdateDTO;

public interface IRolService {

    // Metodo para guardar un rol por medio de un RolCreateDTO:
    RolDTO guardarRol(RolCreateDTO dto);    

    // Metodo para obtener la lista de todos los roles:
    List<RolDTO> listaRoles();

    // Metodo para obtener un rol por medio de su id:
    RolDTO buscarRolPorId (Long idRol);

    // Metodo para obtener un rol por medio de su nombre:
    RolDTO buscarRolPorNombre (String nombreRol);

    // Metodo para actualizar un rol:
    RolDTO actualizarRol (RolUpdateDTO dto, Long idRol);

    // Metodo para eliminar un rol:
    void eliminarRol (Long idRol); 

} 


