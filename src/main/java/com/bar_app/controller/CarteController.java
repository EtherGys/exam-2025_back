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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bar_app.entity.Carte;
import com.bar_app.service.CarteService;
import com.bar_app.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/cartes")
public class CarteController {
    @Autowired
    private CarteService carteService;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<Carte>> getAllCartes() {
        return ResponseEntity.ok(carteService.getAllCartes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carte> getCarteById(@PathVariable Long id) {
        Optional<Carte> carte = carteService.getCarteById(id);
        return carte.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/barmaker")
    public ResponseEntity<List<Carte>> getCartesByBarmaker(HttpServletRequest httpRequest) {
            Long barmakerId = (Long) httpRequest.getAttribute("userId");
        if (barmakerId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(carteService.getCartesByBarmakerId(barmakerId));
    }

    @PostMapping
    public ResponseEntity<?> createCarte(@RequestBody CarteRequest request, HttpServletRequest httpRequest) {
        try {
            String authHeader = httpRequest.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token manquant");
            }
            String token = authHeader.substring(7);
            Long barmakerId = jwtService.extractUserId(token);
            Carte carte = carteService.createCarte(
                request.getNom(),
                request.getDescription(),
                request.getImage(),
                barmakerId,
                request.getCocktailIds()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(carte);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarte(@PathVariable Long id) {
        carteService.deleteCarte(id);
        return ResponseEntity.ok().body("Carte supprimée");
    }

    public static class CarteRequest {
        private String nom;
        private String description;
        private String image;
        private java.util.List<Long> cocktailIds;

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public java.util.List<Long> getCocktailIds() {
            return cocktailIds;
        }

        public void setCocktailIds(java.util.List<Long> cocktailIds) {
            this.cocktailIds = cocktailIds;
        }
    }
}