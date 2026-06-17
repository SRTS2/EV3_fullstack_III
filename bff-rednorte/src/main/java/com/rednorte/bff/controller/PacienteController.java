package com.rednorte.bff.controller;

import com.rednorte.bff.client.PacientesClient;
import com.rednorte.bff.dto.PacienteDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/pacientes")
public class PacienteController {

    private final PacientesClient pacientesClient;

    public PacienteController(PacientesClient pacientesClient) {
        this.pacientesClient = pacientesClient;
    }

    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listarTodos() {
        return ResponseEntity.ok(pacientesClient.listarPacientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pacientesClient.getPaciente(id));
    }

    @PostMapping
    public ResponseEntity<PacienteDTO> crear(@RequestBody PacienteDTO paciente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pacientesClient.crearPaciente(paciente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> actualizar(@PathVariable Long id, @RequestBody PacienteDTO paciente) {
        return ResponseEntity.ok(pacientesClient.actualizarPaciente(id, paciente));
    }
}
