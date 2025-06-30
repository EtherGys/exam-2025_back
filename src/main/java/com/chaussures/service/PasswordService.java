package com.chaussures.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    
    private final BCryptPasswordEncoder passwordEncoder;
    
    public PasswordService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    
    /**
     * Chiffre un mot de passe en utilisant BCrypt
     * @param rawPassword Le mot de passe en clair
     * @return Le mot de passe chiffré
     */
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
    
    /**
     * Vérifie si un mot de passe en clair correspond au mot de passe chiffré
     * @param rawPassword Le mot de passe en clair à vérifier
     * @param encodedPassword Le mot de passe chiffré stocké
     * @return true si les mots de passe correspondent, false sinon
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    
    /**
     * Vérifie si un mot de passe est déjà chiffré
     * @param password Le mot de passe à vérifier
     * @return true si le mot de passe est déjà chiffré, false sinon
     */
    public boolean isEncoded(String password) {
        return password != null && password.startsWith("$2a$") && password.length() == 60;
    }
} 