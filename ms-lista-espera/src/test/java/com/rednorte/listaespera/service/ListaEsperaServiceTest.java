package com.rednorte.listaespera.service;

import com.rednorte.listaespera.dto.ListaEsperaDTO;
import com.rednorte.listaespera.model.entity.PacienteEspera;
import com.rednorte.listaespera.model.enums.EstadoEspera;
import com.rednorte.listaespera.model.enums.Prioridad;
import com.rednorte.listaespera.repository.PacienteEsperaRepository;
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
class ListaEsperaServiceTest {

    @Mock
    private PacienteEsperaRepository repository;

    @InjectMocks
    private ListaEsperaService service;

    private PacienteEspera pacienteEspera;
    private ListaEsperaDTO listaEsperaDTO;

    @BeforeEach
    void setUp() {
        pacienteEspera = PacienteEspera.builder()
                .id(1L)
                .pacienteId(10L)
                .nombrePaciente("Juan Test")
                .rutPaciente("11111111-1")
                .especialidad("Cardiología")
                .prioridad(Prioridad.ALTA)
                .estado(EstadoEspera.EN_ESPERA)
                .fechaIngreso(LocalDateTime.now())
                .tiempoEstimadoDias(3)
                .build();

        listaEsperaDTO = ListaEsperaDTO.builder()
                .id(1L)
                .pacienteId(10L)
                .nombrePaciente("Juan Test")
                .rutPaciente("11111111-1")
                .especialidad("Cardiología")
                .prioridad(Prioridad.ALTA)
                .estado(EstadoEspera.EN_ESPERA)
                .fechaIngreso(LocalDateTime.now())
                .tiempoEstimadoDias(3)
                .build();
    }

    @Test
    void testRegistrar() {
        when(repository.save(any())).thenReturn(pacienteEspera);
        ListaEsperaDTO resultado = service.registrar(listaEsperaDTO);
        assertNotNull(resultado);
        assertEquals("Juan Test", resultado.getNombrePaciente());
        verify(repository, times(1)).save(any());
    }

    @Test
    void testListarPendientes() {
        when(repository.findByEstado(EstadoEspera.EN_ESPERA)).thenReturn(List.of(pacienteEspera));
        var resultado = service.listarPendientes();
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void testObtenerPorPaciente() {
        when(repository.findByPacienteId(10L)).thenReturn(List.of(pacienteEspera));
        var resultado = service.obtenerPorPaciente(10L);
        assertFalse(resultado.isEmpty());
        assertEquals("Cardiología", resultado.get(0).getEspecialidad());
    }

    @Test
    void testObtenerPorId_Existe() {
        when(repository.findById(1L)).thenReturn(Optional.of(pacienteEspera));
        var resultado = service.obtenerPorId(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void testObtenerPorId_NoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.obtenerPorId(99L));
    }

    @Test
    void testActualizarEstado() {
        when(repository.findById(1L)).thenReturn(Optional.of(pacienteEspera));
        when(repository.save(any())).thenReturn(pacienteEspera);
        var resultado = service.actualizarEstado(1L, EstadoEspera.ASIGNADO);
        assertNotNull(resultado);
        verify(repository, times(1)).save(any());
    }

    @Test
    void testEliminar_Existe() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        service.eliminar(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testEliminar_NoExiste() {
        when(repository.existsById(99L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> service.eliminar(99L));
    }
}
