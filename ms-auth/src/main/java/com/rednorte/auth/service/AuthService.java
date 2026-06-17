package com.rednorte.auth.service;

import com.rednorte.auth.dto.LoginRequest;
import com.rednorte.auth.dto.LoginResponse;
import com.rednorte.auth.dto.RegisterRequest;
import com.rednorte.auth.dto.UsuarioDTO;
import com.rednorte.auth.model.entity.Usuario;
import com.rednorte.auth.repository.UsuarioRepository;
import com.rednorte.auth.security.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByRut(request.getRut())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales invalidas"));

        if (!usuario.getActivo()) {
            throw new IllegalStateException("Usuario desactivado");
        }

        if (!passwordEncoder.matches(request.getContrasena(), usuario.getContrasena())) {
            throw new IllegalArgumentException("Credenciales invalidas");
        }

        String token = jwtUtil.generateToken(
                usuario.getRut(),
                usuario.getRole(),
                usuario.getId(),
                usuario.getPacienteId()
        );

        return LoginResponse.builder()
                .token(token)
                .role(usuario.getRole())
                .usuarioId(usuario.getId())
                .pacienteId(usuario.getPacienteId())
                .nombre(usuario.getNombre())
                .build();
    }

    @Transactional
    public UsuarioDTO register(RegisterRequest request) {
        if (usuarioRepository.existsByRut(request.getRut())) {
            throw new IllegalArgumentException("El RUT ya esta registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setRut(request.getRut());
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
        usuario.setRole("PACIENTE");
        usuario.setActivo(true);
        usuario = usuarioRepository.save(usuario);

        return toDTO(usuario);
    }

    @Transactional(readOnly = true)
    public UsuarioDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + id));
        return toDTO(usuario);
    }

    @Transactional
    public void actualizarPacienteId(Long usuarioId, Long pacienteId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + usuarioId));
        usuario.setPacienteId(pacienteId);
        usuarioRepository.save(usuario);
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .rut(usuario.getRut())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .role(usuario.getRole())
                .activo(usuario.getActivo())
                .pacienteId(usuario.getPacienteId())
                .build();
    }
}
