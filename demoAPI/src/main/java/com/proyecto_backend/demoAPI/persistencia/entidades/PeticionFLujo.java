package com.proyecto_backend.demoAPI.persistencia.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "peticion_flujo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeticionFLujo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_peticion_flujo")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario_remitente")
    private Usuario remitente;

    @ManyToOne
    @JoinColumn(name = "id_usuario_destinatario")
    private Usuario destionatario;

//    @OneToOne
//    @JoinColumn(name = "id_documento")
//    private Documento documento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_peticion_flujo)", nullable = false)
    private TipoPeticionFlujo tipoPeticion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado_peticion_flujo", nullable = false)
    private EstadoPeticionFlujo estado;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String nombre;

}
