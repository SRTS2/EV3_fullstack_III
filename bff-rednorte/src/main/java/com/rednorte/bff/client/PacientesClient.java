package com.rednorte.bff.client;

import com.rednorte.bff.dto.PacienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ms-pacientes", url = "${ms.pacientes.url:http://localhost:8083}")
public interface PacientesClient {

    @GetMapping("/api/v1/pacientes")
    List<PacienteDTO> listarPacientes();

    @GetMapping("/api/v1/pacientes/{id}")
    PacienteDTO getPaciente(@PathVariable("id") Long id);

    @GetMapping("/api/v1/pacientes/rut/{rut}")
    PacienteDTO obtenerPorRut(@PathVariable("rut") String rut);

    @PostMapping("/api/v1/pacientes")
    PacienteDTO crearPaciente(@RequestBody PacienteDTO paciente);

    @PutMapping("/api/v1/pacientes/{id}")
    PacienteDTO actualizarPaciente(@PathVariable("id") Long id, @RequestBody PacienteDTO paciente);

    @PatchMapping("/api/v1/pacientes/{id}")
    PacienteDTO actualizarParcial(@PathVariable("id") Long id, @RequestBody PacienteDTO paciente);

    @DeleteMapping("/api/v1/pacientes/{id}")
    void desactivar(@PathVariable("id") Long id);
}
