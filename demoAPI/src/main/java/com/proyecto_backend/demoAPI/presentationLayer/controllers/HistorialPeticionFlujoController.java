package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.HistorialPeticionFlujoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IHistorialPeticionFlujoService;
import com.proyecto_backend.demoAPI.exceptions.ApiError;
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
@RequestMapping("/historial")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "HistorialPetición", description = "Operaciones CRUD para Hisorial de peticiones")
@CrossOrigin(origins = "*")
public class HistorialPeticionFlujoController {

    private final IHistorialPeticionFlujoService historialService;


    // CREAR HISTORIAL (uso interno sistema)

//    @PostMapping
//    @Operation(
//            summary = "Crear registro de historial",
//            description = "Crea automáticamente un registro de historial cuando cambia el estado de una petición. Uso interno del sistema."
//    )
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Historial creado exitosamente"),
//            @ApiResponse(responseCode = "400", description = "Datos inválidos para crear historial"),
//            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
//    })
//    public ResponseEntity<HistorialPeticionFlujoResponseDTO> createHistorial(
//            @Valid @RequestBody HistorialPeticionFlujoEntity entity
//    ) {
//        log.info("POST /historial - Creando registro de historial");
//
//        HistorialPeticionFlujoResponseDTO created = historialService.createHistorial(entity);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(created);
//    }

    // Obtener historial por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener registro de historial por ID",
            description = "Obtiene un registro de historial con el Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historial encontrado",
                    content = @Content(schema = @Schema(implementation = HistorialPeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Historial no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<HistorialPeticionFlujoResponseDTO> getHistorialById(
            @Parameter(description = "ID del registro de historial que desea buscar", required = true, example = "1")
            @PathVariable Long id) {

        log.info("GET /historial/{id} - Buscando historial por ID {}", id);
        return ResponseEntity.ok(historialService.getHistorialById(id));
    }


    // Historial completo por ID de petición
    @GetMapping("/peticion/{id}")
    @Operation(summary = "Obtener historial completo por ID de petición",
            description = "Obtiene el historial por Id de la petición dueña del historial")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historial encontrado",
                    content = @Content(schema = @Schema(implementation = HistorialPeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<List<HistorialPeticionFlujoResponseDTO>> getHistorialByPeticionId(
            @Parameter(description = "ID la petición para buscar el hitorial completo", required = true, example = "1")
            @PathVariable Long id) {

        log.info("GET /historial/peticion/{idpeticion} - Historial completo por ID petición {}", id);
        return ResponseEntity.ok(historialService.getHistorialCompletoByPeticionId(id));
    }


    // Historial completo por nombre de petición
    @GetMapping("/peticion/nombre/{nombre}")
    @Operation(summary = "Obtener historial completo por nombre de petición",
            description = "Obtiene el historial por nombre de la petición dueña del historial")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historial encontrado",
                    content = @Content(schema = @Schema(implementation = HistorialPeticionFlujoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Nombre inválido",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<List<HistorialPeticionFlujoResponseDTO>> getHistorialByPeticionNombre(
            @Parameter(description = "Nombre la petición para buscar el hitorial completo", required = true, example = "Cancelación de materia")
            @PathVariable String nombre) {

        log.info("GET /historial/peticion/nombre/{nombrepeticion} - Historial completo por nombre {}", nombre);
        return ResponseEntity.ok(historialService.getHistorialCompletoByPeticionNombre(nombre));
    }
}
