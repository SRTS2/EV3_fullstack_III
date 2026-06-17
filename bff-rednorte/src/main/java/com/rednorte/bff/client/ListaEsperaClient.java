package com.rednorte.bff.client;

import com.rednorte.bff.dto.ListaEsperaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ms-lista-espera", url = "${ms.lista-espera.url:http://localhost:8081}")
public interface ListaEsperaClient {

    @GetMapping("/api/v1/espera")
    List<ListaEsperaDTO> getPacientesEnEspera();

    @GetMapping("/api/v1/espera/{id}")
    ListaEsperaDTO obtenerPorId(@PathVariable("id") Long id);

    @GetMapping("/api/v1/espera/paciente/{pacienteId}")
    List<ListaEsperaDTO> getPacienteEnEspera(@PathVariable("pacienteId") Long pacienteId);

    @GetMapping("/api/v1/espera/especialidad/{especialidad}")
    List<ListaEsperaDTO> obtenerPorEspecialidad(@PathVariable("especialidad") String especialidad);

    @PostMapping("/api/v1/espera")
    ListaEsperaDTO registrarEnEspera(@RequestBody ListaEsperaDTO dto);

    @PutMapping("/api/v1/espera/{id}")
    ListaEsperaDTO actualizar(@PathVariable("id") Long id, @RequestBody ListaEsperaDTO dto);

    @PatchMapping("/api/v1/espera/{id}/estado")
    ListaEsperaDTO actualizarEstado(@PathVariable("id") Long id, @RequestParam("estado") String estado);

    @DeleteMapping("/api/v1/espera/{id}")
    void eliminar(@PathVariable("id") Long id);
}
