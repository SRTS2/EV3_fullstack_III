package com.rednorte.auth.service;

import com.rednorte.auth.dto.LoginRequest;
import com.rednorte.auth.dto.LoginResponse;
import com.rednorte.auth.dto.RegisterRequest;
import com.rednorte.auth.dto.UsuarioDTO;
import com.rednorte.auth.model.entity.Usuario;
import com.rednorte.auth.repository.UsuarioRepository;
import com.rednorte.auth.security.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtUtil jwtUtil;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(usuarioRepository, jwtUtil);
    }

    private Usuario crearUsuario() {
        Usuario u = new Usuario();
        u.setId(1L);
        u.setRut("12345678-9");
        u.setNombre("Juan Perez");
        u.setEmail("juan@mail.com");
        u.setContrasena("$2a$10$hash");
        u.setRole("PACIENTE");
        u.setActivo(true);
        return u;
    }

    @Test
    void testRegister_Exitoso() {
        RegisterRequest request = new RegisterRequest("12345678-9", "Juan Perez", "juan@mail.com", "pass123");

        when(usuarioRepository.existsByRut("12345678-9")).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(i -> {
            Usuario u = i.getArgument(0);
            u.setId(1L);
            return u;
        });

        UsuarioDTO result = authService.register(request);

        assertNotNull(result);
        assertEquals("12345678-9", result.getRut());
        assertEquals("Juan Perez", result.getNombre());
        assertEquals("PACIENTE", result.getRole());
        assertTrue(result.getActivo());

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());
        assertNotEquals("pass123", captor.getValue().getContrasena());
    }

    @Test
    void testRegister_RutDuplicado() {
        RegisterRequest request = new RegisterRequest("12345678-9", "Juan Perez", "juan@mail.com", "pass123");

        when(usuarioRepository.existsByRut("12345678-9")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> authService.register(request));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void testLogin_Exitoso() {
        Usuario usuario = crearUsuario();
        LoginRequest request = new LoginRequest("12345678-9", "pass123");

        when(usuarioRepository.findByRut("12345678-9")).thenReturn(Optional.of(usuario));
        when(jwtUtil.generateToken(anyString(), anyString(), anyLong(), any())).thenReturn("jwt-token");

        LoginResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("PACIENTE", response.getRole());
        assertEquals(1L, response.getUsuarioId());
    }

    @Test
    void testLogin_RutInvalido() {
        LoginRequest request = new LoginRequest("99999999-9", "pass123");

        when(usuarioRepository.findByRut("99999999-9")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> authService.login(request));
    }

    @Test
    void testLogin_UsuarioInactivo() {
        Usuario usuario = crearUsuario();
        usuario.setActivo(false);
        LoginRequest request = new LoginRequest("12345678-9", "pass123");

        when(usuarioRepository.findByRut("12345678-9")).thenReturn(Optional.of(usuario));

        assertThrows(IllegalStateException.class, () -> authService.login(request));
    }

    @Test
    void testObtenerPorId_Existe() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(crearUsuario()));

        UsuarioDTO result = authService.obtenerPorId(1L);

        assertNotNull(result);
        assertEquals("12345678-9", result.getRut());
    }

    @Test
    void testObtenerPorId_NoExiste() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> authService.obtenerPorId(99L));
    }

    @Test
    void testActualizarPacienteId() {
        Usuario usuario = crearUsuario();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        authService.actualizarPacienteId(1L, 100L);

        assertEquals(100L, usuario.getPacienteId());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void testListarTodos() {
        when(usuarioRepository.findAll()).thenReturn(List.of(crearUsuario()));

        var result = authService.listarTodos();

        assertEquals(1, result.size());
        assertEquals("12345678-9", result.get(0).getRut());
    }

    @Test
    void testActualizar_Exitoso() {
        Usuario usuario = crearUsuario();
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Nombre Actualizado");
        dto.setEmail("nuevo@mail.com");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioDTO result = authService.actualizar(1L, dto);

        assertEquals("Nombre Actualizado", result.getNombre());
        assertEquals("nuevo@mail.com", result.getEmail());
    }

    @Test
    void testActualizar_NoExiste() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Test");

        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> authService.actualizar(99L, dto));
    }

    @Test
    void testDesactivar_Exitoso() {
        Usuario usuario = crearUsuario();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        authService.desactivar(1L);

        assertFalse(usuario.getActivo());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void testDesactivar_NoExiste() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> authService.desactivar(99L));
    }
}
