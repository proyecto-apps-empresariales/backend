package com.proyecto_backend.demoAPI.businessLayer.dtos;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioUpdateDTO {

    // Atributos de la clase OrganizacionUpdateDTO:
    // @Size(min = m, max = n) valida que el atributo tenga minimo m y maximo n caracteres.
    @Size(min = 3, max = 50)
    private String nombre;
    @Size(min = 3, max = 50)
    private String apellido;
    @Size(min = 3, max = 50)
    private String celular;
    private Boolean estaActivo;
    // @Positive que sea mayor a 0.
    @Positive
    private Long idOrganizacion;
    @Positive
    private Long idRol;
    
}
