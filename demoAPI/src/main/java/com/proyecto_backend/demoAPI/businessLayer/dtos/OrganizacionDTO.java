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
public class OrganizacionDTO {
    
    // Atributos de la clase OrganizacionDTO:
    private Long idOrganizacion;
    private String nombre;
    private String descripcion;
    private LocalDate fechaCreacion;
}
