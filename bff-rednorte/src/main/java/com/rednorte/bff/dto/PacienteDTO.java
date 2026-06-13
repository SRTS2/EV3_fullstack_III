package com.rednorte.bff.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PacienteDTO {
    private Long id;
    private String nombre;
    private String rut;
    private String email;
    private String telefono;
    private String direccion;
    private LocalDate fechaNacimiento;
    private String contrasena;
}
