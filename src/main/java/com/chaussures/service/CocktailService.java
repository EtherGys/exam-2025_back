package com.chaussures.service;

import com.chaussures.entity.Cocktail;
import com.chaussures.repository.CocktailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CocktailService {
    @Autowired
    private CocktailRepository cocktailRepository;

    /**
     * Récupère tous les cocktails
     */
    public List<Cocktail> getAllCocktails() {
        return cocktailRepository.findAll();
    }

    /**
     * Récupère un cocktail par son ID
     */
    public Optional<Cocktail> getCocktailById(Long id) {
        return cocktailRepository.findById(id);
    }

    /**
     * Crée un nouveau cocktail
     */
    public Cocktail createCocktail(Cocktail cocktail) {
        return cocktailRepository.save(cocktail);
    }

    /**
     * Met à jour un cocktail existant
     */
    public Cocktail updateCocktail(Long id, Cocktail cocktailDetails) {
        Cocktail cocktail = cocktailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cocktail non trouvé avec l'ID: " + id));
        cocktail.setNom(cocktailDetails.getNom());
        cocktail.setIngredients(cocktailDetails.getIngredients());
        cocktail.setPrixS(cocktailDetails.getPrixS());
        cocktail.setPrixM(cocktailDetails.getPrixM());
        cocktail.setPrixL(cocktailDetails.getPrixL());
        cocktail.setCategorie(cocktailDetails.getCategorie());
        return cocktailRepository.save(cocktail);
    }

    /**
     * Supprime un cocktail
     */
    public void deleteCocktail(Long id) {
        Cocktail cocktail = cocktailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cocktail non trouvé avec l'ID: " + id));
        cocktailRepository.delete(cocktail);
    }

    /**
     * Recherche des cocktails par nom
     */
    public List<Cocktail> searchCocktailsByNom(String nom) {
        return cocktailRepository.findByNomContainingIgnoreCase(nom);
    }

    /**
     * Vérifie si un cocktail existe par son ID
     */
    public boolean cocktailExists(Long id) {
        return cocktailRepository.existsById(id);
    }
} 