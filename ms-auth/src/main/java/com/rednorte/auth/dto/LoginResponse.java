package com.rednorte.auth.dto;

public class LoginResponse {

    private String token;
    private String role;
    private Long pacienteId;

    public LoginResponse() {}

    public LoginResponse(String token, String role, Long pacienteId) {
        this.token = token;
        this.role = role;
        this.pacienteId = pacienteId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }
}
