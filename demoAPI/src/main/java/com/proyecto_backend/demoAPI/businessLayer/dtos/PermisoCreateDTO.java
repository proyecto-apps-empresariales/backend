package com.proyecto_backend.demoAPI.businessLayer.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PermisoCreateDTO {
    
    // Atributos de la clase PermisoCreateDTO:
    
    @NotBlank
    @Size(min = 3, max = 20)
    private String nombre;

    @NotBlank
    @Size(min = 3, max = 250)
    private String descripcion;

}
