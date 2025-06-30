package com.bar_app.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.bar_app.entity.Cocktail;
import com.bar_app.entity.Commande;
import com.bar_app.entity.LigneDeCommande;
import com.bar_app.entity.StatutCocktail;
import com.bar_app.entity.Taille;

public class LigneDeCommandeTest {
    @Test
    public void testCreationLigneDeCommande() {
        Commande commande = new Commande();
        Cocktail cocktail = new Cocktail();
        LigneDeCommande ldc = new LigneDeCommande(commande, cocktail, Taille.M, 8.0, 2, StatutCocktail.COMMANDE);
        Assertions.assertEquals(commande, ldc.getCommande());
        Assertions.assertEquals(cocktail, ldc.getCocktail());
        Assertions.assertEquals(Taille.M, ldc.getTaille());
        Assertions.assertEquals(8.0, ldc.getPrixTaille());
        Assertions.assertEquals(2, ldc.getQuantite());
        Assertions.assertEquals(StatutCocktail.COMMANDE, ldc.getStatutCocktail());
    }

    @Test
    public void testSetters() {
        LigneDeCommande ldc = new LigneDeCommande();
        Commande commande = new Commande();
        Cocktail cocktail = new Cocktail();
        ldc.setCommande(commande);
        ldc.setCocktail(cocktail);
        ldc.setTaille(Taille.L);
        ldc.setPrixTaille(10.0);
        ldc.setQuantite(3);
        ldc.setStatutCocktail(StatutCocktail.TERMINE);
        Assertions.assertEquals(commande, ldc.getCommande());
        Assertions.assertEquals(cocktail, ldc.getCocktail());
        Assertions.assertEquals(Taille.L, ldc.getTaille());
        Assertions.assertEquals(10.0, ldc.getPrixTaille());
        Assertions.assertEquals(3, ldc.getQuantite());
        Assertions.assertEquals(StatutCocktail.TERMINE, ldc.getStatutCocktail());
    }
} 