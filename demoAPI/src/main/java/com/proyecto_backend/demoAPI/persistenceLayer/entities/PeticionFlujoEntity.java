package com.proyecto_backend.demoAPI.persistenceLayer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "peticion_flujo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeticionFlujoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_peticion_flujo")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario_remitente")
    private Usuario remitente;

    @ManyToOne
    @JoinColumn(name = "id_usuario_destinatario")
    private Usuario destinatario;

//    @OneToOne
//    @JoinColumn(name = "id_documento")
//    private Documento documento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_peticion_flujo)", nullable = false)
    private TipoPeticionFlujoEntity tipoPeticion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado_peticion_flujo", nullable = false)
    private EstadoPeticionFlujoEntity estado;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String nombre;

}
