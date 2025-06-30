package com.chaussures.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "commandes")
public class Commande {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Le client est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    @JsonIgnore
    private Client client;
    
    @Column(name = "date_heure_commande", nullable = false)
    private LocalDateTime dateHeureCommande;
    
    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statut_commande", nullable = false)
    private StatutCommande statutCommande = StatutCommande.COMMANDEE;
    
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<LigneDeCommande> lignesDeCommande;
    
    // Constructeurs
    public Commande() {}
    
    public Commande(Client client, LocalDateTime dateHeureCommande, StatutCommande statutCommande, List<LigneDeCommande> lignesDeCommande) {
        this.client = client;
        this.dateHeureCommande = dateHeureCommande;
        this.dateCreation = LocalDateTime.now();
        this.statutCommande = statutCommande;
        this.lignesDeCommande = lignesDeCommande;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Client getClient() {
        return client;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }
    
    public LocalDateTime getDateHeureCommande() {
        return dateHeureCommande;
    }
    
    public void setDateHeureCommande(LocalDateTime dateHeureCommande) {
        this.dateHeureCommande = dateHeureCommande;
    }
    
    public LocalDateTime getDateCreation() {
        return dateCreation;
    }
    
    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    public StatutCommande getStatutCommande() {
        return statutCommande;
    }
    
    public void setStatutCommande(StatutCommande statutCommande) {
        this.statutCommande = statutCommande;
    }
    
    public List<LigneDeCommande> getLignesDeCommande() {
        return lignesDeCommande;
    }
    
    public void setLignesDeCommande(List<LigneDeCommande> lignesDeCommande) {
        this.lignesDeCommande = lignesDeCommande;
    }
    
    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", client=" + (client != null ? client.getId() : "null") +
                ", dateHeureCommande=" + dateHeureCommande +
                ", dateCreation=" + dateCreation +
                ", statutCommande=" + statutCommande +
                ", lignesDeCommande=" + (lignesDeCommande != null ? lignesDeCommande.size() : "null") +
                '}';
    }
} 