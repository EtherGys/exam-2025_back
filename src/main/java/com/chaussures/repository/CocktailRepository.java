package com.chaussures.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chaussures.entity.Cocktail;

@Repository
public interface CocktailRepository extends JpaRepository<Cocktail, Long> {
    
    /**
     * Trouve des produits par nom
     */
    List<Cocktail> findByNomContainingIgnoreCase(String nom);
} 