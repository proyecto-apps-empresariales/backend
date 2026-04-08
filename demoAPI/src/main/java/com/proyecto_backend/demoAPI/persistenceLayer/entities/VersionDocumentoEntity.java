package com.proyecto_backend.demoAPI.persistenceLayer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionDocumentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_version_documento")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_documento", nullable = false)
    private DocumentoEntity documento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_actualizador", nullable = false)
    private Usuario usuarioActualizador;

    @Column(name="nombre_version", nullable = false)
    private String nombre;

    @Column(name="archivo_url", nullable = false)
    private String archivoUrl;

    @Column(name="descripcion_actualizacion", nullable = false)
    private String descripcion;

    @Column(name="fecha_actualizacion", nullable = false)
    @CreationTimestamp
    private LocalDate fechaActualizacion;
}
