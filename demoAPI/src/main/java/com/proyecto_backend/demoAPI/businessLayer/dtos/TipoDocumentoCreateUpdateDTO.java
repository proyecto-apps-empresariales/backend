package com.proyecto_backend.demoAPI.businessLayer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para la creacion de un tipo de documento")
public class TipoDocumentoCreateUpdateDTO {

    @Schema(description = "Nombre del tipo de documento", example = "Registros de sanciones sanitarias")
    @NotBlank(message = "El nombre del tipo de documento no puede estar vacio")
    @Size(max = 20, message = "Debe tener máximo 20 caracteres")
    private String nombre;

    @Schema(description = "Descripcion del tipo de documento", example = "Documento administrativo y legal que contiene la información sobre las sanciones ejecutoriadas")
    @NotBlank(message = "La descripcion del tipo de documento no puede estar vacio")
    @Size(max = 250, message = "Debe tener máximo 250 caracteres")
    private String descripcion;
}
