package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.DocumentoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.DocumentoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.DocumentoUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IDocumentoService;
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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/documentos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Documentos", description = "Operaciones CRUD y consultas avanzadas para gestión de documentos")
@CrossOrigin(origins = "*")
public class DocumentoController {

    private final IDocumentoService documentoService;

    /**
     * Crear un nuevo documento
     */
    @PostMapping("/crear")
    @Operation(
            summary = "Crear documento",
            description = "Crea un nuevo documento con su tipo, usuario creador y metadatos asociados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Documento creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DocumentoResponseDTO.class)
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
    public ResponseEntity<DocumentoResponseDTO> createDocumento(
            @Parameter(description = "Datos necesarios para crear el documento", required = true)
            @RequestBody DocumentoCreateDTO dto
    ) {
        DocumentoResponseDTO response = documentoService.createDocumento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtener documento por ID
     */
    @GetMapping("/{idDocumento}")
    @Operation(
            summary = "Obtener documento por ID",
            description = "Retorna la información completa de un documento"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Documento encontrado",
                    content = @Content(schema = @Schema(implementation = DocumentoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Documento no encontrado"
            )
    })
    public ResponseEntity<DocumentoResponseDTO> getDocumentoById(
            @Parameter(description = "ID del documento", example = "10", required = true)
            @PathVariable Long idDocumento
    ) {

        DocumentoResponseDTO response = documentoService.getDocumentoById(idDocumento);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener todos los documentos
     */
    @GetMapping
    @Operation(
            summary = "Listar documentos",
            description = "Obtiene la lista completa de documentos registrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = DocumentoResponseDTO.class))
    )
    public ResponseEntity<List<DocumentoResponseDTO>> getAllDocumentos() {

        List<DocumentoResponseDTO> documentos = documentoService.getAllDocumento();
        return ResponseEntity.ok(documentos);
    }

    /**
     * Actualizar documento
     */
    @PatchMapping("/update/{idDocumento}")
    @Operation(
            summary = "Actualizar documento",
            description = "Actualiza los datos de un documento existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Documento actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = DocumentoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Documento no encontrado"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos"
            )
    })
    public ResponseEntity<DocumentoResponseDTO> updateDocumento(
            @Parameter(description = "ID del documento a actualizar", example = "5", required = true)
            @PathVariable Long idDocumento,

            @Parameter(description = "Datos a actualizar del documento", required = true)
            @RequestBody DocumentoUpdateDTO dto
    ) {

        DocumentoResponseDTO response = documentoService.updateDocumento(idDocumento, dto);
        return ResponseEntity.ok(response);
    }

    /**
     * Buscar documentos por usuario creador
     */
    @GetMapping("/creado")
    @Operation(
            summary = "Documentos por usuario creador",
            description = "Obtiene todos los documentos creados por un usuario específico"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Documentos encontrados",
            content = @Content(schema = @Schema(implementation = DocumentoResponseDTO.class))
    )
    public ResponseEntity<List<DocumentoResponseDTO>> getAllDocumentoByUsuarioCreador(
            @Parameter(description = "Correo del usuario creador", example = "usuario@correo.com", required = true)
            @RequestParam String usuario
    ) {

        List<DocumentoResponseDTO> documentos = documentoService.getAllDocumentoByUsuarioCreador(usuario);
        return ResponseEntity.ok(documentos);
    }

    /**
     * Buscar documento por nombre
     */
    @GetMapping("/nombre")
    @Operation(
            summary = "Buscar documento por nombre",
            description = "Obtiene un documento cuyo nombre coincida exactamente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Documento encontrado",
                    content = @Content(schema = @Schema(implementation = DocumentoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Documento no encontrado"
            )
    })
    public ResponseEntity<DocumentoResponseDTO> getDocumentoByNombre(
            @Parameter(description = "Nombre del documento", example = "Informe Epidemiológico", required = true)
            @RequestParam String nombre
    ) {

        DocumentoResponseDTO response = documentoService.getDocumentoByNombre(nombre);
        return ResponseEntity.ok(response);
    }

    /**
     * Buscar documentos por tipo
     */
    @GetMapping("/tipoDocumento")
    @Operation(
            summary = "Documentos por tipo",
            description = "Obtiene todos los documentos asociados a un tipo de documento"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Documentos encontrados",
            content = @Content(schema = @Schema(implementation = DocumentoResponseDTO.class))
    )
    public ResponseEntity<List<DocumentoResponseDTO>> getAllDocumentoByTipoDocumento(
            @Parameter(description = "Nombre del tipo de documento", example = "Resolución", required = true)
            @RequestParam String tipoDocumento
    ) {

        List<DocumentoResponseDTO> documentos = documentoService.getAllDocumentoByTipoDocumento(tipoDocumento);
        return ResponseEntity.ok(documentos);
    }

    /**
     * Buscar documentos por rango de fechas
     */
    @GetMapping("/fechaCreacion")
    @Operation(
            summary = "Documentos por rango de fechas",
            description = "Obtiene todos los documentos creados entre dos fechas"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Documentos encontrados",
                    content = @Content(schema = @Schema(implementation = DocumentoResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Fechas inválidas"
            )
    })
    public ResponseEntity<List<DocumentoResponseDTO>> getAllDocumentoByFechaCreacion(
            @Parameter(description = "Fecha inicial (YYYY-MM-DD)", example = "2024-01-01", required = true)
            @RequestParam LocalDate inicio,

            @Parameter(description = "Fecha final (YYYY-MM-DD)", example = "2024-12-31", required = true)
            @RequestParam LocalDate fin
    ) {

        List<DocumentoResponseDTO> documentos = documentoService.getAllDocumentoByFechaCreacion(inicio, fin);
        return ResponseEntity.ok(documentos);
    }
}
