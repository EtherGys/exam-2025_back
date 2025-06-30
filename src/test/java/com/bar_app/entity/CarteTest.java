package com.bar_app.entity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.bar_app.entity.Carte;
import com.bar_app.entity.Client;
import com.bar_app.entity.Cocktail;
import com.bar_app.entity.Role;

public class CarteTest {
    @Test
    public void testCreationCarte() {
        Client barmaker = new Client("Martin", "Sophie", "sophie@email.com", "pass123", "1 rue de Paris", Role.BARMAKER);
        List<Cocktail> cocktails = Collections.emptyList();
        LocalDateTime now = LocalDateTime.now();
        Carte carte = new Carte("Carte Été", now, barmaker, cocktails);
        Assertions.assertEquals("Carte Été", carte.getNom());
        Assertions.assertEquals(now, carte.getDateCreation());
        Assertions.assertEquals(barmaker, carte.getBarmaker());
        Assertions.assertEquals(0, carte.getCocktails().size());
    }

    @Test
    public void testSetters() {
        Carte carte = new Carte();
        carte.setNom("Carte Hiver");
        LocalDateTime now = LocalDateTime.now();
        carte.setDateCreation(now);
        Client barmaker = new Client();
        carte.setBarmaker(barmaker);
        List<Cocktail> cocktails = Arrays.asList(new Cocktail(), new Cocktail());
        carte.setCocktails(cocktails);
        Assertions.assertEquals("Carte Hiver", carte.getNom());
        Assertions.assertEquals(now, carte.getDateCreation());
        Assertions.assertEquals(barmaker, carte.getBarmaker());
        Assertions.assertEquals(2, carte.getCocktails().size());
    }
} 