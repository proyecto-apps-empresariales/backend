package com.proyecto_backend.demoAPI.persistenceLayer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="plantilla_documento")
@AllArgsConstructor
@NoArgsConstructor
public class PlantillaDocumentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_plantilla_documento")
    private Long id;

    @Column(name="archivo_pdf",nullable = false)
    private String archivoUrl;

    @Column(name="descripcion",nullable = false)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_documento", nullable = false)
    private TipoDocumentoEntity tipoDocumento;
}
