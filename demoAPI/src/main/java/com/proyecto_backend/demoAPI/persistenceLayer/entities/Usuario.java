package com.proyecto_backend.demoAPI.persistenceLayer.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    // Atributos de la clase Usuario:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;
    @Column(nullable = false, name = "nombre")
    private String nombre;
    @Column(nullable = false, name = "apellido")
    private String apellido;
    @Column(nullable = false, unique = true, name = "correo")
    private String correo;
    @Column(nullable = false, name = "contrasena_hash")
    private String contrasenaHash;
    @Column(name = "celular")
    private String celular;
    @Column(nullable = false, name = "fecha_creacion", updatable = false)
    private LocalDate fechaCreacion;
    @Column(name = "esta_activo")
    private Boolean estaActivo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_organizacion")
    private Organizacion organizacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol")
    private Rol rol;

}
