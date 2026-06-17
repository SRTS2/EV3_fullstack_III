package com.rednorte.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "El RUT es obligatorio")
    @Pattern(regexp = "^\\d{1,2}\\.?\\d{3}\\.?\\d{3}[-]?[\\dKk]$", message = "Formato de RUT invalido")
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @Email(message = "Debe proporcionar un email valido")
    private String email;

    private String role;
    private Boolean activo;
    private Long pacienteId;
}
