package com.chaussures.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chaussures.dto.AuthResponse;
import com.chaussures.entity.Client;
import com.chaussures.entity.Role;
import com.chaussures.repository.ClientRepository;

@Service
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private PasswordService passwordService;
    
    @Autowired
    private JwtService jwtService;
    
    /**
     * Récupère tous les clients
     */
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    
    /**
     * Récupère un client par son ID
     */
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }
    
    /**
     * Récupère un client par son email
     */
    public Optional<Client> getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }
    
    /**
     * Crée un nouveau client
     */
    public Client createClient(Client client) {
        // Vérifier si l'email existe déjà
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new RuntimeException("Un client avec cet email existe déjà");
        }
        // Si le rôle n'est pas précisé, on met USER par défaut
        if (client.getRole() == null) {
            client.setRole(Role.USER);
        }
        // Chiffrer le mot de passe avant de sauvegarder
        String encodedPassword = passwordService.encodePassword(client.getMotDePasse());
        client.setMotDePasse(encodedPassword);
        return clientRepository.save(client);
    }
    
    /**
     * Met à jour un client existant
     */
    public Client updateClient(Long id, Client clientDetails) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'ID: " + id));
        
        // Vérifier si le nouvel email existe déjà (sauf pour le client actuel)
        if (!client.getEmail().equals(clientDetails.getEmail()) && 
            clientRepository.existsByEmail(clientDetails.getEmail())) {
            throw new RuntimeException("Un client avec cet email existe déjà");
        }
        
        client.setNom(clientDetails.getNom());
        client.setPrenom(clientDetails.getPrenom());
        client.setEmail(clientDetails.getEmail());
        client.setAdresse(clientDetails.getAdresse());
        
        // Mettre à jour le rôle si précisé
        if (clientDetails.getRole() != null) {
            client.setRole(clientDetails.getRole());
        }
        
        // Chiffrer le mot de passe seulement s'il a été modifié et n'est pas déjà chiffré
        if (clientDetails.getMotDePasse() != null && 
            !passwordService.isEncoded(clientDetails.getMotDePasse())) {
            String encodedPassword = passwordService.encodePassword(clientDetails.getMotDePasse());
            client.setMotDePasse(encodedPassword);
        } else if (clientDetails.getMotDePasse() != null) {
            client.setMotDePasse(clientDetails.getMotDePasse());
        }
        
        return clientRepository.save(client);
    }
    
    /**
     * Vérifie les identifiants de connexion d'un client et retourne un token
     */
    public AuthResponse authenticateClient(String email, String password) {
        Optional<Client> clientOpt = clientRepository.findByEmail(email);
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            if (passwordService.matches(password, client.getMotDePasse())) {
                // Générer un token JWT
                String token = jwtService.generateToken(client.getEmail(), client.getId(), client.getRole().name());
                return new AuthResponse(token, client);
            }
        }
        return new AuthResponse("Email ou mot de passe incorrect");
    }
    
    /**
     * Vérifie les identifiants de connexion d'un client (ancienne méthode pour compatibilité)
     */
    public Optional<Client> authenticateClientOld(String email, String password) {
        Optional<Client> clientOpt = clientRepository.findByEmail(email);
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            if (passwordService.matches(password, client.getMotDePasse())) {
                return clientOpt;
            }
        }
        return Optional.empty();
    }
    
    /**
     * Supprime un client
     */
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'ID: " + id));
        clientRepository.delete(client);
    }
    
    /**
     * Recherche des clients par nom
     */
    public List<Client> searchClientsByNom(String nom) {
        return clientRepository.findByNomContainingIgnoreCase(nom);
    }
    
    /**
     * Recherche des clients par prénom
     */
    public List<Client> searchClientsByPrenom(String prenom) {
        return clientRepository.findByPrenomContainingIgnoreCase(prenom);
    }
    
    /**
     * Vérifie si un client existe par son ID
     */
    public boolean clientExists(Long id) {
        return clientRepository.existsById(id);
    }
} 