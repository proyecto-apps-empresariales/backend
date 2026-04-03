package com.proyecto_backend.demoAPI.businessLayer.dtos;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizacionUpdateDTO {
    
    // Atributos de la clase OrganizacionUpdateDTO:
    // @Size(min = m, max = n) valida que el atributo tenga minimo m y maximo n caracteres.
    @Size(min = 3,max = 50)
    private String nombre;
    @Size(min = 3,max = 250)
    private String descripcion;
}
