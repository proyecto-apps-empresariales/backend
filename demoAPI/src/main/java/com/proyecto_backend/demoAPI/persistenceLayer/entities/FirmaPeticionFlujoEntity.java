package com.proyecto_backend.demoAPI.persistenceLayer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "firma_peticion_flujo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirmaPeticionFlujoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_firma_peticion_flujo")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_peticion_flujo")
    private PeticionFlujoEntity peticion;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaFirma;

    @Column(nullable = false)
    private String observacion;

}
