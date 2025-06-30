package com.chaussures.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chaussures.dto.AuthResponse;
import com.chaussures.entity.Client;
import com.chaussures.service.ClientService;
import com.chaussures.service.JwtService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class ClientController {
    
    @Autowired
    private ClientService clientService;
    
    @Autowired
    private JwtService jwtService;
    
    /**
     * GET /api/clients - Récupère tous les clients
     */
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }
    
    /**
     * GET /api/clients/{id} - Récupère un client par son ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Optional<Client> client = clientService.getClientById(id);
        return client.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * GET /api/clients/email/{email} - Récupère un client par son email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Client> getClientByEmail(@PathVariable String email) {
        Optional<Client> client = clientService.getClientByEmail(email);
        return client.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * POST /api/clients - Crée un nouveau client
     */
    @PostMapping(consumes = {"application/json", "application/json;charset=UTF-8"})
    public ResponseEntity<?> createClient(@Valid @RequestBody Client client) {
        try {
            Client createdClient = clientService.createClient(client);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * PUT /api/clients/{id} - Met à jour un client existant
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @Valid @RequestBody Client clientDetails) {
        try {
            Client updatedClient = clientService.updateClient(id, clientDetails);
            return ResponseEntity.ok(updatedClient);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * DELETE /api/clients/{id} - Supprime un client
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        try {
            clientService.deleteClient(id);
            return ResponseEntity.ok().body("Client supprimé avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * GET /api/clients/search/nom/{nom} - Recherche des clients par nom
     */
    @GetMapping("/search/nom/{nom}")
    public ResponseEntity<List<Client>> searchClientsByNom(@PathVariable String nom) {
        List<Client> clients = clientService.searchClientsByNom(nom);
        return ResponseEntity.ok(clients);
    }
    
    /**
     * GET /api/clients/search/prenom/{prenom} - Recherche des clients par prénom
     */
    @GetMapping("/search/prenom/{prenom}")
    public ResponseEntity<List<Client>> searchClientsByPrenom(@PathVariable String prenom) {
        List<Client> clients = clientService.searchClientsByPrenom(prenom);
        return ResponseEntity.ok(clients);
    }
    
    /**
     * GET /api/clients/exists/{id} - Vérifie si un client existe
     */
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> clientExists(@PathVariable Long id) {
        boolean exists = clientService.clientExists(id);
        return ResponseEntity.ok(exists);
    }
    
    /**
     * POST /api/clients/login - Authentification d'un client
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse authResponse = clientService.authenticateClient(loginRequest.getEmail(), loginRequest.getPassword());
            if (authResponse.getToken() != null) {
                return ResponseEntity.ok(authResponse);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResponse);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'authentification: " + e.getMessage());
        }
    }
    
    /**
     * POST /api/clients/validate-token - Valide un token JWT
     */
    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestBody TokenValidationRequest tokenRequest) {
        try {
            String email = jwtService.extractEmail(tokenRequest.getToken());
            Long userId = jwtService.extractUserId(tokenRequest.getToken());
            
            if (jwtService.validateToken(tokenRequest.getToken(), email)) {
                Optional<Client> client = clientService.getClientById(userId);
                if (client.isPresent()) {
                    return ResponseEntity.ok(client.get());
                }
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token invalide ou expiré");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token invalide");
        }
    }
    
    /**
     * POST /api/clients/login-old - Ancienne méthode d'authentification (pour compatibilité)
     */
    @PostMapping("/login-old")
    public ResponseEntity<?> loginOld(@RequestBody LoginRequest loginRequest) {
        try {
            Optional<Client> client = clientService.authenticateClientOld(loginRequest.getEmail(), loginRequest.getPassword());
            if (client.isPresent()) {
                return ResponseEntity.ok(client.get());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe incorrect");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'authentification: " + e.getMessage());
        }
    }
    
    /**
     * Classe interne pour la requête de connexion
     */
    public static class LoginRequest {
        private String email;
        private String password;
        
        // Constructeurs
        public LoginRequest() {}
        
        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
        
        // Getters et Setters
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        public String getPassword() {
            return password;
        }
        
        public void setPassword(String password) {
            this.password = password;
        }
    }
    
    /**
     * Classe interne pour la validation de token
     */
    public static class TokenValidationRequest {
        private String token;
        
        // Constructeurs
        public TokenValidationRequest() {}
        
        public TokenValidationRequest(String token) {
            this.token = token;
        }
        
        // Getters et Setters
        public String getToken() {
            return token;
        }
        
        public void setToken(String token) {
            this.token = token;
        }
    }
} 