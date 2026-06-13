package com.rednorte.pacientes.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginPacienteDTO {

    @NotBlank(message = "El RUT es obligatorio")
    private String rut;

    @NotBlank(message = "La contrasena es obligatoria")
    private String contrasena;

    public LoginPacienteDTO() {}

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}
