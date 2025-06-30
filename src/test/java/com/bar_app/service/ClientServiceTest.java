package com.bar_app.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.bar_app.dto.AuthResponse;
import com.bar_app.entity.Client;
import com.bar_app.entity.Role;
import com.bar_app.repository.ClientRepository;
import com.bar_app.service.ClientService;
import com.bar_app.service.JwtService;
import com.bar_app.service.PasswordService;

class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private PasswordService passwordService;
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void getAllClients_returnsList() {
        List<Client> clients = List.of(new Client());
        when(clientRepository.findAll()).thenReturn(clients);
        assertEquals(clients, clientService.getAllClients());
    }

    @Test
    void createClient_setsRoleAndEncodesPassword() {
        Client client = new Client();
        client.setEmail("a@b.com");
        client.setMotDePasse("pw");
        when(clientRepository.existsByEmail("a@b.com")).thenReturn(false);
        when(passwordService.encodePassword("pw")).thenReturn("encoded");
        when(clientRepository.save(any())).thenReturn(client);
        Client saved = clientService.createClient(client);
        assertEquals("encoded", saved.getMotDePasse());
    }

    @Test
    void authenticateClient_success() {
        Client client = new Client();
        client.setEmail("a@b.com");
        client.setMotDePasse("encoded");
        client.setRole(Role.USER);
        when(clientRepository.findByEmail("a@b.com")).thenReturn(Optional.of(client));
        when(passwordService.matches("pw", "encoded")).thenReturn(true);
        when(jwtService.generateToken(any(), any(), any())).thenReturn("token");
        AuthResponse response = clientService.authenticateClient("a@b.com", "pw");
        assertEquals("token", response.getToken());
    }

    @Test
    void clientExists_true() {
        when(clientRepository.existsById(1L)).thenReturn(true);
        assertTrue(clientService.clientExists(1L));
    }
} 