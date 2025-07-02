package com.bar_app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bar_app.entity.Commande;
import com.bar_app.entity.LigneDeCommande;
import com.bar_app.entity.StatutCocktail;
import com.bar_app.service.CommandeService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/commandes")
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
    public ResponseEntity<Commande> createCommande(@RequestBody CommandeRequest request,
    HttpServletRequest httpRequest) {
        Long clientId = (Long) httpRequest.getAttribute("userId");
        if (clientId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Commande createdCommande = commandeService.createCommande(
        clientId,
        request.getLignesDeCommande());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCommande);
    }
    
    /**
    * PUT /api/commandes/{id} - Met à jour une commande existante
    */
    @PutMapping("/{id}")
    public ResponseEntity<Commande> updateCommande(@PathVariable Long id, @RequestBody CommandeRequest request,
    HttpServletRequest httpRequest) {
        Long clientId = (Long) httpRequest.getAttribute("userId");
        if (clientId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Commande updatedCommande = commandeService.updateCommande(
        id,
        clientId,
        request.getLignesDeCommande());
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
    * GET /api/commandes/client - Récupère toutes les commandes d'un
    * client
    */
    @GetMapping("/client")
    public ResponseEntity<List<Commande>> getCommandesByClientId(HttpServletRequest httpRequest) {
        Long clientId = (Long) httpRequest.getAttribute("userId");
        if (clientId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Commande> commandes = commandeService.getCommandesByClientId(clientId);
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
    
    /**
    * GET /api/commandes/barmaker - Récupère les lignes de commande
    * non terminées d'un barmaker
    */
    @GetMapping("/barmaker")
    public ResponseEntity<List<LigneDeCommande>> getLignesNonTermineesByBarmaker(HttpServletRequest httpRequest) {
        Long barmakerId = (Long) httpRequest.getAttribute("userId");
        if (barmakerId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<LigneDeCommande> lignes = commandeService.getLignesNonTermineesByBarmaker(barmakerId);
        return ResponseEntity.ok(lignes);
    }
    
    @PutMapping("/lignes/{ligneId}/statut")
    public ResponseEntity<?> updateStatutLigneDeCommande(
            @PathVariable Long ligneId,
            @RequestBody UpdateStatutRequest request) {
        try {
            LigneDeCommande ldc = commandeService.updateStatutLigneDeCommande(ligneId, StatutCocktail.valueOf(request.getStatut()));
            return ResponseEntity.ok(ldc);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Classe interne pour les requêtes de commande
    public static class CommandeRequest {
        private List<LigneDeCommandeItem> lignesDeCommande;
        
        // Constructeurs
        public CommandeRequest() {
        }
        
        public CommandeRequest(List<LigneDeCommandeItem> lignesDeCommande) {
            this.lignesDeCommande = lignesDeCommande;
        }
        
        // Getters et Setters
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
        public LigneDeCommandeItem() {
        }
        
        public LigneDeCommandeItem(Long cocktailId, String taille, Double prixTaille, Integer quantite,
        String statutCocktail) {
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
    
    public static class UpdateStatutRequest {
        private String statut;
        public String getStatut() { return statut; }
        public void setStatut(String statut) { this.statut = statut; }
    }
}