package com.proyecto_backend.demoAPI.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PermisoDTO {
    
    // Atributos de la clase PermisoDTO:
    private Long idPermiso;
    private String nombre;
    private String descripcion;
    
}
