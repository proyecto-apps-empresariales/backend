package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoDocumentoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IRequerimientoDocumentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requerimientoDocumento")
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "Requerimientos de Documento",
        description = "Consulta de requerimientos asociados a tipos de documento"
)
@CrossOrigin(origins = "*")
public class RequerimientoDocumentoController {

    private final IRequerimientoDocumentoService requerimientoDocumentoService;

    /**
     * Obtener requerimiento por ID
     */
    @GetMapping("/{idRequerimiento}")
    @Operation(
            summary = "Obtener requerimiento por ID",
            description = "Retorna la información completa de un requerimiento de documento"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Requerimiento encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RequerimientoDocumentoResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Requerimiento no encontrado"
            )
    })
    public ResponseEntity<RequerimientoDocumentoResponseDTO> getRequerimientoById(
            @Parameter(description = "ID del requerimiento", example = "3", required = true)
            @PathVariable Long idRequerimiento
    ) {

        RequerimientoDocumentoResponseDTO response = requerimientoDocumentoService.getRequerimientoById(idRequerimiento);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener todos los requerimientos
     */
    @GetMapping
    @Operation(
            summary = "Listar requerimientos",
            description = "Obtiene la lista completa de requerimientos de documentos"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista obtenida exitosamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RequerimientoDocumentoResponseDTO.class)
            )
    )
    public ResponseEntity<List<RequerimientoDocumentoResponseDTO>> getAllRequerimientos() {

        List<RequerimientoDocumentoResponseDTO> requerimientos = requerimientoDocumentoService.getAllRequerimientos();
        return ResponseEntity.ok(requerimientos);
    }
}
