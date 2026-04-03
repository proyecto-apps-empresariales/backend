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

import com.proyecto_backend.demoAPI.businessLayer.dtos.OrganizacionCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.OrganizacionDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.OrganizacionUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.OrganizacionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/organizaciones")
@RequiredArgsConstructor
public class OrganizacionController {

    // Creamos las instancias de los servicios correspondientes:
    private final OrganizacionService organizacionService;

    // Endpoint para guardar una organizacion:
    @PostMapping
    public ResponseEntity<OrganizacionDTO> guardarOrganizacion(@Valid @RequestBody OrganizacionCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(organizacionService.guardarOrganizacion(dto));
    }

    // Endpoint para retornar la lista de organizaciones:
    @GetMapping
    public ResponseEntity<List<OrganizacionDTO>> listaOrganizaciones() {
        return ResponseEntity.ok(organizacionService.listaOrganizaciones());
    }

    // Endpoint para buscar una organizacion por id:
    @GetMapping("/{idOrganizacion}")
    public ResponseEntity<OrganizacionDTO> buscarOrganizacionPorId(@PathVariable Long idOrganizacion) {
        return ResponseEntity.ok(organizacionService.buscarOrganizacionPorId(idOrganizacion));
    }

    // Endpoint para buscar una organizacion por nombre:
    @GetMapping("/nombre/{nombreOrganizacion}")
    public ResponseEntity<OrganizacionDTO> buscarOrganizacionPorNombre(@PathVariable String nombreOrganizacion) {
        return ResponseEntity.ok(organizacionService.buscarOrganizacionPorNombre(nombreOrganizacion));
    }

    // Endpoint para actualizar una organizacion:
    @PutMapping("/{idOrganizacion}")
    public ResponseEntity<OrganizacionDTO> actualizarOrganizacion(@Valid @RequestBody OrganizacionUpdateDTO dto,
            @PathVariable Long idOrganizacion) {
        return ResponseEntity.ok(organizacionService.actualizarOrganizacion(dto, idOrganizacion));
    }

    // Endopoint para eliminar una organizacion:
    @DeleteMapping("/{idOrganizacion}")
    public ResponseEntity<Void> eliminarOrganizacion (@PathVariable Long idOrganizacion) {
        organizacionService.eliminarOrganizacion(idOrganizacion);
        return ResponseEntity.noContent().build();
    }

}
