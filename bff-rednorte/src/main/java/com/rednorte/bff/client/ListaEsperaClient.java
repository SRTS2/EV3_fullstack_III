package com.rednorte.bff.client;

import com.rednorte.bff.dto.ListaEsperaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ms-lista-espera", url = "${ms.lista-espera.url:http://localhost:8082}")
public interface ListaEsperaClient {

    @GetMapping("/api/v1/espera")
    List<ListaEsperaDTO> getPacientesEnEspera();

    @GetMapping("/api/v1/espera/paciente/{pacienteId}")
    List<ListaEsperaDTO> getPacienteEnEspera(@PathVariable("pacienteId") Long pacienteId);

    @PostMapping("/api/v1/espera")
    ListaEsperaDTO registrarEnEspera(@RequestBody ListaEsperaDTO dto);
}
