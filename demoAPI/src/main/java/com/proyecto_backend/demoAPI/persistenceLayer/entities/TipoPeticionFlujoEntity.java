package com.proyecto_backend.demoAPI.persistenceLayer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "tipo_peticion_flujo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoPeticionFlujoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_peticion_flujo")
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    private String instruccionesPdf;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tipo_peticion_flujo_requerimiento_peticion",
            joinColumns = @JoinColumn(name = "id_tipo_peticion_flujo"),
            inverseJoinColumns = @JoinColumn(name = "id_requerimiento_peticion")
    )
    private List<RequerimientoPeticionEntity> requerimientos;
}
