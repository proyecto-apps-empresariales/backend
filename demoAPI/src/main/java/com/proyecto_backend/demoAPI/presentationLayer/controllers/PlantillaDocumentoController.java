package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PlantillaDocumentoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PlantillaDocumentoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IPlantillaDocumentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plantillaDocumento")
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "Plantillas de Documento",
        description = "Operaciones CRUD para la gestión de plantillas de documentos"
)
@CrossOrigin(origins = "*")
public class PlantillaDocumentoController {

    private final IPlantillaDocumentoService plantillaDocumentoService;

    /**
     * Crear una nueva plantilla de documento
     */
    @PostMapping("/crear")
    @Operation(
            summary = "Crear plantilla de documento",
            description = "Crea una nueva plantilla de documento con sus requerimientos asociados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Plantilla creada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlantillaDocumentoResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos o faltantes"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    public ResponseEntity<PlantillaDocumentoResponseDTO> createPlantillaDocumento(
            @Parameter(description = "Datos necesarios para crear la plantilla", required = true)
            @RequestBody PlantillaDocumentoCreateUpdateDTO dto
    ) {
        PlantillaDocumentoResponseDTO response = plantillaDocumentoService.createPlantilla(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtener plantilla por ID
     */
    @GetMapping("/{idPlantillaDocumento}")
    @Operation(
            summary = "Obtener plantilla por ID",
            description = "Retorna la información completa de una plantilla de documento"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Plantilla encontrada",
                    content = @Content(schema = @Schema(implementation = PlantillaDocumentoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Plantilla no encontrada"
            )
    })
    public ResponseEntity<PlantillaDocumentoResponseDTO> getPlantillaDocumentoById(
            @Parameter(description = "ID de la plantilla", example = "5", required = true)
            @PathVariable Long idPlantillaDocumento
    ) {
        PlantillaDocumentoResponseDTO response = plantillaDocumentoService.getPlantillaById(idPlantillaDocumento);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener todas las plantillas
     */
    @GetMapping
    @Operation(
            summary = "Listar plantillas",
            description = "Obtiene la lista completa de plantillas de documentos registradas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = PlantillaDocumentoResponseDTO.class))
    )
    public ResponseEntity<List<PlantillaDocumentoResponseDTO>> getAllPlantillaDocumentos() {

        List<PlantillaDocumentoResponseDTO> plantillas = plantillaDocumentoService.getAllPlantillas();
        return ResponseEntity.ok(plantillas);
    }

    /**
     * Actualizar plantilla de documento
     */
    @PatchMapping("/update/{idPlantillaDocumento}")
    @Operation(
            summary = "Actualizar plantilla de documento",
            description = "Actualiza los datos de una plantilla existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Plantilla actualizada exitosamente",
                    content = @Content(schema = @Schema(implementation = PlantillaDocumentoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Plantilla no encontrada"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos"
            )
    })
    public ResponseEntity<PlantillaDocumentoResponseDTO> updateDocumento(
            @Parameter(description = "ID de la plantilla a actualizar", example = "3", required = true)
            @PathVariable Long idPlantillaDocumento,

            @Parameter(description = "Datos a actualizar de la plantilla", required = true)
            @RequestBody PlantillaDocumentoCreateUpdateDTO dto
    ) {
        PlantillaDocumentoResponseDTO response = plantillaDocumentoService.updatePlantilla(idPlantillaDocumento, dto);
        return ResponseEntity.ok(response);
    }
}
