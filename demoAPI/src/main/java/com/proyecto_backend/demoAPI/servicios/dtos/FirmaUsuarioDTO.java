package com.proyecto_backend.demoAPI.servicios.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FirmaUsuarioDTO {
    
    // Atributos de la clase FirmaUsuarioDTO:
    private Long idFirma;
    private String archivoFirma;
    private LocalDate fecha;
    private String descripcion;
    private Long idUsuario;
    
}
