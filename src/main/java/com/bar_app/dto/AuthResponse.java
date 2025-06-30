package com.bar_app.dto;

import com.bar_app.entity.Client;

public class AuthResponse {
    private String token;
    private Client client;
    private String message;
    
    public AuthResponse() {}
    
    public AuthResponse(String token, Client client) {
        this.token = token;
        this.client = client;
        this.message = "Authentification réussie";
    }
    
    public AuthResponse(String message) {
        this.message = message;
    }
    
    // Getters et Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public Client getClient() {
        return client;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
} 