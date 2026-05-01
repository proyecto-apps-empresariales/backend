package com.proyecto_backend.demoAPI.businessLayer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "DTO para actualizar la información de una Notificación")
public class NotificacionUpdateDTO {

    @Schema(description = "Título de la notificación", example = "Cambio de estado del documento")
    @Size(min = 3, max = 50, message = "Debe tener entre 3 y 50 caracteres")
    private String titulo;

    @Schema(description = "Mensaje de la notificación", example = "El documento ha sido aprobado por el revisor")
    @Size(min = 3, max = 255, message = "Debe tener entre 3 y 255 caracteres")
    private String mensaje;

    @Schema(description = "Indica si la notificación ya fue leída", example = "true")
    private Boolean fueLeida;

    @Schema(description = "Identificador del usuario destinatario de la notificación", example = "101")
    @Positive(message = "El idUsuario debe ser mayor a 0")
    private Long idUsuario;

}