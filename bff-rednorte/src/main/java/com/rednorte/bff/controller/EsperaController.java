package com.rednorte.bff.controller;

import com.rednorte.bff.client.ListaEsperaClient;
import com.rednorte.bff.dto.ListaEsperaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/espera")
public class EsperaController {

    private final ListaEsperaClient listaEsperaClient;

    public EsperaController(ListaEsperaClient listaEsperaClient) {
        this.listaEsperaClient = listaEsperaClient;
    }

    @GetMapping
    public ResponseEntity<List<ListaEsperaDTO>> listarTodas() {
        return ResponseEntity.ok(listaEsperaClient.getPacientesEnEspera());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaEsperaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(listaEsperaClient.obtenerPorId(id));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ListaEsperaDTO>> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(listaEsperaClient.getPacienteEnEspera(pacienteId));
    }

    @GetMapping("/especialidad/{especialidad}")
    public ResponseEntity<List<ListaEsperaDTO>> obtenerPorEspecialidad(@PathVariable String especialidad) {
        return ResponseEntity.ok(listaEsperaClient.obtenerPorEspecialidad(especialidad));
    }

    @PostMapping
    public ResponseEntity<ListaEsperaDTO> registrar(@RequestBody ListaEsperaDTO dto) {
        ListaEsperaDTO result = listaEsperaClient.registrarEnEspera(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListaEsperaDTO> actualizar(@PathVariable Long id, @RequestBody ListaEsperaDTO dto) {
        return ResponseEntity.ok(listaEsperaClient.actualizar(id, dto));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ListaEsperaDTO> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.ok(listaEsperaClient.actualizarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        listaEsperaClient.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
