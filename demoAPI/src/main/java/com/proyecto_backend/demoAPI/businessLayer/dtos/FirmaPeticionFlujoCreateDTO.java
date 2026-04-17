package com.proyecto_backend.demoAPI.businessLayer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para crear firma de petición")
public class FirmaPeticionFlujoCreateDTO {

    @Schema(description = "Id del usuario que firma", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull(message = "El id del usuario que firma no puede estar vacio")
    private Long usuarioFirmador;

    @Schema(description = "Id de la petición que se firma", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull(message = "El id de la peticion no puede estar vacio")
    private Long peticion;

    @Schema(description = "Observación del documento", example = "Cumple con los requsitos, se firma después de revisión")
    @NotBlank(message = "La observación del documento no puede estar vacio")
    @Size(max = 250, message = "Debe tener máximo 250 caracteres")
    private String observacion;
}
