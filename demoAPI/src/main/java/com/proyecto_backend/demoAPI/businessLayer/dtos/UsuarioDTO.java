package com.proyecto_backend.demoAPI.businessLayer.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    // Atributos de la clase UsuarioDTO:
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String celular;
    private LocalDate fechaCreacion;
    private Boolean estaActivo;
    private Long idOrganizacion;
    private Long idRol;
    
}
