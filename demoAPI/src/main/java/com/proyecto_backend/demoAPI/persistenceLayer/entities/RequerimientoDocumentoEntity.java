package com.proyecto_backend.demoAPI.persistenceLayer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name="requerimiento")
@AllArgsConstructor
@NoArgsConstructor
public class RequerimientoDocumentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_requerimiento")
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @ManyToMany(mappedBy = "requerimientos", fetch = FetchType.LAZY)
    private List<TipoDocumentoEntity> tipoDocumentos;
}
