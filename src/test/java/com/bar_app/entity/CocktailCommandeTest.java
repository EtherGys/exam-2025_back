package com.bar_app.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.bar_app.entity.Cocktail;
import com.bar_app.entity.CocktailCommande;
import com.bar_app.entity.Commande;
import com.bar_app.entity.EtatAvancementCocktail;

public class CocktailCommandeTest {
    @Test
    public void testCreationCocktailCommande() {
        Cocktail cocktail = new Cocktail();
        Commande commande = new Commande();
        CocktailCommande cc = new CocktailCommande(cocktail, commande, EtatAvancementCocktail.ASSEMBLAGE);
        Assertions.assertEquals(cocktail, cc.getCocktail());
        Assertions.assertEquals(commande, cc.getCommande());
        Assertions.assertEquals(EtatAvancementCocktail.ASSEMBLAGE, cc.getEtatAvancement());
    }

    @Test
    public void testSetters() {
        CocktailCommande cc = new CocktailCommande();
        Cocktail cocktail = new Cocktail();
        Commande commande = new Commande();
        cc.setCocktail(cocktail);
        cc.setCommande(commande);
        cc.setEtatAvancement(EtatAvancementCocktail.TERMINE);
        Assertions.assertEquals(cocktail, cc.getCocktail());
        Assertions.assertEquals(commande, cc.getCommande());
        Assertions.assertEquals(EtatAvancementCocktail.TERMINE, cc.getEtatAvancement());
    }
} 