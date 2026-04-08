package com.proyecto_backend.demoAPI.businessLayer.dtos;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionUpdateDTO {
    
    // Atributos de la clase NotificacionUpdateDTO:

    @Size(min = 3, max = 50)
    private String titulo;

    @Size(min = 3, max = 255)
    private String mensaje;

    private Boolean fueLeida;

    @Positive
    private Long idUsuario;

}
