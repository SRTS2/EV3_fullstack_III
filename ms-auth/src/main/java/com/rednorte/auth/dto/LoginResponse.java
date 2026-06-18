package com.rednorte.auth.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LoginResponse {

    private String token;
    private String role;
    private Long usuarioId;
    private Long pacienteId;
    private String nombre;
}
