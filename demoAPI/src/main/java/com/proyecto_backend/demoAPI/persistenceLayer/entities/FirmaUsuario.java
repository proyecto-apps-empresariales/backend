package com.proyecto_backend.demoAPI.persistenceLayer.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "firma_usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FirmaUsuario {
    
    // Atributos de la clase FirmaUsuario:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_firma")
    private Long idFirma;
    @Column(name = "archivo_firma", nullable = false, unique = true)
    private String archivoFirma;
    @Column(name = "fecha", nullable = false, updatable = false)
    private LocalDate fecha;
    @Column(name = "descripcion")
    private String descripcion;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
