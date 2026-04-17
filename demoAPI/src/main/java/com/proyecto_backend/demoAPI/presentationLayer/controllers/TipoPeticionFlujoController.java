package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoPeticionFlujoCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.TipoPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.ITipoPeticionFlujoService;
import com.proyecto_backend.demoAPI.exceptions.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/tipo-peticion")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "TipoPeticionFlujo", description = "Operaciones CRUD para Tipos de Petición")
@CrossOrigin(origins = "*")
public class TipoPeticionFlujoController {

    private final ITipoPeticionFlujoService tipoPeticionService;

    // CREATE
    @Operation(
            summary = "Crear nuevo Tipo de Petición",
            description = "Crea un nuevo Tipo de Petición con sus requerimientos asociados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tipo de petición creado",
                    content = @Content(schema = @Schema(implementation = TipoPeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "409", description = "Conflicto de datos",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping
    public ResponseEntity<TipoPeticionFlujoResponseDTO> createTipoPeticion(
            @Parameter(description = "Datos del Tipo de Petición a crear (id debe ser null)", required = true)
            @Valid @RequestBody TipoPeticionFlujoCreateUpdateDTO createDTO) {

        log.info("POST /tipo-peticion - Creando tipo de petición {}", createDTO.getNombre());
        TipoPeticionFlujoResponseDTO created = tipoPeticionService.createTipoPeticionFlujo(createDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // GET BY ID
    @Operation(
            summary = "Obtener Tipo de Petición por ID",
            description = "Obtiene un Tipo de Petición por su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de petición encontrado",
                    content = @Content(schema = @Schema(implementation = TipoPeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<TipoPeticionFlujoResponseDTO> getById(
            @Parameter(description = "ID del Tipo de Petición que desea buscar", required = true, example = "1")
            @PathVariable Long id) {

        log.info("GET /tipo-peticion/{} - Buscando tipo de petición por ID", id);
        return ResponseEntity.ok(tipoPeticionService.getTipoPeticionFlujoById(id));
    }

    // GET BY NOMBRE
    @Operation(
            summary = "Obtener Tipo de Petición por nombre",
            description = "Obtiene un Tipo de Petición por su nombre"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de petición encontrado",
                    content = @Content(schema = @Schema(implementation = TipoPeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<TipoPeticionFlujoResponseDTO> getByNombre(
            @Parameter(description = "Nombre del Tipo de Petición que desea buscar", required = true, example = "cancelación de materias")
            @PathVariable String nombre) {

        log.info("GET /tipo-peticion/nombre/{} - Buscando tipo de petición por nombre", nombre);
        return ResponseEntity.ok(tipoPeticionService.getTipoPeticionFlujoByNombre(nombre));
    }

    // GET ALL
    @Operation(
            summary = "Obtener todos los Tipos de Petición",
            description = "Lista todos los Tipos de Petición registrados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente",
                    content = @Content(schema = @Schema(implementation = TipoPeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping
    public ResponseEntity<List<TipoPeticionFlujoResponseDTO>> getAll() {

        log.info("GET /tipo-peticion - Listando todos los tipos de petición");
        return ResponseEntity.ok(tipoPeticionService.getAllTipoPeticionFlujos());
    }

    // UPDATE
    @Operation(
            summary = "Actualizar Tipo de Petición",
            description = "Actualiza un Tipo de Petición existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de petición actualizado",
                    content = @Content(schema = @Schema(implementation = TipoPeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "409", description = "Conflicto de datos",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<TipoPeticionFlujoResponseDTO> update(
            @Parameter(description = "ID del Tipo de Petición que desea actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos del Tipo de Petición a actualizar (id debe ser null)", required = true)
            @Valid @RequestBody TipoPeticionFlujoCreateUpdateDTO updateDTO) {

        log.info("PUT /tipo-peticion/{} - Actualizando tipo de petición {}", id, updateDTO.getNombre());
        return ResponseEntity.ok(tipoPeticionService.updateTipoPeticionFlujo(id, updateDTO));
    }

    // DELETE
    @Operation(
            summary = "Eliminar Tipo de Petición",
            description = "Elimina un Tipo de Petición por su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tipo de petición eliminado"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del Tipo de Petición que desea eliminar", required = true, example = "1")
            @PathVariable Long id) {

        log.info("DELETE /tipo-peticion/{} - Eliminando tipo de petición", id);
        tipoPeticionService.deleteTipoPeticionFlujo(id);
        return ResponseEntity.noContent().build();
    }
}