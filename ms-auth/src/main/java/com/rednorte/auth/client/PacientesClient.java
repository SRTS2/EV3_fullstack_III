package com.rednorte.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "ms-pacientes", url = "${ms.pacientes.url:http://localhost:8083}")
public interface PacientesClient {

    @PostMapping("/api/v1/pacientes/login")
    Map<String, Object> login(@RequestBody Map<String, String> credenciales);

    @GetMapping("/api/v1/pacientes/{id}")
    Map<String, Object> obtenerPorId(@PathVariable("id") Long id);
}
