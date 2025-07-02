package com.bar_app.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cocktails")
public class Cocktail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du cocktail est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom du cocktail doit contenir entre 2 et 100 caractères")
    @Column(name = "nom", nullable = false)
    private String nom;

    @ElementCollection
    @Column(name = "ingredient", nullable = false)
    private List<String> ingredients;

    @NotNull(message = "Le prix S est obligatoire")
    @Positive(message = "Le prix doit être positif")
    @Column(name = "prix_s", nullable = false)
    private Double prixS;

    @NotNull(message = "Le prix M est obligatoire")
    @Positive(message = "Le prix doit être positif")
    @Column(name = "prix_m", nullable = false)
    private Double prixM;

    @NotNull(message = "Le prix L est obligatoire")
    @Positive(message = "Le prix doit être positif")
    @Column(name = "prix_l", nullable = false)
    private Double prixL;

    @NotEmpty(message = "Le cocktail doit avoir au moins une catégorie")
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = "categorie", nullable = false)
    private List<CocktailCategorie> categories;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    public Cocktail() {
    }

    public Cocktail(String nom, List<String> ingredients, Double prixS, Double prixM, Double prixL,
            List<CocktailCategorie> categories, String image, String description) {
        this.nom = nom;
        this.ingredients = ingredients;
        this.prixS = prixS;
        this.prixM = prixM;
        this.prixL = prixL;
        this.categories = categories;
        this.image = image;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public Double getPrixS() {
        return prixS;
    }

    public void setPrixS(Double prixS) {
        this.prixS = prixS;
    }

    public Double getPrixM() {
        return prixM;
    }

    public void setPrixM(Double prixM) {
        this.prixM = prixM;
    }

    public Double getPrixL() {
        return prixL;
    }

    public void setPrixL(Double prixL) {
        this.prixL = prixL;
    }

    public List<CocktailCategorie> getCategories() {
        return categories;
    }

    public void setCategories(List<CocktailCategorie> categories) {
        this.categories = categories;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Cocktail{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", ingredients=" + ingredients +
                ", prixS=" + prixS +
                ", prixM=" + prixM +
                ", prixL=" + prixL +
                ", categories=" + categories +
                ", image='" + image + '\'' +
                '}';
    }
}