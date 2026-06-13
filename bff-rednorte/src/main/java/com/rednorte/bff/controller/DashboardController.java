package com.rednorte.bff.controller;

import com.rednorte.bff.dto.PacienteDashboardDTO;
import com.rednorte.bff.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/paciente/{id}")
    public ResponseEntity<PacienteDashboardDTO> getDashboard(@PathVariable Long id) {
        PacienteDashboardDTO dashboard = dashboardService.getDashboardPaciente(id);
        return ResponseEntity.ok(dashboard);
    }
}
