package com.proyecto_backend.demoAPI.servicios.dtos;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolUpdateDTO {
    
    // Atributos de la clase RolUpdateDTO:
    // @Size(min = m, max = n) valida que el atributo tenga minimo m y maximo n caracteres.
    @Size(min = 3, max = 20)
    private String nombre;
    @Size(min = 3, max = 250)
    private String descripcion;

}
