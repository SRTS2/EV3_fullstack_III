package com.rednorte.bff.controller;

import com.rednorte.bff.client.ListaEsperaClient;
import com.rednorte.bff.dto.ListaEsperaDTO;
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
    public ResponseEntity<List<ListaEsperaDTO>> getPacientesEnEspera() {
        List<ListaEsperaDTO> result = listaEsperaClient.getPacientesEnEspera();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ListaEsperaDTO>> getPacienteEnEspera(@PathVariable Long pacienteId) {
        List<ListaEsperaDTO> result = listaEsperaClient.getPacienteEnEspera(pacienteId);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ListaEsperaDTO> registrarEnEspera(@RequestBody ListaEsperaDTO dto) {
        ListaEsperaDTO result = listaEsperaClient.registrarEnEspera(dto);
        return ResponseEntity.ok(result);
    }
}
