package com.rednorte.reasignacion.service;

import com.rednorte.reasignacion.dto.ReasignacionDTO;
import com.rednorte.reasignacion.model.entity.Reasignacion;
import com.rednorte.reasignacion.model.enums.EstadoReasignacion;
import com.rednorte.reasignacion.repository.ReasignacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReasignacionServiceTest {

    @Mock
    private ReasignacionRepository repository;

    @InjectMocks
    private ReasignacionService service;

    private Reasignacion reasignacion;

    @BeforeEach
    void setUp() {
        reasignacion = new Reasignacion();
        reasignacion.setId(1L);
        reasignacion.setPacienteId(10L);
        reasignacion.setNombrePaciente("Juan Test");
        reasignacion.setEspecialidad("Cardiología");
        reasignacion.setFechaOriginal(LocalDateTime.now().minusDays(1));
        reasignacion.setFechaReasignada(LocalDateTime.now().plusDays(3));
        reasignacion.setEstado(EstadoReasignacion.PENDIENTE);
    }

    @Test
    void testEjecutarReasignacion() {
        when(repository.save(any())).thenReturn(reasignacion);
        ReasignacionDTO dto = new ReasignacionDTO();
        dto.setPacienteId(10L);
        dto.setEspecialidad("Cardiología");
        ReasignacionDTO resultado = service.ejecutarReasignacion(dto);
        assertNotNull(resultado);
        assertEquals("Cardiología", resultado.getEspecialidad());
        assertEquals(EstadoReasignacion.PENDIENTE, resultado.getEstado());
        verify(repository, times(1)).save(any());
    }

    @Test
    void testObtenerHistorial() {
        when(repository.findByPacienteId(10L)).thenReturn(List.of(reasignacion));
        var resultado = service.obtenerHistorial(10L);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void testListarDisponibles() {
        when(repository.findByEstado(EstadoReasignacion.PENDIENTE)).thenReturn(List.of(reasignacion));
        var resultado = service.listarDisponibles();
        assertFalse(resultado.isEmpty());
    }

    @Test
    void testConfirmarReasignacion() {
        Reasignacion confirmada = new Reasignacion();
        confirmada.setId(1L);
        confirmada.setPacienteId(10L);
        confirmada.setEspecialidad("Cardiología");
        confirmada.setEstado(EstadoReasignacion.CONFIRMADA);

        when(repository.findById(1L)).thenReturn(Optional.of(reasignacion));
        when(repository.save(any())).thenReturn(confirmada);

        ReasignacionDTO resultado = service.confirmarReasignacion(1L);
        assertEquals(EstadoReasignacion.CONFIRMADA, resultado.getEstado());
    }

    @Test
    void testCancelarReasignacion() {
        Reasignacion cancelada = new Reasignacion();
        cancelada.setId(1L);
        cancelada.setPacienteId(10L);
        cancelada.setEspecialidad("Cardiología");
        cancelada.setEstado(EstadoReasignacion.CANCELADA);
        cancelada.setMotivoCancelacion("Paciente no disponible");

        when(repository.findById(1L)).thenReturn(Optional.of(reasignacion));
        when(repository.save(any())).thenReturn(cancelada);

        ReasignacionDTO resultado = service.cancelarReasignacion(1L, "Paciente no disponible");
        assertEquals(EstadoReasignacion.CANCELADA, resultado.getEstado());
    }

    @Test
    void testConfirmar_NoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.confirmarReasignacion(99L));
    }
}
