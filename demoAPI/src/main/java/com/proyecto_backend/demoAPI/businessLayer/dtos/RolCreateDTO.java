package com.proyecto_backend.demoAPI.businessLayer.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolCreateDTO {
    
    // Atributos de la clase RolCreateDTO:
    // @NotBlank valida que un String no sea null, no este vacio "" ó no contenga solo espacios " ".
    // @Size(min = m, max = n) valida que el atributo tenga minimo m y maximo n caracteres.
    @NotBlank
    @Size(min = 3, max = 20)
    private String nombre;
    @NotBlank
    @Size(min = 3, max = 250)
    private String descripcion;
    
}
