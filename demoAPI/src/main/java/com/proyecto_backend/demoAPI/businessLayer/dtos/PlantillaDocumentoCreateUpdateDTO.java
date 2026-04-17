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
@Schema(description = "DTO para crear y actualizar plantilla de documentos")
public class PlantillaDocumentoCreateUpdateDTO {

    @Schema(description = "Url de la plantilla documento en la nube", example = "https://storage.salud.gov.co/documentos/expedientes/12345/versiones/1.0/plantilla.pdf")
    @NotBlank(message = "La URL de la plantilla documento no puede estar vacia")
    @Size(max = 255, message = "Debe tener máximo 255 caracteres")
    private String archivoUrl;

    @Schema(description = "Descripcion de la plantilla documento", example = "Plantilla para registro de sanciones sanitarias")
    @NotBlank(message = "La descripcion de la plantilla documento no puede estar vacia")
    @Size(max = 250, message = "Debe tener máximo 250 caracteres")
    private String descripcion;

    @Schema(description = "Nombre del tipo de documento que usa la plantilla documento", example = "Registros de sanciones sanitarias", accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank(message = "El nombre del tipo de documento no puede estar vacio")
    private String tipoDocumento;
}
