package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto_backend.demoAPI.businessLayer.dtos.PermisoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PermisoDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.PermisoUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IPermisoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/permisos")
@RequiredArgsConstructor
public class PermisoController {

    // Inyectamos el servicio correspondiente:
    private final IPermisoService permisoService;

    // Endpoint para guardar un permiso:
    @PostMapping
    public ResponseEntity<PermisoDTO> guardarPermiso (@Valid @RequestBody PermisoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(permisoService.guardarPermiso(dto));
    }

    // Endpoint para retornar la lista de permisos:
    @GetMapping
    public ResponseEntity<List<PermisoDTO>> listaPermisos() {
        return ResponseEntity.ok(permisoService.listaPermisos());
    }

    // Endpoint para buscar un permiso por id:
    @GetMapping("/{idPermiso}")
    public ResponseEntity<PermisoDTO> buscarPermisoPorId (@PathVariable Long idPermiso) {
        return ResponseEntity.ok(permisoService.buscarPorId(idPermiso));
    }

    // Endpoint para buscar un permiso por nombre:
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<PermisoDTO> buscarPermisoPorNombre (@PathVariable String nombre) {
        return ResponseEntity.ok(permisoService.buscarPorNombre(nombre));
    }
    
    // Endpoint para actualizar un permiso:
    @PutMapping("/{idPermiso}")
    public ResponseEntity<PermisoDTO> actualizarPermiso (@Valid @RequestBody PermisoUpdateDTO dto, @PathVariable Long idPermiso) {
        return ResponseEntity.ok(permisoService.actualizarPermiso(dto, idPermiso));
    }

    // Endpoint para eliminar un permiso:
    @DeleteMapping("/{idPermiso}")
    public ResponseEntity<Void> eliminarPermiso (@PathVariable Long idPermiso) {
        permisoService.eliminarPermiso(idPermiso);
        return ResponseEntity.noContent().build();
    }
}
