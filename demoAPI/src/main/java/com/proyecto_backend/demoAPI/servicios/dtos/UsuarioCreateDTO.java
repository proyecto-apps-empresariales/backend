package com.proyecto_backend.demoAPI.servicios.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor 
@NoArgsConstructor
public class UsuarioCreateDTO {
    
    // @NotBlank valida que un String no sea null, no este vacio "" ó no contenga solo espacios " "
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @Email
    @NotBlank
    private String correo;
    @NotBlank
    private String contrasena;
    @NotBlank
    private String celular;
    // @NotNull valida que no sea null y @Positive que sea mayor a 0
    @NotNull
    @Positive
    private Long idOrganizacion;
    @NotNull
    @Positive
    private Long idRol;
    
}
