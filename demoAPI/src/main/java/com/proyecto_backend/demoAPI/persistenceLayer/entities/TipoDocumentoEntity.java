package com.proyecto_backend.demoAPI.persistenceLayer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="tipo_documento")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoDocumentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_tipo_documento")
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tipo_documento_requerimiento",
            joinColumns = @JoinColumn(name = "id_tipo_documento"),
            inverseJoinColumns = @JoinColumn(name = "id_requerimiento")
    )
    private List<RequerimientoDocumentoEntity> requerimientos;
}
