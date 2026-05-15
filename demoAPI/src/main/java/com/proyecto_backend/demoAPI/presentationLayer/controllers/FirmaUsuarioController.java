package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.FirmaUsuarioUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IFirmaUsuarioService;

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
@RequestMapping("/firmaUsuario")
@RequiredArgsConstructor
@Tag(name = "Firmas de Usuario", description = "Operaciones CRUD para gestión de firmas de usuario")
@CrossOrigin(origins = "*")
public class FirmaUsuarioController {

    private final IFirmaUsuarioService firmaUsuarioService;

    @PostMapping
    @Operation(summary = "Crear firma de usuario", description = "Registra una nueva firma asociada a un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Firma creada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FirmaUsuarioDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "Archivo de firma ya registrado")
    })
    public ResponseEntity<FirmaUsuarioDTO> guardarFirmaUsuario(
            @Parameter(description = "Datos de la firma a crear", required = true) @Valid @RequestBody FirmaUsuarioCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(firmaUsuarioService.guardarFirmaUsuario(dto));
    }

    @GetMapping
    @Operation(summary = "Listar firmas", description = "Obtiene la lista completa de firmas registradas")
    @ApiResponse(responseCode = "200", description = "Lista de firmas obtenida exitosamente")
    public ResponseEntity<List<FirmaUsuarioDTO>> listaFirmas() {
        return ResponseEntity.ok(firmaUsuarioService.listaFirmas());
    }

    @GetMapping("/usuario/{idUsuario}")
    @Operation(summary = "Listar firmas por usuario", description = "Obtiene la lista de firmas asociadas a un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido")
    })
    public ResponseEntity<List<FirmaUsuarioDTO>> listaFirmasPorUsuario(
            @Parameter(description = "ID del usuario", required = true, example = "5") @PathVariable Long idUsuario) {
        return ResponseEntity.ok(firmaUsuarioService.listaFirmasPorUsuario(idUsuario));
    }

    @GetMapping("/{idFirma}")
    @Operation(summary = "Buscar firma por ID", description = "Obtiene la información de una firma específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Firma encontrada"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Firma no encontrada")
    })
    public ResponseEntity<FirmaUsuarioDTO> firmaUsuarioPorIdFirma(
            @Parameter(description = "ID de la firma", required = true, example = "1") @PathVariable Long idFirma) {
        return ResponseEntity.ok(firmaUsuarioService.buscarFirmaPorIdFirma(idFirma));
    }

    @GetMapping("/archivoFirma/{archivoFirma}")
    @Operation(summary = "Buscar firma por archivo", description = "Obtiene una firma a partir del nombre del archivo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Firma encontrada"),
            @ApiResponse(responseCode = "400", description = "Archivo inválido"),
            @ApiResponse(responseCode = "404", description = "Firma no encontrada")
    })
    public ResponseEntity<FirmaUsuarioDTO> firmaUsuarioPorArchivoFirma(
            @Parameter(description = "Nombre del archivo de la firma", required = true, example = "firma.png") @PathVariable String archivoFirma) {
        return ResponseEntity.ok(firmaUsuarioService.buscarFirmaPorArchivoFirma(archivoFirma));
    }

    @PutMapping("/{idFirma}")
    @Operation(summary = "Actualizar firma", description = "Actualiza la información de una firma existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Firma actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o ID inválido"),
            @ApiResponse(responseCode = "404", description = "Firma no encontrada"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "409", description = "Archivo de firma ya registrado")
    })
    public ResponseEntity<FirmaUsuarioDTO> actualizarFirma(
            @Parameter(description = "Datos actualizados de la firma", required = true) @Valid @RequestBody FirmaUsuarioUpdateDTO dto,
            @Parameter(description = "ID de la firma a actualizar", required = true, example = "1") @PathVariable Long idFirma) {
        return ResponseEntity.ok(firmaUsuarioService.actualizarFirmaUsuario(dto, idFirma));
    }

    @DeleteMapping("/{idFirma}")
    @Operation(summary = "Eliminar firma", description = "Elimina una firma del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Firma eliminada exitosamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Firma no encontrada")
    })
    public ResponseEntity<Void> eliminarFirma(
            @Parameter(description = "ID de la firma a eliminar", required = true, example = "1") @PathVariable Long idFirma) {
        firmaUsuarioService.eliminarFirmaUsuario(idFirma);
        return ResponseEntity.noContent().build();
    }

}