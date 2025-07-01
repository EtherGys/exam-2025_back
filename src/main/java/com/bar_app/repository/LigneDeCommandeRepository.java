package com.bar_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bar_app.entity.LigneDeCommande;

@Repository
public interface LigneDeCommandeRepository extends JpaRepository<LigneDeCommande, Long> {
    @Query("SELECT ldc FROM LigneDeCommande ldc " +
           "JOIN ldc.cocktail c " +
           "JOIN Carte carte ON c MEMBER OF carte.cocktails " +
           "WHERE carte.barmaker.id = :barmakerId " +
           "AND ldc.statutCocktail <> 'TERMINE'")
    List<LigneDeCommande> findNonTermineesByBarmakerId(@Param("barmakerId") Long barmakerId);
} 