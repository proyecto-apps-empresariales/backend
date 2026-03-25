package com.proyecto_backend.demoAPI.servicios.dtos;


import jakarta.validation.constraints.Email;
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
public class UsuarioCreateDTO {
    
    // Atributos de la clase UsuarioCreateDTO:
    // @NotBlank valida que un String no sea null, no este vacio "" ó no contenga solo espacios " ".
    // @Size(min = m, max = n) valida que el atributo tenga minimo m y maximo n caracteres.
    @NotBlank
    @Size(min = 3, max = 50)
    private String nombre;
    @NotBlank
    @Size(min = 3,max = 50)
    private String apellido;
    @Email
    @NotBlank
    @Size(min = 3,max = 50)
    private String correo;
    @NotBlank
    @Size(min = 3,max = 250)
    private String contrasena;
    @NotBlank
    @Size(min = 3,max = 10)
    private String celular;
    // @NotNull valida que no sea null y @Positive que sea mayor a 0.
    @NotNull
    @Positive
    private Long idOrganizacion;
    @NotNull
    @Positive
    private Long idRol;
    
}
