package com.bar_app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.bar_app.service.JwtService;

class JwtServiceTest {
    private final JwtService jwtService = new JwtService();

    @Test
    void generateAndValidateToken() {
        String token = jwtService.generateToken("test@example.com", 1L, "USER");
        assertNotNull(token);
        assertTrue(jwtService.validateToken(token, "test@example.com"));
    }

    @Test
    void extractClaims() {
        String token = jwtService.generateToken("test@example.com", 1L, "USER");
        assertEquals("test@example.com", jwtService.extractEmail(token));
        assertEquals(1L, jwtService.extractUserId(token));
        assertEquals("USER", jwtService.extractUserRole(token));
    }
} 