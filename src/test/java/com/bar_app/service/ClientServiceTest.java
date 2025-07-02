package com.bar_app.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void createClient_emailExists() {
        Client client = new Client();
        client.setEmail("a@b.com");
        when(clientRepository.existsByEmail("a@b.com")).thenReturn(true);
        assertThrows(RuntimeException.class, () -> clientService.createClient(client));
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

    @Test
    void getClientById_found() {
        Client client = new Client();
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        assertEquals(Optional.of(client), clientService.getClientById(1L));
    }

    @Test
    void getClientById_notFound() {
        when(clientRepository.findById(2L)).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), clientService.getClientById(2L));
    }

    @Test
    void updateClient_success() {
        Client existing = new Client();
        existing.setId(1L);
        existing.setEmail("a@b.com");
        Client update = new Client();
        update.setEmail("a@b.com");
        when(clientRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(clientRepository.existsByEmail("a@b.com")).thenReturn(false);
        when(passwordService.isEncoded(any())).thenReturn(true);
        when(clientRepository.save(any())).thenReturn(existing);
        Client result = clientService.updateClient(1L, update);
        assertEquals("a@b.com", result.getEmail());
    }

    @Test
    void updateClient_emailExists() {
        Client existing = new Client();
        existing.setId(1L);
        existing.setEmail("a@b.com");
        Client update = new Client();
        update.setEmail("other@b.com");
        when(clientRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(clientRepository.existsByEmail("other@b.com")).thenReturn(true);
        assertThrows(RuntimeException.class, () -> clientService.updateClient(1L, update));
    }

    @Test
    void updateClient_passwordNotEncoded() {
        Client existing = new Client();
        existing.setId(1L);
        existing.setEmail("a@b.com");
        Client update = new Client();
        update.setEmail("a@b.com");
        update.setMotDePasse("plain");
        when(clientRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(clientRepository.existsByEmail("a@b.com")).thenReturn(false);
        when(passwordService.isEncoded("plain")).thenReturn(false);
        when(passwordService.encodePassword("plain")).thenReturn("encoded");
        when(clientRepository.save(any())).thenReturn(existing);
        Client result = clientService.updateClient(1L, update);
        assertEquals("encoded", result.getMotDePasse());
    }

    @Test
    void deleteClient_success() {
        Client client = new Client();
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        assertDoesNotThrow(() -> clientService.deleteClient(1L));
    }

    @Test
    void deleteClient_notFound() {
        when(clientRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> clientService.deleteClient(2L));
    }

    @Test
    void searchClientsByNom_returnsList() {
        List<Client> clients = List.of(new Client());
        when(clientRepository.findByNomContainingIgnoreCase("Dupont")).thenReturn(clients);
        assertEquals(clients, clientService.searchClientsByNom("Dupont"));
    }

    @Test
    void searchClientsByPrenom_returnsList() {
        List<Client> clients = List.of(new Client());
        when(clientRepository.findByPrenomContainingIgnoreCase("Jean")).thenReturn(clients);
        assertEquals(clients, clientService.searchClientsByPrenom("Jean"));
    }

    @Test
    void authenticateClient_failure() {
        when(clientRepository.findByEmail("a@b.com")).thenReturn(Optional.empty());
        AuthResponse response = clientService.authenticateClient("a@b.com", "pw");
        assertEquals("Email ou mot de passe incorrect", response.getMessage());
    }

    @Test
    void authenticateClientOld_success() {
        Client client = new Client();
        client.setEmail("a@b.com");
        client.setMotDePasse("encoded");
        when(clientRepository.findByEmail("a@b.com")).thenReturn(Optional.of(client));
        when(passwordService.matches("pw", "encoded")).thenReturn(true);
        Optional<Client> result = clientService.authenticateClientOld("a@b.com", "pw");
        assertTrue(result.isPresent());
    }

    @Test
    void authenticateClientOld_failure() {
        when(clientRepository.findByEmail("a@b.com")).thenReturn(Optional.empty());
        Optional<Client> result = clientService.authenticateClientOld("a@b.com", "pw");
        assertTrue(result.isEmpty());
    }
} 