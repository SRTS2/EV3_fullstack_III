package com.rednorte.listaespera.model.entity;

import com.rednorte.listaespera.model.enums.EstadoEspera;
import com.rednorte.listaespera.model.enums.Prioridad;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pacientes_espera")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PacienteEspera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pacienteId;

    private String nombrePaciente;

    private String rutPaciente;

    private String especialidad;

    @Enumerated(EnumType.STRING)
    private Prioridad prioridad;

    @Enumerated(EnumType.STRING)
    private EstadoEspera estado;

    private LocalDateTime fechaIngreso;

    private Integer tiempoEstimadoDias;

    @Column(length = 500)
    private String observaciones;
}
