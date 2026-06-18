package com.rednorte.reasignacion.controller;

import com.rednorte.reasignacion.dto.ReasignacionDTO;
import com.rednorte.reasignacion.service.ReasignacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reasignaciones")
@RequiredArgsConstructor
public class ReasignacionController {

    private final ReasignacionService service;

    @GetMapping
    public ResponseEntity<List<ReasignacionDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReasignacionDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ReasignacionDTO> ejecutarReasignacion(@Valid @RequestBody ReasignacionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.ejecutarReasignacion(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReasignacionDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ReasignacionDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<ReasignacionDTO> confirmar(@PathVariable Long id) {
        return ResponseEntity.ok(service.confirmarReasignacion(id));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<ReasignacionDTO> cancelar(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String motivo = body.getOrDefault("motivo", "Sin motivo especificado");
        return ResponseEntity.ok(service.cancelarReasignacion(id, motivo));
    }

    @GetMapping("/historial/paciente/{pacienteId}")
    public ResponseEntity<List<ReasignacionDTO>> historial(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(service.obtenerHistorial(pacienteId));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<ReasignacionDTO>> disponibles() {
        return ResponseEntity.ok(service.listarDisponibles());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
