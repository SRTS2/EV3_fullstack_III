package com.rednorte.bff.controller;

import com.rednorte.bff.client.ReasignacionClient;
import com.rednorte.bff.dto.ReasignacionDTO;
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

    @GetMapping("/historial/paciente/{pacienteId}")
    public ResponseEntity<List<ReasignacionDTO>> getHistorialPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(reasignacionClient.getHistorialPorPaciente(pacienteId));
    }

    @PostMapping
    public ResponseEntity<ReasignacionDTO> ejecutarReasignacion(@RequestBody Map<String, Object> body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reasignacionClient.ejecutarReasignacion(body));
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<ReasignacionDTO> confirmarReasignacion(@PathVariable Long id) {
        return ResponseEntity.ok(reasignacionClient.confirmarReasignacion(id));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<ReasignacionDTO> cancelarReasignacion(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(reasignacionClient.cancelarReasignacion(id, body));
    }
}
