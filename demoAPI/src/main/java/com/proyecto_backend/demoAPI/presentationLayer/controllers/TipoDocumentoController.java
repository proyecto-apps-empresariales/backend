package com.proyecto_backend.demoAPI.presentationLayer.controllers;

import com.proyecto_backend.demoAPI.businessLayer.dtos.*;
import com.proyecto_backend.demoAPI.businessLayer.services.ITipoDocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipoDocumento")
@RequiredArgsConstructor
public class TipoDocumentoController {

    private final ITipoDocumentoService tipoDocumentoService;

    //Endpoint para crear tipo documento
    @PostMapping("/crear")
    public ResponseEntity<TipoDocumentoResponseDTO> createTipoDocumento(@RequestBody TipoDocumentoCreateUpdateDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoDocumentoService.createTipoDocumento(dto));
    }

    //Endpoint para obtener tipo de documento por id
    @GetMapping("/{idTipoDocumento}")
    public ResponseEntity<TipoDocumentoResponseDTO> getTipoDocumentoById(@PathVariable Long idTipoDocumento){
        return ResponseEntity.ok(tipoDocumentoService.getTipoDocumentoByID(idTipoDocumento));
    }

    //Endpoint para obtener todos los tipos de documentos
    @GetMapping
    public ResponseEntity<List<TipoDocumentoResponseDTO>> getAllTipoDocumentos(){
        return ResponseEntity.ok(tipoDocumentoService.getAllTipoDocumento());
    }

    //Endpoint para editar tipo documento
    @PatchMapping("/update/{idTipoDocumento}")
    public ResponseEntity<TipoDocumentoResponseDTO> updateDocumento(@PathVariable Long idTipoDocumento,@RequestBody TipoDocumentoCreateUpdateDTO dto){
        return ResponseEntity.ok(tipoDocumentoService.updateTipoDocumento(idTipoDocumento,dto));
    }

}
