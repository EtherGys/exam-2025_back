package com.bar_app.dto;

import java.util.List;

public class CocktailRequest {
    private String nom;
    private List<String> ingredients;
    private Double prixS;
    private Double prixM;
    private Double prixL;
    private List<String> categories;
    private String image;
    private String description;
    private Long carteId;

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public List<String> getIngredients() { return ingredients; }
    public void setIngredients(List<String> ingredients) { this.ingredients = ingredients; }
    public Double getPrixS() { return prixS; }
    public void setPrixS(Double prixS) { this.prixS = prixS; }
    public Double getPrixM() { return prixM; }
    public void setPrixM(Double prixM) { this.prixM = prixM; }
    public Double getPrixL() { return prixL; }
    public void setPrixL(Double prixL) { this.prixL = prixL; }
    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getCarteId() { return carteId; }
    public void setCarteId(Long carteId) { this.carteId = carteId; }
} 