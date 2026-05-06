package com.proyecto_backend.demoAPI.businessLayer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de respuesta para Notificación")
public class NotificacionDTO {

    @Schema(description = "Identificador único de la notificación", example = "2001")
    private Long idNotificacion;

    @Schema(description = "Título de la notificación", example = "Recordatorio del documento")
    private String titulo;

    @Schema(description = "Mensaje de la notificación", example = "Tienes un documento pendiente por revisar")
    private String mensaje;

    @Schema(description = "Fecha en que se generó la notificación", example = "2026-04-13")
    private LocalDate fecha;

    @Schema(description = "Indica si la notificación ya fue leída", example = "false")
    private boolean fueLeida;

    @Schema(description = "Identificador del usuario destinatario de la notificación", example = "101")
    private Long idUsuario;

    @Schema(description = "Nombre del usuario destinatario", example = "Mateo García")
    private String nombreUsuario;

    @Schema(description = "Correo electrónico del usuario destinatario", example = "mateo.garcia@email.com")
    private String correoUsuario;

}