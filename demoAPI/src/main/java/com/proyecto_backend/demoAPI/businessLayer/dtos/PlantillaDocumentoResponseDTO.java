package com.proyecto_backend.demoAPI.businessLayer.dtos;

import com.proyecto_backend.demoAPI.persistenceLayer.entities.TipoDocumentoEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Informacion sobre la plantilla documento")
public class PlantillaDocumentoResponseDTO {

    @Schema(description = "Id único la plantilla", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull
    private Long id;

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
