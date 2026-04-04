package com.proyecto_backend.demoAPI.persistenceLayer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "requerimiento_peticion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequerimientoPeticionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_requerimiento_peticion")
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @ManyToMany(mappedBy = "requerimientos", fetch = FetchType.LAZY)
    private List<TipoPeticionFlujoEntity> tipoPeticiones;
}
