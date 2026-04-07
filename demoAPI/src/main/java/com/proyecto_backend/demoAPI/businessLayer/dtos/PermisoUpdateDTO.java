package com.proyecto_backend.demoAPI.businessLayer.dtos;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PermisoUpdateDTO {
    
    // Atributos de la clase PermisoUpdateDTO:
    
    @Size(min = 3, max = 20)
    private String nombre;

    @Size(min = 3, max = 250)
    private String descripcion;
    
}
