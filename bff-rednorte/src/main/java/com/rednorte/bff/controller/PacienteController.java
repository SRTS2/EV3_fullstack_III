package com.rednorte.bff.controller;

import com.rednorte.bff.client.PacientesClient;
import com.rednorte.bff.dto.PacienteDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/rut/{rut}")
    public ResponseEntity<PacienteDTO> obtenerPorRut(@PathVariable String rut) {
        return ResponseEntity.ok(pacientesClient.obtenerPorRut(rut));
    }

    @PostMapping
    public ResponseEntity<PacienteDTO> crear(@Valid @RequestBody PacienteDTO paciente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pacientesClient.crearPaciente(paciente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> actualizar(@PathVariable Long id, @Valid @RequestBody PacienteDTO paciente) {
        return ResponseEntity.ok(pacientesClient.actualizarPaciente(id, paciente));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PacienteDTO> actualizarParcial(@PathVariable Long id, @Valid @RequestBody PacienteDTO paciente) {
        return ResponseEntity.ok(pacientesClient.actualizarParcial(id, paciente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        pacientesClient.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}
