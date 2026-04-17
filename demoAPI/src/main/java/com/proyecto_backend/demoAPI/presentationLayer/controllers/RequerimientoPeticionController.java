package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoPeticionCreateUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RequerimientoPeticionResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IRequerimientoPeticionService;
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
@RequestMapping("/requerimiento-peticion")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Requerimiento", description = "Operaciones CRUD para requerimientos")
@CrossOrigin(origins = "*")
public class RequerimientoPeticionController {

    private final IRequerimientoPeticionService requerimientoService;

    // CREATE
    @Operation(summary = "Crear requerimiento",
            description = "Crea un nuevo requerimiento")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Requerimiento creado",
                    content = @Content(schema = @Schema(implementation = RequerimientoPeticionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "409", description = "Conflicto de datos",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping
    public ResponseEntity<RequerimientoPeticionResponseDTO> create(
            @Parameter(description = "Datos del Requerimiento a crear (id debe ser null)", required = true)
            @Valid @RequestBody RequerimientoPeticionCreateUpdateDTO createDTO) {

        log.info("POST /requerimiento-peticion - Creando requerimiento {}", createDTO.getNombre());
        RequerimientoPeticionResponseDTO created = requerimientoService.createRequerimiento(createDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // GET BY ID
    @Operation(summary = "Obtener requerimiento por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Requerimiento encontrado",
                    content = @Content(schema = @Schema(implementation = RequerimientoPeticionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Requerimiento no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<RequerimientoPeticionResponseDTO> getById(
            @Parameter(description = "ID del Requerimiento que desea buscar", required = true, example = "1")
            @PathVariable Long id) {

        log.info("GET /requerimiento-peticion/{id} - Buscando requerimiento {}", id);
        return ResponseEntity.ok(requerimientoService.getRequerimientoById(id));
    }

    // GET BY NOMBRE
    @Operation(summary = "Obtener requerimiento por nombre")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Requerimiento encontrado",
                    content = @Content(schema = @Schema(implementation = RequerimientoPeticionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Nombre inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Requerimiento no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<RequerimientoPeticionResponseDTO> getByNombre(
            @Parameter(description = "Nombre del Requerimiento que desea buscar", required = true, example = "Necesita Firma del Remitente")
            @PathVariable String nombre) {

        log.info("GET /requerimiento/nombre/{nombre} - Buscando requerimiento {}", nombre);
        return ResponseEntity.ok(requerimientoService.getRequerimientoByNombre(nombre));
    }

    // GET ALL
    @Operation(summary = "Listar todos los requerimientos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido",
                    content = @Content(schema = @Schema(implementation = RequerimientoPeticionResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping
    public ResponseEntity<List<RequerimientoPeticionResponseDTO>> getAll() {

        log.info("GET /requerimiento-peticion - Listando requerimientos");
        return ResponseEntity.ok(requerimientoService.getAllRequerimientos());
    }

    // UPDATE
    @Operation(summary = "Actualizar requerimiento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Requerimiento actualizado",
                    content = @Content(schema = @Schema(implementation = RequerimientoPeticionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Requerimiento no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "409", description = "Conflicto de datos",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<RequerimientoPeticionResponseDTO> update(
            @Parameter(description = "ID del Requerimiento que desea actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos del Requerimiento a actualizar (id debe ser null)", required = true)
            @Valid @RequestBody RequerimientoPeticionCreateUpdateDTO updateDTO) {

        log.info("PUT /requerimiento-peticion/{id} - Actualizando requerimiento {}", id);
        return ResponseEntity.ok(requerimientoService.updateRequerimiento(id, updateDTO));
    }

    // DELETE
    @Operation(summary = "Eliminar requerimiento",
            description = "Elimina un requerimiento de petición con el Id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Requerimiento eliminado"),
            @ApiResponse(responseCode = "404", description = "Requerimiento no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del Requerimiento que desea eliminar", required = true, example = "1")
            @PathVariable Long id) {

        log.info("DELETE /requerimiento/{id} - Eliminando requerimiento {}", id);
        requerimientoService.deleteRequerimiento(id);
        return ResponseEntity.noContent().build();
    }
}
