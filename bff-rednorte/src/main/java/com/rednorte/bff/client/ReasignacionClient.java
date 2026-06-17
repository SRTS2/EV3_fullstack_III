package com.rednorte.bff.client;

import com.rednorte.bff.dto.ReasignacionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "ms-reasignacion", url = "${ms.reasignacion.url:http://localhost:8082}")
public interface ReasignacionClient {

    @GetMapping("/api/v1/reasignaciones")
    List<ReasignacionDTO> listarTodas();

    @GetMapping("/api/v1/reasignaciones/{id}")
    ReasignacionDTO obtenerPorId(@PathVariable("id") Long id);

    @GetMapping("/api/v1/reasignaciones/historial/paciente/{pacienteId}")
    List<ReasignacionDTO> getHistorialPorPaciente(@PathVariable("pacienteId") Long pacienteId);

    @GetMapping("/api/v1/reasignaciones/disponibles")
    List<ReasignacionDTO> listarDisponibles();

    @PostMapping("/api/v1/reasignaciones")
    ReasignacionDTO ejecutarReasignacion(@RequestBody ReasignacionDTO body);

    @PutMapping("/api/v1/reasignaciones/{id}")
    ReasignacionDTO actualizar(@PathVariable("id") Long id, @RequestBody ReasignacionDTO body);

    @PutMapping("/api/v1/reasignaciones/{id}/confirmar")
    ReasignacionDTO confirmarReasignacion(@PathVariable("id") Long id);

    @PutMapping("/api/v1/reasignaciones/{id}/cancelar")
    ReasignacionDTO cancelarReasignacion(@PathVariable("id") Long id, @RequestBody Map<String, String> body);

    @DeleteMapping("/api/v1/reasignaciones/{id}")
    void eliminar(@PathVariable("id") Long id);
}
