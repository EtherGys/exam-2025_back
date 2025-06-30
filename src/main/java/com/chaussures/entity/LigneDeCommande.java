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
@Table(name = "ligne_de_commande")
public class LigneDeCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cocktail_id", nullable = false)
    private Cocktail cocktail;

    @Enumerated(EnumType.STRING)
    @Column(name = "taille", nullable = false)
    private Taille taille;

    @Column(name = "prix_taille", nullable = false)
    private Double prixTaille;

    @Column(name = "quantite", nullable = false)
    private Integer quantite;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_cocktail", nullable = false)
    private StatutCocktail statutCocktail = StatutCocktail.COMMANDE;

    public LigneDeCommande() {}

    public LigneDeCommande(Commande commande, Cocktail cocktail, Taille taille, Double prixTaille, Integer quantite, StatutCocktail statutCocktail) {
        this.commande = commande;
        this.cocktail = cocktail;
        this.taille = taille;
        this.prixTaille = prixTaille;
        this.quantite = quantite;
        this.statutCocktail = statutCocktail;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
    public Cocktail getCocktail() { return cocktail; }
    public void setCocktail(Cocktail cocktail) { this.cocktail = cocktail; }
    public Taille getTaille() { return taille; }
    public void setTaille(Taille taille) { this.taille = taille; }
    public Double getPrixTaille() { return prixTaille; }
    public void setPrixTaille(Double prixTaille) { this.prixTaille = prixTaille; }
    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }
    public StatutCocktail getStatutCocktail() { return statutCocktail; }
    public void setStatutCocktail(StatutCocktail statutCocktail) { this.statutCocktail = statutCocktail; }

    @Override
    public String toString() {
        return "LigneDeCommande{" +
                "id=" + id +
                ", commande=" + (commande != null ? commande.getId() : "null") +
                ", cocktail=" + (cocktail != null ? cocktail.getId() : "null") +
                ", taille=" + taille +
                ", prixTaille=" + prixTaille +
                ", quantite=" + quantite +
                ", statutCocktail=" + statutCocktail +
                '}';
    }
} 