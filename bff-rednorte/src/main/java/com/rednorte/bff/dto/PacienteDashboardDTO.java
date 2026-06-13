package com.rednorte.bff.dto;

import lombok.Data;

import java.util.List;

@Data
public class PacienteDashboardDTO {
    private PacienteDTO paciente;
    private ListaEsperaDTO listaEspera;
    private List<ReasignacionDTO> reasignaciones;
}
