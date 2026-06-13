package com.rednorte.bff.client;

import com.rednorte.bff.dto.ReasignacionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "ms-reasignacion", url = "${ms.reasignacion.url:http://localhost:8083}")
public interface ReasignacionClient {

    @GetMapping("/api/v1/reasignaciones/historial/paciente/{pacienteId}")
    List<ReasignacionDTO> getHistorialPorPaciente(@PathVariable("pacienteId") Long pacienteId);

    @PostMapping("/api/v1/reasignaciones")
    ReasignacionDTO ejecutarReasignacion(@RequestBody Map<String, Object> body);

    @PutMapping("/api/v1/reasignaciones/{id}/confirmar")
    ReasignacionDTO confirmarReasignacion(@PathVariable("id") Long id);

    @PutMapping("/api/v1/reasignaciones/{id}/cancelar")
    ReasignacionDTO cancelarReasignacion(@PathVariable("id") Long id, @RequestBody Map<String, String> body);
}
