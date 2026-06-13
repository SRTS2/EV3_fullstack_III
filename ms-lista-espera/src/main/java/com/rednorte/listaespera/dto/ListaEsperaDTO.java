package com.rednorte.listaespera.dto;

import com.rednorte.listaespera.model.enums.EstadoEspera;
import com.rednorte.listaespera.model.enums.Prioridad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ListaEsperaDTO {

    private Long id;

    private Long pacienteId;

    @NotBlank(message = "El nombre del paciente es obligatorio")
    private String nombrePaciente;

    @NotBlank(message = "El RUT del paciente es obligatorio")
    private String rutPaciente;

    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;

    @NotNull(message = "La prioridad es obligatoria")
    private Prioridad prioridad;

    private EstadoEspera estado;

    private LocalDateTime fechaIngreso;

    private Integer tiempoEstimadoDias;

    @Size(max = 500, message = "Las observaciones no pueden superar 500 caracteres")
    private String observaciones;
}
