package com.bar_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bar_app.entity.Cocktail;
import com.bar_app.repository.CocktailRepository;

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
        cocktail.setCategories(cocktailDetails.getCategories());
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