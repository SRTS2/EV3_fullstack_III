package com.rednorte.auth.controller;

import com.rednorte.auth.dto.LoginRequest;
import com.rednorte.auth.dto.LoginResponse;
import com.rednorte.auth.dto.RegisterRequest;
import com.rednorte.auth.dto.UsuarioDTO;
import com.rednorte.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> register(@Valid @RequestBody RegisterRequest request) {
        UsuarioDTO created = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        return ResponseEntity.ok(authService.listarTodos());
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(authService.obtenerPorId(id));
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(authService.actualizar(id, dto));
    }

    @PatchMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO> actualizarParcial(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(authService.actualizar(id, dto));
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        authService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}
