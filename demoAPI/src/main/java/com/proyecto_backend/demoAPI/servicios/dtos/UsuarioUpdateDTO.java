package com.proyecto_backend.demoAPI.servicios.dtos;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioUpdateDTO {

    private String nombre;
    private String apellido;
    private String contrasena;
    private String celular;
    private Boolean estaActivo;
    @Positive
    private Long idOrganizacion;
    @Positive
    private Long idRol;
    
}
