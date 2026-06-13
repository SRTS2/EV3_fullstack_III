package com.rednorte.listaespera.controller;

import com.rednorte.listaespera.dto.ListaEsperaDTO;
import com.rednorte.listaespera.model.enums.EstadoEspera;
import com.rednorte.listaespera.service.ListaEsperaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/espera")
@RequiredArgsConstructor
public class ListaEsperaController {

    private final ListaEsperaService service;

    @GetMapping
    public ResponseEntity<List<ListaEsperaDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarPendientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaEsperaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ListaEsperaDTO>> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(service.obtenerPorPaciente(pacienteId));
    }

    @GetMapping("/especialidad/{especialidad}")
    public ResponseEntity<List<ListaEsperaDTO>> obtenerPorEspecialidad(@PathVariable String especialidad) {
        return ResponseEntity.ok(service.listarPorEspecialidad(especialidad));
    }

    @PostMapping
    public ResponseEntity<ListaEsperaDTO> registrar(@Valid @RequestBody ListaEsperaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListaEsperaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ListaEsperaDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ListaEsperaDTO> actualizarEstado(@PathVariable Long id, @RequestParam EstadoEspera estado) {
        return ResponseEntity.ok(service.actualizarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
