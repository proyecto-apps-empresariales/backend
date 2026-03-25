package com.proyecto_backend.persistencia.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "requerimiento_peticion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoPeticionFlujo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_requerimiento_peticion")
    private Long id;
    private String nombre;
    private String descripcion;
    @Column(name = "instrucciones_pdf")
    private String instruccionesPdf;
}
