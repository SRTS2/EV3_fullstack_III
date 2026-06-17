package com.rednorte.auth.service;

import com.rednorte.auth.dto.LoginRequest;
import com.rednorte.auth.dto.LoginResponse;
import com.rednorte.auth.dto.RegisterRequest;
import com.rednorte.auth.dto.UsuarioDTO;
import com.rednorte.auth.model.entity.Usuario;
import com.rednorte.auth.repository.UsuarioRepository;
import com.rednorte.auth.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByRut(request.getRut())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));

        if (!usuario.getActivo()) {
            throw new IllegalArgumentException("Usuario inactivo");
        }

        if (!passwordEncoder.matches(request.getContrasena(), usuario.getContrasena())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        String token = jwtUtil.generateToken(usuario.getNombre(), usuario.getRole(), usuario.getPacienteId());

        return new LoginResponse(token, usuario.getRole(), usuario.getPacienteId());
    }

    public UsuarioDTO register(RegisterRequest request) {
        if (usuarioRepository.existsByRut(request.getRut())) {
            throw new IllegalArgumentException("El RUT ya está registrado");
        }

        Usuario usuario = Usuario.builder()
                .rut(request.getRut())
                .nombre(request.getNombre())
                .email(request.getEmail())
                .contrasena(passwordEncoder.encode(request.getContrasena()))
                .role("PACIENTE")
                .activo(true)
                .build();

        usuario = usuarioRepository.save(usuario);

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
