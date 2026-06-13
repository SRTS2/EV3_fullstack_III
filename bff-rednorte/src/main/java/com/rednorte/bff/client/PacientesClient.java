package com.rednorte.bff.client;

import com.rednorte.bff.dto.PacienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "ms-pacientes", url = "${ms.pacientes.url:http://localhost:8083}")
public interface PacientesClient {

    @GetMapping("/api/v1/pacientes/{id}")
    PacienteDTO getPaciente(@PathVariable("id") Long id);

    @GetMapping("/api/v1/pacientes")
    List<PacienteDTO> listarPacientes();

    @PostMapping("/api/v1/pacientes")
    PacienteDTO crearPaciente(@RequestBody PacienteDTO paciente);

    @PostMapping("/api/v1/pacientes/login")
    PacienteDTO login(@RequestBody Map<String, String> credenciales);

    @PutMapping("/api/v1/pacientes/{id}")
    PacienteDTO actualizarPaciente(@PathVariable("id") Long id, @RequestBody PacienteDTO paciente);
}
