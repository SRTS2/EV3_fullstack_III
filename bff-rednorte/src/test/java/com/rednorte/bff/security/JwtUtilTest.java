package com.rednorte.bff.security;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil("ClaveSecretaSuperSeguraParaPruebas2024BFF");
    }

    @Test
    void testGenerateAndValidateToken() {
        String token = jwtUtil.generateToken("11111111-1", "PACIENTE", 1L);
        assertNotNull(token);
        Claims claims = jwtUtil.validateToken(token);
        assertEquals("11111111-1", claims.getSubject());
        assertEquals("PACIENTE", claims.get("role"));
    }

    @Test
    void testExtractUsername() {
        String token = jwtUtil.generateToken("user123", "ADMIN", 5L);
        assertEquals("user123", jwtUtil.extractUsername(token));
    }

    @Test
    void testExtractRole() {
        String token = jwtUtil.generateToken("admin", "ADMIN", 1L);
        assertEquals("ADMIN", jwtUtil.extractRole(token));
    }

    @Test
    void testExtractPacienteId() {
        String token = jwtUtil.generateToken("test", "PACIENTE", 99L);
        assertEquals(99L, jwtUtil.extractPacienteId(token));
    }
}
