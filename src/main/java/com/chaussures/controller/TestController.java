package com.chaussures.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    @GetMapping("/")
    public String home() {
        return "Application de gestion des commandes de chaussures - API REST disponible sur /api";
    }
    
    @GetMapping("/health")
    public String health() {
        return "OK - Application en cours d'exécution";
    }
    
    @GetMapping("/info")
    public String info() {
        return """
            Application de Gestion des Commandes de Chaussures
            ================================================
            
            Endpoints disponibles :
            - /api/clients - Gestion des clients
            - /api/produits - Gestion des produits (chaussures)
            - /api/commandes - Gestion des commandes
            
            Base de données : MySQL
            Port : 8080
            """;
    }
} 