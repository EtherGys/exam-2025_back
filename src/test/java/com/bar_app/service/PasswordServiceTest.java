package com.bar_app.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.bar_app.service.PasswordService;

class PasswordServiceTest {
    private final PasswordService passwordService = new PasswordService();

    @Test
    void encodePassword_andMatches() {
        String raw = "password";
        String encoded = passwordService.encodePassword(raw);
        assertTrue(passwordService.matches(raw, encoded));
        assertFalse(passwordService.matches("wrong", encoded));
    }

    @Test
    void isEncoded_true_false() {
        String encoded = passwordService.encodePassword("pw");
        assertTrue(passwordService.isEncoded(encoded));
        assertFalse(passwordService.isEncoded("notencoded"));
    }
} 