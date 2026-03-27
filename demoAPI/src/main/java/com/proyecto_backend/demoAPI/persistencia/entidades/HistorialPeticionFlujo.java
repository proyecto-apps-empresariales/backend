package com.proyecto_backend.demoAPI.persistencia.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "historial_peticion_flujo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistorialPeticionFlujo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial_peticion_flujo")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_peticion_flujo", nullable = false)
    private PeticionFLujo peticion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuarioEditor;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "descripcion_accion", nullable = false)
    private String descripcion;

}
