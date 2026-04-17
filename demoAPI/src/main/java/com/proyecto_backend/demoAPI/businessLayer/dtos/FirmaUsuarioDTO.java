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
@Schema(description = "DTO de respuesta para Firma de Usuario")
public class FirmaUsuarioDTO {

    @Schema(description = "Identificador único de la firma", example = "301")
    private Long idFirma;

    @Schema(description = "Archivo de la firma del usuario", example = "firma_usuario.pdf")
    private String archivoFirma;

    @Schema(description = "Fecha en que se registró la firma", example = "2026-04-13")
    private LocalDate fecha;

    @Schema(description = "Descripción de la firma del usuario", example = "Firma digital utilizada para validar documentos")
    private String descripcion;

    @Schema(description = "Identificador del usuario asociado a la firma", example = "101")
    private Long idUsuario;

    @Schema(description = "Nombre del usuario asociado a la firma", example = "Mateo García")
    private String nombreUsuario;

    @Schema(description = "Correo electrónico del usuario asociado a la firma", example = "mateo.garcia@email.com")
    private String correoUsuario;

}