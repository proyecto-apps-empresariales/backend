package com.proyecto_backend.demoAPI.businessLayer.controladores;

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

import com.proyecto_backend.demoAPI.businessLayer.dtos.RolCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RolDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.RolUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.RolService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RolController {

    // Creamos las instancias de los servicios correspondientes:
    private final RolService rolService;

    // Endpoint para guardar un rol:
    @PostMapping
    public ResponseEntity<RolDTO> guardarRol(@Valid @RequestBody RolCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rolService.guardarRol(dto));
    }

    // Endpoint para retornar la lista de roles:
    @GetMapping
    public ResponseEntity<List<RolDTO>> listaRoles() {
        return ResponseEntity.ok(rolService.listaRoles());
    }

    // Endpoint para buscar un rol por id:
    @GetMapping("/{idRol}")
    public ResponseEntity<RolDTO> buscarRolPorId(@PathVariable Long idRol) {
        return ResponseEntity.ok(rolService.buscarRolPorId(idRol));
    }

    // Endpoint para buscar un rol por nombre:
    @GetMapping("/nombre/{nombreRol}")
    public ResponseEntity<RolDTO> buscarRolPorNombre(@PathVariable String nombreRol) {
        return ResponseEntity.ok(rolService.buscarRolPorNombre(nombreRol));
    }

    // Endpoint para actualizar un rol:
    @PutMapping("/{idRol}")
    public ResponseEntity<RolDTO> actualizarRol (@Valid @RequestBody RolUpdateDTO dto, @PathVariable Long idRol) {
        return ResponseEntity.ok(rolService.actualizarRol(dto, idRol));
    }

    // Endopoint para eliminar un rol:
    @DeleteMapping("/{idRol}")
    public ResponseEntity<Void> eliminarRol (@PathVariable Long idRol) {
        rolService.eliminarRol(idRol);
        return ResponseEntity.noContent().build();
    }
}
