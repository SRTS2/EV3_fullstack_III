package com.rednorte.reasignacion.dto;

import com.rednorte.reasignacion.model.enums.EstadoReasignacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReasignacionDTO {

    private Long id;

    @NotNull(message = "El ID del paciente es obligatorio")
    private Long pacienteId;

    @NotBlank(message = "El nombre del paciente es obligatorio")
    private String nombrePaciente;

    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;

    private LocalDateTime fechaOriginal;

    private LocalDateTime fechaReasignada;

    private EstadoReasignacion estado;

    private String motivoCancelacion;
}
