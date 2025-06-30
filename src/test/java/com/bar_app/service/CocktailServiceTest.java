package com.bar_app.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.bar_app.entity.Cocktail;
import com.bar_app.repository.CocktailRepository;
import com.bar_app.service.CocktailService;

class CocktailServiceTest {
    @Mock
    private CocktailRepository cocktailRepository;
    @InjectMocks
    private CocktailService cocktailService;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void getAllCocktails_returnsList() {
        List<Cocktail> cocktails = List.of(new Cocktail());
        when(cocktailRepository.findAll()).thenReturn(cocktails);
        assertEquals(cocktails, cocktailService.getAllCocktails());
    }

    @Test
    void getCocktailById_found() {
        Cocktail cocktail = new Cocktail();
        when(cocktailRepository.findById(1L)).thenReturn(Optional.of(cocktail));
        assertTrue(cocktailService.getCocktailById(1L).isPresent());
    }

    @Test
    void createCocktail_saves() {
        Cocktail cocktail = new Cocktail();
        when(cocktailRepository.save(cocktail)).thenReturn(cocktail);
        assertEquals(cocktail, cocktailService.createCocktail(cocktail));
    }

    @Test
    void updateCocktail_updatesFields() {
        Cocktail existing = new Cocktail();
        existing.setNom("old");
        Cocktail update = new Cocktail();
        update.setNom("new");
        when(cocktailRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(cocktailRepository.save(any())).thenReturn(existing);
        Cocktail result = cocktailService.updateCocktail(1L, update);
        assertEquals("new", result.getNom());
    }

    @Test
    void deleteCocktail_deletes() {
        Cocktail cocktail = new Cocktail();
        when(cocktailRepository.findById(1L)).thenReturn(Optional.of(cocktail));
        doNothing().when(cocktailRepository).delete(cocktail);
        assertDoesNotThrow(() -> cocktailService.deleteCocktail(1L));
    }

    @Test
    void searchCocktailsByNom_returnsList() {
        when(cocktailRepository.findByNomContainingIgnoreCase("mojito")).thenReturn(List.of(new Cocktail()));
        assertFalse(cocktailService.searchCocktailsByNom("mojito").isEmpty());
    }

    @Test
    void cocktailExists_true() {
        when(cocktailRepository.existsById(1L)).thenReturn(true);
        assertTrue(cocktailService.cocktailExists(1L));
    }
} 