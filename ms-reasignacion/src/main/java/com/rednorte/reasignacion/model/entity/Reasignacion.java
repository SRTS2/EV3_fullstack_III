package com.rednorte.reasignacion.model.entity;

import com.rednorte.reasignacion.model.enums.EstadoReasignacion;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reasignaciones")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Reasignacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pacienteId;

    private String nombrePaciente;

    private String especialidad;

    private LocalDateTime fechaOriginal;

    private LocalDateTime fechaReasignada;

    @Enumerated(EnumType.STRING)
    private EstadoReasignacion estado;

    private String motivoCancelacion;
}
