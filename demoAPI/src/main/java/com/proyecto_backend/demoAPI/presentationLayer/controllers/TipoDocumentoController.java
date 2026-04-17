package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoDocumentoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoDocumentoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.ITipoDocumentoService;
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
@RequestMapping("/tipoDocumento")
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "Tipos de Documento",
        description = "Operaciones CRUD para la gestión de tipos de documentos"
)
@CrossOrigin(origins = "*")
public class TipoDocumentoController {

    private final ITipoDocumentoService tipoDocumentoService;

    /**
     * Crear un nuevo tipo de documento
     */
    @PostMapping("/crear")
    @Operation(
            summary = "Crear tipo de documento",
            description = "Crea un nuevo tipo de documento con su nombre, descripción y requerimientos asociados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Tipo de documento creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TipoDocumentoResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos o faltantes"
            )
    })
    public ResponseEntity<TipoDocumentoResponseDTO> createTipoDocumento(
            @Parameter(description = "Datos necesarios para crear el tipo de documento", required = true)
            @RequestBody TipoDocumentoCreateUpdateDTO dto
    ) {

        TipoDocumentoResponseDTO response = tipoDocumentoService.createTipoDocumento(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtener tipo de documento por ID
     */
    @GetMapping("/{idTipoDocumento}")
    @Operation(
            summary = "Obtener tipo de documento por ID",
            description = "Retorna la información completa de un tipo de documento"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tipo de documento encontrado",
                    content = @Content(schema = @Schema(implementation = TipoDocumentoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tipo de documento no encontrado"
            )
    })
    public ResponseEntity<TipoDocumentoResponseDTO> getTipoDocumentoById(
            @Parameter(description = "ID del tipo de documento", example = "4", required = true)
            @PathVariable Long idTipoDocumento
    ) {

        TipoDocumentoResponseDTO response = tipoDocumentoService.getTipoDocumentoByID(idTipoDocumento);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener todos los tipos de documento
     */
    @GetMapping
    @Operation(
            summary = "Listar tipos de documento",
            description = "Obtiene la lista completa de tipos de documentos registrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = TipoDocumentoResponseDTO.class))
    )
    public ResponseEntity<List<TipoDocumentoResponseDTO>> getAllTipoDocumentos() {

        List<TipoDocumentoResponseDTO> tipos = tipoDocumentoService.getAllTipoDocumento();
        return ResponseEntity.ok(tipos);
    }

    /**
     * Actualizar tipo de documento
     */
    @PatchMapping("/update/{idTipoDocumento}")
    @Operation(
            summary = "Actualizar tipo de documento",
            description = "Actualiza los datos de un tipo de documento existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tipo de documento actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = TipoDocumentoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tipo de documento no encontrado"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos"
            ),
    })
    public ResponseEntity<TipoDocumentoResponseDTO> updateDocumento(
            @Parameter(description = "ID del tipo de documento a actualizar", example = "2", required = true)
            @PathVariable Long idTipoDocumento,

            @Parameter(description = "Datos a actualizar del tipo de documento", required = true)
            @RequestBody TipoDocumentoCreateUpdateDTO dto
    ) {

        TipoDocumentoResponseDTO response = tipoDocumentoService.updateTipoDocumento(idTipoDocumento, dto);
        return ResponseEntity.ok(response);
    }
}
