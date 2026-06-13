package com.rednorte.bff.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReasignacionDTO {

    private Long id;
    private Long pacienteId;
    private String nombrePaciente;
    private String especialidad;
    private String fechaOriginal;
    private String fechaReasignada;
    private String estado;
    private String motivoCancelacion;
}
