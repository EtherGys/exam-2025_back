package com.chaussures.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chaussures.entity.Carte;

@Repository
public interface CarteRepository extends JpaRepository<Carte, Long> {
    List<Carte> findByBarmakerId(Long barmakerId);
    
    Optional<Carte> findByNomAndBarmakerId(String nom, Long barmakerId);
    
    boolean existsByNomAndBarmakerId(String nom, Long barmakerId);
} 