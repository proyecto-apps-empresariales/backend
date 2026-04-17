package com.proyecto_backend.demoAPI.businessLayer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para la creación de una Notificación")
public class NotificacionCreateDTO {

    @Schema(description = "Título de la notificación", example = "Recordatorio del documento")
    @NotBlank(message = "El título no puede estar vacío")
    @Size(min = 3, max = 50, message = "Debe tener entre 3 y 50 caracteres")
    private String titulo;

    @Schema(description = "Mensaje de la notificación", example = "Tienes un documento pendiente por revisar")
    @NotBlank(message = "El mensaje no puede estar vacío")
    @Size(min = 3, max = 255, message = "Debe tener entre 3 y 255 caracteres")
    private String mensaje;

    @Schema(description = "Identificador del usuario destinatario de la notificación", example = "101")
    @NotNull(message = "El idUsuario no puede ser nulo")
    @Positive(message = "El idUsuario debe ser mayor a 0")
    private Long idUsuario;

}