package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto_backend.demoAPI.businessLayer.dtos.OrganizacionCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.OrganizacionDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.OrganizacionUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IOrganizacionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/organizaciones")
@RequiredArgsConstructor
@Tag(name = "Organizaciones", description = "Operaciones CRUD para gestión de organizaciones")
@CrossOrigin(origins = "*")
public class OrganizacionController {

    private final IOrganizacionService organizacionService;

    @PostMapping
    @Operation(summary = "Crear organización", description = "Registra una nueva organización en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Organización creada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrganizacionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "Nombre ya registrado")
    })
    public ResponseEntity<OrganizacionDTO> guardarOrganizacion(
            @Parameter(description = "Datos de la organización a crear", required = true) @Valid @RequestBody OrganizacionCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(organizacionService.guardarOrganizacion(dto));
    }

    @GetMapping
    @Operation(summary = "Listar organizaciones", description = "Obtiene la lista completa de organizaciones registradas")
    @ApiResponse(responseCode = "200", description = "Lista de organizaciones obtenida exitosamente")
    public ResponseEntity<List<OrganizacionDTO>> listaOrganizaciones() {
        return ResponseEntity.ok(organizacionService.listaOrganizaciones());
    }

    @GetMapping("/id/{idOrganizacion}")
    @Operation(summary = "Buscar organización por ID", description = "Obtiene la información de una organización específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Organización encontrada"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Organización no encontrada")
    })
    public ResponseEntity<OrganizacionDTO> buscarOrganizacionPorId(
            @Parameter(description = "ID de la organización", required = true, example = "1") @PathVariable Long idOrganizacion) {
        return ResponseEntity.ok(organizacionService.buscarOrganizacionPorId(idOrganizacion));
    }

    @GetMapping("/nombre/{nombreOrganizacion}")
    @Operation(summary = "Buscar organización por nombre", description = "Obtiene una organización a partir de su nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Organización encontrada"),
            @ApiResponse(responseCode = "400", description = "Nombre inválido"),
            @ApiResponse(responseCode = "404", description = "Organización no encontrada")
    })
    public ResponseEntity<OrganizacionDTO> buscarOrganizacionPorNombre(
            @Parameter(description = "Nombre de la organización", required = true, example = "MiEmpresa") @PathVariable String nombreOrganizacion) {
        return ResponseEntity.ok(organizacionService.buscarOrganizacionPorNombre(nombreOrganizacion));
    }

    @PutMapping("/{idOrganizacion}")
    @Operation(summary = "Actualizar organización", description = "Actualiza la información de una organización existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Organización actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o ID inválido"),
            @ApiResponse(responseCode = "404", description = "Organización no encontrada"),
            @ApiResponse(responseCode = "409", description = "Nombre ya registrado")
    })
    public ResponseEntity<OrganizacionDTO> actualizarOrganizacion(
            @Parameter(description = "Datos actualizados de la organización", required = true) @Valid @RequestBody OrganizacionUpdateDTO dto,
            @Parameter(description = "ID de la organización a actualizar", required = true, example = "1") @PathVariable Long idOrganizacion) {
        return ResponseEntity.ok(organizacionService.actualizarOrganizacion(dto, idOrganizacion));
    }

    @DeleteMapping("/{idOrganizacion}")
    @Operation(summary = "Eliminar organización", description = "Elimina una organización del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Organización eliminada exitosamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Organización no encontrada")
    })
    public ResponseEntity<Void> eliminarOrganizacion(
            @Parameter(description = "ID de la organización a eliminar", required = true, example = "1") @PathVariable Long idOrganizacion) {
        organizacionService.eliminarOrganizacion(idOrganizacion);
        return ResponseEntity.noContent().build();
    }

}