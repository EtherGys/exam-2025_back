package com.bar_app.repository;

import com.bar_app.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    
    /**
     * Trouve un client par son email
     */
    Optional<Client> findByEmail(String email);
    
    /**
     * Vérifie si un email existe déjà
     */
    boolean existsByEmail(String email);
    
    /**
     * Trouve des clients par nom
     */
    java.util.List<Client> findByNomContainingIgnoreCase(String nom);
    
    /**
     * Trouve des clients par prénom
     */
    java.util.List<Client> findByPrenomContainingIgnoreCase(String prenom);
} 