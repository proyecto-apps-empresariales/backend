package com.proyecto_backend.demoAPI.servicios.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolDTO {
    
    // Atributos de la clase RolDTO:
    private Long idRol;
    private String nombre;
    private String descripcion;
}
