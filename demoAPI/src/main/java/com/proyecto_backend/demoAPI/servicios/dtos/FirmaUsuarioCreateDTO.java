package com.proyecto_backend.demoAPI.servicios.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class FirmaUsuarioCreateDTO {
    
    // Atributos de la clase FirmaUsuarioCreateDTO:
    // @NotBlank valida que un String no sea null, no este vacio "" ó no contenga solo espacios " ".
    // @Size(min = m, max = n) valida que el atributo tenga minimo m y maximo n caracteres.
    @NotBlank
    @Size(min = 3, max = 255)
    private String archivoFirma;
    @NotBlank
    @Size(min = 3, max = 250)
    private String descripcion;
    // @NotNull valida que no sea null y @Positive que sea mayor a 0.
    @NotNull
    @Positive
    private Long idUsuario;
    
}
