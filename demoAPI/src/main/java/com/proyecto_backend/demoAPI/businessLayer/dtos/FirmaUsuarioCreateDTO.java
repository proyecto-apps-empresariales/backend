package com.proyecto_backend.demoAPI.businessLayer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "DTO para la creación de una Firma de Usuario")
public class FirmaUsuarioCreateDTO {

    @Schema(description = "Archivo de la firma del usuario", example = "firma_usuario.pdf")
    @NotBlank(message = "El archivo de la firma no puede estar vacío")
    @Size(min = 3, max = 255, message = "Debe tener entre 3 y 255 caracteres")
    private String archivoFirma;

    @Schema(description = "Descripción de la firma del usuario", example = "Firma digital utilizada para validar documentos")
    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 3, max = 250, message = "Debe tener entre 3 y 250 caracteres")
    private String descripcion;

    @Schema(description = "Identificador del usuario asociado a la firma", example = "101")
    @NotNull(message = "El idUsuario no puede ser nulo")
    @Positive(message = "El idUsuario debe ser mayor a 0")
    private Long idUsuario;

}