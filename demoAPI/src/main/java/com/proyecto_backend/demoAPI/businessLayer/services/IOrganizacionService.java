package com.proyecto_backend.demoAPI.businessLayer.services;

import java.util.List;
import com.proyecto_backend.demoAPI.businessLayer.dtos.OrganizacionCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.OrganizacionDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.OrganizacionUpdateDTO;

public interface IOrganizacionService {

    // Metodo para guardar una organizacion por medio de una OrganizacionCreateDTO:
    OrganizacionDTO guardarOrganizacion (OrganizacionCreateDTO dto);

    // Metodo para obtener la lista de todos las organizaciones:
    List<OrganizacionDTO> listaOrganizaciones ();

    // Metodo para obtener una organizacion por medio de su id:
    OrganizacionDTO buscarOrganizacionPorId (Long idOrganizacion);

    // Metodo para obtener una organizacion por medio de su nombre:
    OrganizacionDTO buscarOrganizacionPorNombre (String nombreOrganizacion);

    // Metodo para actualizar una organizacion:
    OrganizacionDTO actualizarOrganizacion (OrganizacionUpdateDTO dto, Long idOrganizacion);

    // Metodo para eliminar una organizacion:
    void eliminarOrganizacion (Long idOrganizacion);

} 
