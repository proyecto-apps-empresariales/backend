package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.DocumentoCreateDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.DocumentoResponseDTO;
import com.proyecto_backend.demoAPI.businessLayer.dtos.DocumentoUpdateDTO;
import com.proyecto_backend.demoAPI.businessLayer.services.IDocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/documentos")
@RequiredArgsConstructor
public class DocumentoController {

    private final IDocumentoService documentoService;

    //Endpoint para crear documento
    @PostMapping("/crear")
    public ResponseEntity<DocumentoResponseDTO> createDocumento(@RequestBody DocumentoCreateDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(documentoService.createDocumento(dto));
    }

    //Endpoint para obtener documento por id
    @GetMapping("/{idDocumento}")
    public ResponseEntity<DocumentoResponseDTO> getDocumentoById(@PathVariable Long idDocumento){
        return ResponseEntity.ok(documentoService.getDocumentoById(idDocumento));
    }

    //Endpoint para obtener todos los documentos
    @GetMapping
    public ResponseEntity<List<DocumentoResponseDTO>> getAllDocumentos(){
        return ResponseEntity.ok(documentoService.getAllDocumento());
    }

    //Endpoint para editar documento
    @PatchMapping("/update/{idDocumento}")
    public ResponseEntity<DocumentoResponseDTO> updateDocumento(@PathVariable Long idDocumento,@RequestBody DocumentoUpdateDTO dto){
        return ResponseEntity.ok(documentoService.updateDocumento(idDocumento,dto));
    }

    //Endpoint para buscar todos los documento creados por un usuario
    @GetMapping("/creado")
    public ResponseEntity<List<DocumentoResponseDTO>> getAllDocumentoByUsuarioCreador(@RequestParam String usuario){
        return ResponseEntity.ok(documentoService.getAllDocumentoByUsuarioCreador(usuario));
    }

    //Endpoint para buscar documento por nombre
    @GetMapping("/nombre")
    public ResponseEntity<DocumentoResponseDTO> getDocumentoByNombre(@RequestParam String nombre){
        return ResponseEntity.ok(documentoService.getDocumentoByNombre(nombre));
    }

    //Endpoint para buscar todos los documento por tipo de documento
    @GetMapping("/tipoDocumento")
    public ResponseEntity<List<DocumentoResponseDTO>> getAllDocumentoByTipoDocumento(@RequestParam String tipoDocumento){
        return ResponseEntity.ok(documentoService.getAllDocumentoByTipoDocumento(tipoDocumento));
    }

    //Endpoint para buscar todos los documento por fecha de creacion
    @GetMapping("/fechaCreacion")
    public ResponseEntity<List<DocumentoResponseDTO>> getAllDocumentoByFechaCreacion(@RequestParam LocalDate inicio, @RequestParam LocalDate fin ){
        return ResponseEntity.ok(documentoService.getAllDocumentoByFechaCreacion(inicio,fin));
    }

}
