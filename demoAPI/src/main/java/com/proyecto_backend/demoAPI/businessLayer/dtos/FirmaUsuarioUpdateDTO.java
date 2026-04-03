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
public class FirmaUsuarioUpdateDTO {

    // Atributos de la clase FirmaUsuarioCreateDTO:
    // @Size(min = m, max = n) valida que el atributo tenga minimo m y maximo n caracteres.
    @Size(min = 3, max = 255)
    private String archivoFirma;
    @Size(min = 3, max = 250)
    private String descripcion;
    // @Positive que sea mayor a 0.
    @Positive
    private Long idUsuario;
    
}
