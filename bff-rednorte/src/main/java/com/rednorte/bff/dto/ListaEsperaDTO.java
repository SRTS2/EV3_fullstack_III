package com.rednorte.bff.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ListaEsperaDTO {

    private Long id;
    private Long pacienteId;
    private String nombrePaciente;
    private String rutPaciente;
    private String especialidad;
    private String prioridad;
    private String estado;
    private String fechaIngreso;
    private String tiempoEstimado;
    private String observaciones;
}
