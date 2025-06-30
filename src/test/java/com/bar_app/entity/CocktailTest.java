package com.bar_app.entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.bar_app.entity.Cocktail;
import com.bar_app.entity.CocktailCategorie;

public class CocktailTest {

    @Test
    public void testCreationCocktailAvecCategoriesMultiples() {
        List<String> ingredients = Arrays.asList("Rhum blanc", "Menthe", "Citron vert");
        List<CocktailCategorie> categories = Arrays.asList(CocktailCategorie.CLASSIQUE, CocktailCategorie.RAFRAICHISSANT);
        Cocktail cocktail = new Cocktail("Mojito", ingredients, 6.0, 8.0, 10.0, categories);

        Assertions.assertEquals("Mojito", cocktail.getNom());
        Assertions.assertEquals(3, cocktail.getIngredients().size());
        Assertions.assertEquals(6.0, cocktail.getPrixS());
        Assertions.assertEquals(8.0, cocktail.getPrixM());
        Assertions.assertEquals(10.0, cocktail.getPrixL());
        Assertions.assertTrue(cocktail.getCategories().contains(CocktailCategorie.CLASSIQUE));
        Assertions.assertTrue(cocktail.getCategories().contains(CocktailCategorie.RAFRAICHISSANT));
    }

    @Test
    public void testSetterGetterCategories() {
        Cocktail cocktail = new Cocktail();
        List<CocktailCategorie> categories = Arrays.asList(CocktailCategorie.FRUITE, CocktailCategorie.SUCRE);
        cocktail.setCategories(categories);
        Assertions.assertEquals(categories, cocktail.getCategories());
    }

    @Test
    public void testCategorieObligatoire() {
        Cocktail cocktail = new Cocktail();
        cocktail.setNom("Test");
        cocktail.setIngredients(Collections.singletonList("Test"));
        cocktail.setPrixS(1.0);
        cocktail.setPrixM(1.0);
        cocktail.setPrixL(1.0);
        // Pas de catégorie
        Assertions.assertTrue(cocktail.getCategories() == null || cocktail.getCategories().isEmpty());
    }
} 