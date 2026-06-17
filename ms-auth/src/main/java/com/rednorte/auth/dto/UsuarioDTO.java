package com.rednorte.auth.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UsuarioDTO {

    private Long id;
    private String rut;
    private String nombre;
    private String email;
    private String role;
    private Boolean activo;
    private Long pacienteId;
}
