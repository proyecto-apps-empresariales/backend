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
@Schema(description = "DTO para actualizar la información de una Firma de Usuario")
public class FirmaUsuarioUpdateDTO {

    @Schema(description = "Archivo de la firma del usuario", example = "firma_usuario.pdf")
    @Size(min = 3, max = 255, message = "Debe tener entre 3 y 255 caracteres")
    private String archivoFirma;

    @Schema(description = "Descripción de la firma del usuario", example = "Firma digital actualizada para documentos oficiales")
    @Size(min = 3, max = 250, message = "Debe tener entre 3 y 250 caracteres")
    private String descripcion;

    @Schema(description = "Identificador del usuario asociado a la firma", example = "101")
    @Positive(message = "El idUsuario debe ser mayor a 0")
    private Long idUsuario;

}