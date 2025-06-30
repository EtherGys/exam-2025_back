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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chaussures.entity.Carte;
import com.chaussures.service.CarteService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/cartes")
@CrossOrigin(origins = "*")
public class CarteController {
    @Autowired
    private CarteService carteService;

    @GetMapping
    public ResponseEntity<List<Carte>> getAllCartes() {
        return ResponseEntity.ok(carteService.getAllCartes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carte> getCarteById(@PathVariable Long id) {
        Optional<Carte> carte = carteService.getCarteById(id);
        return carte.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/barmaker/{barmakerId}")
    public ResponseEntity<List<Carte>> getCartesByBarmaker(@PathVariable Long barmakerId) {
        return ResponseEntity.ok(carteService.getCartesByBarmakerId(barmakerId));
    }

    @PostMapping
    public ResponseEntity<Carte> createCarte(@RequestBody CarteRequest request, HttpServletRequest httpRequest) {
        Long barmakerId = (Long) httpRequest.getAttribute("userId");
        Carte carte = carteService.createCarte(request.getNom(), barmakerId, request.getCocktailIds());
        return ResponseEntity.status(HttpStatus.CREATED).body(carte);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarte(@PathVariable Long id) {
        carteService.deleteCarte(id);
        return ResponseEntity.ok().body("Carte supprimée");
    }

    public static class CarteRequest {
        private String nom;
        private java.util.List<Long> cocktailIds;
        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }
        public java.util.List<Long> getCocktailIds() { return cocktailIds; }
        public void setCocktailIds(java.util.List<Long> cocktailIds) { this.cocktailIds = cocktailIds; }
    }
} 