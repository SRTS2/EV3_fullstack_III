package com.rednorte.pacientes.controller;

import com.rednorte.pacientes.dto.PacienteDTO;
import com.rednorte.pacientes.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<PacienteDTO> registrar(@Valid @RequestBody PacienteDTO datos) {
        PacienteDTO resultado = pacienteService.registrar(datos, datos.getContrasena());
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listarTodos() {
        return ResponseEntity.ok(pacienteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> obtenerPorId(@PathVariable Long id) {
        PacienteDTO resultado = pacienteService.obtenerPorId(id);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<PacienteDTO> obtenerPorRut(@PathVariable String rut) {
        PacienteDTO resultado = pacienteService.obtenerPorRut(rut);
        return ResponseEntity.ok(resultado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> actualizar(@PathVariable Long id,
                                                   @Valid @RequestBody PacienteDTO datos) {
        PacienteDTO resultado = pacienteService.actualizar(id, datos);
        return ResponseEntity.ok(resultado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PacienteDTO> actualizarParcial(@PathVariable Long id,
                                                           @Valid @RequestBody PacienteDTO datos) {
        PacienteDTO resultado = pacienteService.actualizar(id, datos);
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        pacienteService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}
