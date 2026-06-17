package com.rednorte.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "El RUT es obligatorio")
    private String rut;

    @NotBlank(message = "La contrasena es obligatoria")
    private String contrasena;
}
