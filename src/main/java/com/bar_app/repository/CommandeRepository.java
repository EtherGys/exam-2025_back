package com.bar_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bar_app.entity.Commande;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    
    /**
     * Trouve toutes les commandes d'un client
     */
    List<Commande> findByClientId(Long clientId);
    
    /**
     * Trouve toutes les commandes d'un cocktail
     */
    @Query("SELECT DISTINCT c FROM Commande c JOIN c.lignesDeCommande ldc WHERE ldc.cocktail.id = :cocktailId")
    List<Commande> findByCocktailId(@Param("cocktailId") Long cocktailId);
    } 