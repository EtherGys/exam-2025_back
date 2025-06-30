package com.bar_app.controller;

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

import com.bar_app.entity.Cocktail;
import com.bar_app.service.CocktailService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cocktails")
@CrossOrigin(origins = "*")
public class CocktailController {
    
    @Autowired
    private CocktailService cocktailService;
    
    /**
     * GET /api/cocktails - Récupère tous les cocktails
     */
    @GetMapping
    public ResponseEntity<List<Cocktail>> getAllCocktails() {
        List<Cocktail> cocktails = cocktailService.getAllCocktails();
        return ResponseEntity.ok(cocktails);
    }
    
    /**
     * GET /api/cocktails/{id} - Récupère un cocktail par son ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cocktail> getCocktailById(@PathVariable Long id) {
        Optional<Cocktail> cocktail = cocktailService.getCocktailById(id);
        return cocktail.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * POST /api/cocktails - Crée un nouveau cocktail
     */
    @PostMapping(consumes = {"application/json", "application/json;charset=UTF-8"})
    public ResponseEntity<?> createCocktail(@Valid @RequestBody Cocktail cocktail) {
        try {
            Cocktail createdCocktail = cocktailService.createCocktail(cocktail);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCocktail);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * PUT /api/cocktails/{id} - Met à jour un cocktail existant
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCocktail(@PathVariable Long id, @Valid @RequestBody Cocktail cocktailDetails) {
        try {
            Cocktail updatedCocktail = cocktailService.updateCocktail(id, cocktailDetails);
            return ResponseEntity.ok(updatedCocktail);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * DELETE /api/cocktails/{id} - Supprime un cocktail
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCocktail(@PathVariable Long id) {
        try {
            cocktailService.deleteCocktail(id);
            return ResponseEntity.ok().body("Cocktail supprimé avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * GET /api/cocktails/search/nom/{nom} - Recherche des cocktails par nom
     */
    @GetMapping("/search/nom/{nom}")
    public ResponseEntity<List<Cocktail>> searchCocktailsByNom(@PathVariable String nom) {
        List<Cocktail> cocktails = cocktailService.searchCocktailsByNom(nom);
        return ResponseEntity.ok(cocktails);
    }
    
    /**
     * GET /api/cocktails/exists/{id} - Vérifie si un cocktail existe
     */
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> cocktailExists(@PathVariable Long id) {
        boolean exists = cocktailService.cocktailExists(id);
        return ResponseEntity.ok(exists);
    }
} 