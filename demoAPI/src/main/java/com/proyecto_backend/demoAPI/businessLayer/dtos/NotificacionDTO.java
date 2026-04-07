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
public class NotificacionDTO {
    
    // Atributos de la clase NotificacionDTO:
    private Long idNotificacion;
    private String titulo;
    private String mensaje;
    private LocalDate fecha;
    private boolean fueLeida;
    private Long idUsuario;
    private String nombreUsuario;
    private String correoUsuario;

}
