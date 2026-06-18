package com.rednorte.bff.controller;

import com.rednorte.bff.client.ReasignacionClient;
import com.rednorte.bff.dto.ReasignacionDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reasignaciones")
public class ReasignacionController {

    private final ReasignacionClient reasignacionClient;

    public ReasignacionController(ReasignacionClient reasignacionClient) {
        this.reasignacionClient = reasignacionClient;
    }

    @GetMapping
    public ResponseEntity<List<ReasignacionDTO>> listarTodas() {
        return ResponseEntity.ok(reasignacionClient.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReasignacionDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reasignacionClient.obtenerPorId(id));
    }

    @GetMapping("/historial/paciente/{pacienteId}")
    public ResponseEntity<List<ReasignacionDTO>> getHistorialPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(reasignacionClient.getHistorialPorPaciente(pacienteId));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<ReasignacionDTO>> listarDisponibles() {
        return ResponseEntity.ok(reasignacionClient.listarDisponibles());
    }

    @PostMapping
    public ResponseEntity<ReasignacionDTO> ejecutarReasignacion(@Valid @RequestBody ReasignacionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reasignacionClient.ejecutarReasignacion(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReasignacionDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ReasignacionDTO dto) {
        return ResponseEntity.ok(reasignacionClient.actualizar(id, dto));
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<ReasignacionDTO> confirmarReasignacion(@PathVariable Long id) {
        return ResponseEntity.ok(reasignacionClient.confirmarReasignacion(id));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<ReasignacionDTO> cancelarReasignacion(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(reasignacionClient.cancelarReasignacion(id, body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reasignacionClient.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
