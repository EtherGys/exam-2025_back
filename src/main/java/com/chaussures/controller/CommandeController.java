package com.chaussures.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chaussures.entity.Commande;
import com.chaussures.service.CommandeService;

@RestController
@RequestMapping("/api/commandes")
@CrossOrigin(origins = "*")
public class CommandeController {
    
    @Autowired
    private CommandeService commandeService;
    
    /**
     * GET /api/commandes - Récupère toutes les commandes
     */
    @GetMapping
    public ResponseEntity<List<Commande>> getAllCommandes() {
        List<Commande> commandes = commandeService.getAllCommandes();
        return ResponseEntity.ok(commandes);
    }
    
    /**
     * GET /api/commandes/{id} - Récupère une commande par son ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommandeById(@PathVariable Long id) {
        Optional<Commande> commande = commandeService.getCommandeById(id);
        return commande.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * POST /api/commandes - Crée une nouvelle commande
     */
    @PostMapping
    public ResponseEntity<Commande> createCommande(@RequestBody CommandeRequest request) {
        Commande createdCommande = commandeService.createCommande(
            request.getClientId(),
            request.getLignesDeCommande()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCommande);
    }
    
    /**
     * PUT /api/commandes/{id} - Met à jour une commande existante
     */
    @PutMapping("/{id}")
    public ResponseEntity<Commande> updateCommande(@PathVariable Long id, @RequestBody CommandeRequest request) {
        Commande updatedCommande = commandeService.updateCommande(
            id,
            request.getClientId(),
            request.getLignesDeCommande()
        );
        return ResponseEntity.ok(updatedCommande);
    }
    
    /**
     * DELETE /api/commandes/{id} - Supprime une commande
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCommande(@PathVariable Long id) {
        try {
            commandeService.deleteCommande(id);
            return ResponseEntity.ok().body("Commande supprimée avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * GET /api/commandes/client/{clientId} - Récupère toutes les commandes d'un client
     */
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Commande>> getCommandesByClientId(@PathVariable Long clientId) {
        List<Commande> commandes = commandeService.getCommandesByClientId(clientId);
        return ResponseEntity.ok(commandes);
    }
    
    /**
     * GET /api/commandes/date-range - Récupère des commandes dans une période donnée
     */
    @GetMapping("/date-range")
    public ResponseEntity<List<Commande>> getCommandesByDateRange(
            @RequestParam String dateDebut, 
            @RequestParam String dateFin) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime debut = LocalDateTime.parse(dateDebut, formatter);
        LocalDateTime fin = LocalDateTime.parse(dateFin, formatter);
        List<Commande> commandes = commandeService.getCommandesByDateRange(debut, fin);
        return ResponseEntity.ok(commandes);
    }
    
    /**
     * GET /api/commandes/client-date-range - Récupère des commandes d'un client dans une période donnée
     */
    @GetMapping("/client-date-range")
    public ResponseEntity<List<Commande>> getCommandesByClientAndDateRange(
            @RequestParam Long clientId,
            @RequestParam String dateDebut, 
            @RequestParam String dateFin) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime debut = LocalDateTime.parse(dateDebut, formatter);
        LocalDateTime fin = LocalDateTime.parse(dateFin, formatter);
        List<Commande> commandes = commandeService.getCommandesByClientAndDateRange(clientId, debut, fin);
        return ResponseEntity.ok(commandes);
    }
    
    /**
     * GET /api/commandes/exists/{id} - Vérifie si une commande existe
     */
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> commandeExists(@PathVariable Long id) {
        boolean exists = commandeService.commandeExists(id);
        return ResponseEntity.ok(exists);
    }
    
    // Classe interne pour les requêtes de commande
    public static class CommandeRequest {
        private Long clientId;
        private List<LigneDeCommandeItem> lignesDeCommande;
        
        // Constructeurs
        public CommandeRequest() {}
        
        public CommandeRequest(Long clientId, List<LigneDeCommandeItem> lignesDeCommande) {
            this.clientId = clientId;
            this.lignesDeCommande = lignesDeCommande;
        }
        
        // Getters et Setters
        public Long getClientId() {
            return clientId;
        }
        
        public void setClientId(Long clientId) {
            this.clientId = clientId;
        }
        
        public List<LigneDeCommandeItem> getLignesDeCommande() {
            return lignesDeCommande;
        }
        
        public void setLignesDeCommande(List<LigneDeCommandeItem> lignesDeCommande) {
            this.lignesDeCommande = lignesDeCommande;
        }
    }
    
    public static class LigneDeCommandeItem {
        private Long cocktailId;
        private String taille;
        private Double prixTaille;
        private Integer quantite;
        private String statutCocktail; // optionnel, sinon COMMANDE
        
        // Constructeurs
        public LigneDeCommandeItem() {}
        
        public LigneDeCommandeItem(Long cocktailId, String taille, Double prixTaille, Integer quantite, String statutCocktail) {
            this.cocktailId = cocktailId;
            this.taille = taille;
            this.prixTaille = prixTaille;
            this.quantite = quantite;
            this.statutCocktail = statutCocktail;
        }
        
        // Getters et Setters
        public Long getCocktailId() {
            return cocktailId;
        }
        
        public void setCocktailId(Long cocktailId) {
            this.cocktailId = cocktailId;
        }
        
        public String getTaille() {
            return taille;
        }
        
        public void setTaille(String taille) {
            this.taille = taille;
        }
        
        public Double getPrixTaille() {
            return prixTaille;
        }
        
        public void setPrixTaille(Double prixTaille) {
            this.prixTaille = prixTaille;
        }
        
        public Integer getQuantite() {
            return quantite;
        }
        
        public void setQuantite(Integer quantite) {
            this.quantite = quantite;
        }
        
        public String getStatutCocktail() {
            return statutCocktail;
        }
        
        public void setStatutCocktail(String statutCocktail) {
            this.statutCocktail = statutCocktail;
        }
    }
} 