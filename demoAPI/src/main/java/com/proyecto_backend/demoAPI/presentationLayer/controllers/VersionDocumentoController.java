package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.VersionDocumentoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.VersionDocumentoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.VersionDocumentoUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IVersionDocumentoService;
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
@RequestMapping("/versiones")
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "Versiones de Documento",
        description = "Operaciones CRUD para la gestión de versiones de documentos"
)
@CrossOrigin(origins = "*")
public class VersionDocumentoController {

    private final IVersionDocumentoService versionDocumentoService;

    /**
     * Crear una nueva versión de documento
     */
    @PostMapping("/crear")
    @Operation(
            summary = "Crear versión de documento",
            description = "Crea una nueva versión asociada a un documento existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Versión creada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VersionDocumentoResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos o documento no encontrado"
            )
    })
    public ResponseEntity<VersionDocumentoResponseDTO> createVersion(
            @Parameter(description = "Datos necesarios para crear la versión", required = true)
            @RequestBody VersionDocumentoCreateDTO dto
    ) {

        VersionDocumentoResponseDTO response = versionDocumentoService.createVersion(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtener todas las versiones de un documento
     */
    @GetMapping("/doc/{idDocumento}")
    @Operation(
            summary = "Listar versiones de un documento",
            description = "Obtiene todas las versiones asociadas a un documento específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Versiones obtenidas exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VersionDocumentoResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Documento no encontrado"
            )
    })
    public ResponseEntity<List<VersionDocumentoResponseDTO>> getAllVersiones(
            @Parameter(description = "ID del documento", example = "10", required = true)
            @PathVariable Long idDocumento
    ) {

        List<VersionDocumentoResponseDTO> versiones = versionDocumentoService.getVersionesByDocumento(idDocumento);
        return ResponseEntity.ok(versiones);
    }

    /**
     * Obtener versión por ID
     */
    @GetMapping("/{idVersion}")
    @Operation(
            summary = "Obtener versión por ID",
            description = "Retorna la información completa de una versión de documento"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Versión encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VersionDocumentoResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Versión no encontrada"
            )
    })
    public ResponseEntity<VersionDocumentoResponseDTO> getVersionById(
            @Parameter(description = "ID de la versión", example = "3", required = true)
            @PathVariable Long idVersion
    ) {

        VersionDocumentoResponseDTO response = versionDocumentoService.getVersionById(idVersion);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualizar versión de documento
     */
    @PatchMapping("/editar/{idVersion}")
    @Operation(
            summary = "Actualizar versión de documento",
            description = "Actualiza los datos de una versión existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Versión actualizada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VersionDocumentoResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Versión no encontrada"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos"
            )
    })
    public ResponseEntity<VersionDocumentoResponseDTO> updateVersion(
            @Parameter(description = "ID de la versión a actualizar", example = "5", required = true)
            @PathVariable Long idVersion,

            @Parameter(description = "Datos a actualizar de la versión", required = true)
            @RequestBody VersionDocumentoUpdateDTO dto
    ) {

        VersionDocumentoResponseDTO response = versionDocumentoService.updateVersion(idVersion, dto);
        return ResponseEntity.ok(response);
    }

    /**
     * Eliminar versión de documento
     */
    @DeleteMapping("/borrar/{idVersion}")
    @Operation(
            summary = "Eliminar versión de documento",
            description = "Elimina una versión específica de un documento"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Versión eliminada exitosamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Versión no encontrada"
            )
    })
    public ResponseEntity<Void> deleteVersion(
            @Parameter(description = "ID de la versión a eliminar", example = "7", required = true)
            @PathVariable Long idVersion
    ) {

        versionDocumentoService.deleteVersion(idVersion);
        return ResponseEntity.noContent().build();
    }
}
