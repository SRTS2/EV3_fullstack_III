package com.rednorte.pacientes.service;

import com.rednorte.pacientes.dto.PacienteDTO;
import com.rednorte.pacientes.model.entity.Paciente;
import com.rednorte.pacientes.repository.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock
    private PacienteRepository repository;

    @InjectMocks
    private PacienteService service;

    private Paciente paciente;
    private PacienteDTO pacienteDTO;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        paciente = new Paciente();
        paciente.setId(1L);
        paciente.setRut("11111111-1");
        paciente.setNombre("Juan Test");
        paciente.setEmail("juan@test.com");
        paciente.setTelefono("912345678");
        paciente.setDireccion("Av Test 123");
        paciente.setFechaNacimiento(LocalDate.of(1990, 5, 15));
        paciente.setContrasena(encoder.encode("123456"));
        paciente.setRole("PACIENTE");
        paciente.setActivo(true);

        pacienteDTO = new PacienteDTO();
        pacienteDTO.setRut("11111111-1");
        pacienteDTO.setNombre("Juan Test");
        pacienteDTO.setEmail("juan@test.com");
        pacienteDTO.setTelefono("912345678");
        pacienteDTO.setDireccion("Av Test 123");
        pacienteDTO.setFechaNacimiento(LocalDate.of(1990, 5, 15));
    }

    @Test
    void testRegistrar() {
        when(repository.existsByRut("11111111-1")).thenReturn(false);
        when(repository.save(any())).thenReturn(paciente);
        PacienteDTO resultado = service.registrar(pacienteDTO, "123456");
        assertNotNull(resultado);
        assertEquals("Juan Test", resultado.getNombre());
        assertNull(resultado.getContrasena());
    }

    @Test
    void testRegistrar_RutDuplicado() {
        when(repository.existsByRut("11111111-1")).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> service.registrar(pacienteDTO, "123456"));
    }

    @Test
    void testAutenticar_Exitoso() {
        when(repository.findByRut("11111111-1")).thenReturn(Optional.of(paciente));
        PacienteDTO resultado = service.autenticar("11111111-1", "123456");
        assertNotNull(resultado);
        assertEquals("Juan Test", resultado.getNombre());
    }

    @Test
    void testAutenticar_ContrasenaIncorrecta() {
        when(repository.findByRut("11111111-1")).thenReturn(Optional.of(paciente));
        assertThrows(IllegalArgumentException.class, () -> service.autenticar("11111111-1", "wrong"));
    }

    @Test
    void testAutenticar_PacienteInactivo() {
        paciente.setActivo(false);
        when(repository.findByRut("11111111-1")).thenReturn(Optional.of(paciente));
        assertThrows(IllegalStateException.class, () -> service.autenticar("11111111-1", "123456"));
    }

    @Test
    void testObtenerPorId_Existe() {
        when(repository.findById(1L)).thenReturn(Optional.of(paciente));
        PacienteDTO resultado = service.obtenerPorId(1L);
        assertNotNull(resultado);
        assertEquals("juan@test.com", resultado.getEmail());
    }

    @Test
    void testObtenerPorId_NoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.obtenerPorId(99L));
    }

    @Test
    void testActualizar() {
        when(repository.findById(1L)).thenReturn(Optional.of(paciente));
        PacienteDTO actualizado = new PacienteDTO();
        actualizado.setNombre("Juan Actualizado");
        actualizado.setEmail("nuevo@test.com");
        when(repository.save(any())).thenReturn(paciente);
        PacienteDTO resultado = service.actualizar(1L, actualizado);
        assertNotNull(resultado);
    }

    @Test
    void testDesactivar() {
        when(repository.findById(1L)).thenReturn(Optional.of(paciente));
        when(repository.save(any())).thenReturn(paciente);
        service.desactivar(1L);
        assertFalse(paciente.getActivo());
        verify(repository, times(1)).save(any());
    }
}
