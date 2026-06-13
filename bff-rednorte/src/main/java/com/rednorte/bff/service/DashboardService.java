package com.rednorte.bff.service;

import com.rednorte.bff.client.ListaEsperaClient;
import com.rednorte.bff.client.PacientesClient;
import com.rednorte.bff.client.ReasignacionClient;
import com.rednorte.bff.dto.ListaEsperaDTO;
import com.rednorte.bff.dto.PacienteDashboardDTO;
import com.rednorte.bff.dto.PacienteDTO;
import com.rednorte.bff.dto.ReasignacionDTO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class DashboardService {

    private final PacientesClient pacientesClient;
    private final ListaEsperaClient listaEsperaClient;
    private final ReasignacionClient reasignacionClient;

    public DashboardService(PacientesClient pacientesClient,
                            ListaEsperaClient listaEsperaClient,
                            ReasignacionClient reasignacionClient) {
        this.pacientesClient = pacientesClient;
        this.listaEsperaClient = listaEsperaClient;
        this.reasignacionClient = reasignacionClient;
    }

    public PacienteDashboardDTO getDashboardPaciente(Long id) {
        CompletableFuture<PacienteDTO> pacienteFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return pacientesClient.getPaciente(id);
            } catch (Exception e) {
                return null;
            }
        });

        CompletableFuture<ListaEsperaDTO> listaEsperaFuture = CompletableFuture.supplyAsync(() -> {
            try {
                List<ListaEsperaDTO> lista = listaEsperaClient.getPacienteEnEspera(id);
                return lista != null && !lista.isEmpty() ? lista.get(0) : null;
            } catch (Exception e) {
                return null;
            }
        });

        CompletableFuture<List<ReasignacionDTO>> reasignacionesFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return reasignacionClient.getHistorialPorPaciente(id);
            } catch (Exception e) {
                return Collections.emptyList();
            }
        });

        CompletableFuture.allOf(pacienteFuture, listaEsperaFuture, reasignacionesFuture).join();

        PacienteDashboardDTO dashboard = new PacienteDashboardDTO();
        dashboard.setPaciente(pacienteFuture.join());
        dashboard.setListaEspera(listaEsperaFuture.join());
        dashboard.setReasignaciones(reasignacionesFuture.join());

        return dashboard;
    }
}
