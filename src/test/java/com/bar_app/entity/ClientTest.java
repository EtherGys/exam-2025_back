package com.bar_app.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.bar_app.entity.Client;
import com.bar_app.entity.Role;

public class ClientTest {
    @Test
    public void testCreationClient() {
        Client client = new Client("Martin", "Sophie", "sophie@email.com", "pass123", "1 rue de Paris", Role.BARMAKER);
        Assertions.assertEquals("Martin", client.getNom());
        Assertions.assertEquals("Sophie", client.getPrenom());
        Assertions.assertEquals("sophie@email.com", client.getEmail());
        Assertions.assertEquals("pass123", client.getMotDePasse());
        Assertions.assertEquals("1 rue de Paris", client.getAdresse());
        Assertions.assertEquals(Role.BARMAKER, client.getRole());
    }

    @Test
    public void testSetters() {
        Client client = new Client();
        client.setNom("Durand");
        client.setPrenom("Paul");
        client.setEmail("paul@email.com");
        client.setMotDePasse("pass456");
        client.setAdresse("2 avenue de Lyon");
        client.setRole(Role.USER);
        Assertions.assertEquals("Durand", client.getNom());
        Assertions.assertEquals("Paul", client.getPrenom());
        Assertions.assertEquals("paul@email.com", client.getEmail());
        Assertions.assertEquals("pass456", client.getMotDePasse());
        Assertions.assertEquals("2 avenue de Lyon", client.getAdresse());
        Assertions.assertEquals(Role.USER, client.getRole());
    }
} 