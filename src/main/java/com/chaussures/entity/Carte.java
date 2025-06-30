package com.chaussures.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cartes")
public class Carte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barmaker_id", nullable = false)
    @JsonIgnore
    private Client barmaker;

    @ManyToMany
    @JoinTable(
        name = "carte_cocktails",
        joinColumns = @JoinColumn(name = "carte_id"),
        inverseJoinColumns = @JoinColumn(name = "cocktail_id")
    )
    private List<Cocktail> cocktails;

    public Carte() {}

    public Carte(String nom, LocalDateTime dateCreation, Client barmaker, List<Cocktail> cocktails) {
        this.nom = nom;
        this.dateCreation = dateCreation;
        this.barmaker = barmaker;
        this.cocktails = cocktails;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    public Client getBarmaker() { return barmaker; }
    public void setBarmaker(Client barmaker) { this.barmaker = barmaker; }
    public List<Cocktail> getCocktails() { return cocktails; }
    public void setCocktails(List<Cocktail> cocktails) { this.cocktails = cocktails; }
} 