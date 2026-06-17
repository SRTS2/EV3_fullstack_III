package com.rednorte.auth.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String rut;

    @Column(nullable = false)
    private String nombre;

    private String email;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private String role = "PACIENTE";

    @Column(nullable = false)
    private Boolean activo = true;

    private Long pacienteId;
}
