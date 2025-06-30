package com.chaussures.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cocktail_commandes")
public class CocktailCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cocktail_id", nullable = false)
    private Cocktail cocktail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat_avancement", nullable = false)
    private EtatAvancementCocktail etatAvancement = EtatAvancementCocktail.PREPARATION_INGREDIENTS;

    public CocktailCommande() {}

    public CocktailCommande(Cocktail cocktail, Commande commande, EtatAvancementCocktail etatAvancement) {
        this.cocktail = cocktail;
        this.commande = commande;
        this.etatAvancement = etatAvancement;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Cocktail getCocktail() { return cocktail; }
    public void setCocktail(Cocktail cocktail) { this.cocktail = cocktail; }
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
    public EtatAvancementCocktail getEtatAvancement() { return etatAvancement; }
    public void setEtatAvancement(EtatAvancementCocktail etatAvancement) { this.etatAvancement = etatAvancement; }

    @Override
    public String toString() {
        return "CocktailCommande{" +
                "id=" + id +
                ", cocktail=" + (cocktail != null ? cocktail.getId() : "null") +
                ", commande=" + (commande != null ? commande.getId() : "null") +
                ", etatAvancement=" + etatAvancement +
                '}';
    }
} 