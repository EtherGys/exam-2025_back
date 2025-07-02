package com.bar_app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bar_app.controller.CommandeController.LigneDeCommandeItem;
import com.bar_app.entity.Client;
import com.bar_app.entity.Cocktail;
import com.bar_app.entity.Commande;
import com.bar_app.entity.LigneDeCommande;
import com.bar_app.entity.StatutCocktail;
import com.bar_app.entity.StatutCommande;
import com.bar_app.entity.Taille;
import com.bar_app.repository.CommandeRepository;
import com.bar_app.repository.LigneDeCommandeRepository;

@Service
public class CommandeService {
    
    @Autowired
    private CommandeRepository commandeRepository;
    
    @Autowired
    private ClientService clientService;
    
    @Autowired
    private CocktailService cocktailService;
    
    @Autowired
    private LigneDeCommandeRepository ligneDeCommandeRepository;
    
    /**
     * Récupère toutes les commandes
     */
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }
    
    /**
     * Récupère une commande par son ID
     */
    public Optional<Commande> getCommandeById(Long id) {
        return commandeRepository.findById(id);
    }
    
    /**
     * Crée une nouvelle commande
     */
    public Commande createCommande(Long clientId, List<LigneDeCommandeItem> lignesDeCommandeItems) {
        Client client = clientService.getClientById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'ID: " + clientId));
        Commande commande = new Commande();
        commande.setClient(client);
        commande.setDateCreation(LocalDateTime.now());
        commande.setStatutCommande(StatutCommande.COMMANDEE);
        List<LigneDeCommande> lignes = new ArrayList<>();
        for (LigneDeCommandeItem item : lignesDeCommandeItems) {
            Cocktail cocktail = cocktailService.getCocktailById(item.getCocktailId())
                    .orElseThrow(() -> new RuntimeException("Cocktail non trouvé avec l'ID: " + item.getCocktailId()));
            Taille taille = Taille.valueOf(item.getTaille());
            StatutCocktail statut = StatutCocktail.COMMANDE;
            if (item.getStatutCocktail() != null) {
                statut = StatutCocktail.valueOf(item.getStatutCocktail());
            }
            LigneDeCommande ldc = new LigneDeCommande();
            ldc.setCommande(commande);
            ldc.setCocktail(cocktail);
            ldc.setNomCocktail(cocktail.getNom());
            ldc.setTaille(taille);
            ldc.setPrixTaille(item.getPrixTaille());
            ldc.setQuantite(item.getQuantite());
            ldc.setStatutCocktail(statut);
            lignes.add(ldc);
        }
        commande.setLignesDeCommande(lignes);
        updateStatutCommandeSelonLignes(commande);
        return commandeRepository.save(commande);
    }
    
    /**
     * Met à jour une commande existante
     */
    public Commande updateCommande(Long id, Long clientId, List<LigneDeCommandeItem> lignesDeCommandeItems) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID: " + id));
        Client client = clientService.getClientById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'ID: " + clientId));
        commande.setClient(client);
        List<LigneDeCommande> lignes = new ArrayList<>();
        for (LigneDeCommandeItem item : lignesDeCommandeItems) {
            Cocktail cocktail = cocktailService.getCocktailById(item.getCocktailId())
                    .orElseThrow(() -> new RuntimeException("Cocktail non trouvé avec l'ID: " + item.getCocktailId()));
            Taille taille = Taille.valueOf(item.getTaille());
            StatutCocktail statut = StatutCocktail.COMMANDE;
            if (item.getStatutCocktail() != null) {
                statut = StatutCocktail.valueOf(item.getStatutCocktail());
            }
            LigneDeCommande ldc = new LigneDeCommande();
            ldc.setCommande(commande);
            ldc.setCocktail(cocktail);
            ldc.setNomCocktail(cocktail.getNom());
            ldc.setTaille(taille);
            ldc.setPrixTaille(item.getPrixTaille());
            ldc.setQuantite(item.getQuantite());
            ldc.setStatutCocktail(statut);
            lignes.add(ldc);
        }
        commande.setLignesDeCommande(lignes);
        updateStatutCommandeSelonLignes(commande);
        return commandeRepository.save(commande);
    }
    
    /**
     * Supprime une commande
     */
    public void deleteCommande(Long id) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID: " + id));
        commandeRepository.delete(commande);
    }
    
    /**
     * Récupère toutes les commandes d'un client
     */
    public List<Commande> getCommandesByClientId(Long clientId) {
        return commandeRepository.findByClientId(clientId);
    }
    
    /**
     * Récupère toutes les commandes d'un cocktail
     */
    public List<Commande> getCommandesByCocktailId(Long cocktailId) {
        return commandeRepository.findByCocktailId(cocktailId);
    }
    
    
    
    /**
     * Vérifie si une commande existe par son ID
     */
    public boolean commandeExists(Long id) {
        return commandeRepository.existsById(id);
    }
    
    private void updateStatutCommandeSelonLignes(Commande commande) {
        if (commande.getLignesDeCommande() != null && !commande.getLignesDeCommande().isEmpty()
            && commande.getLignesDeCommande().stream().allMatch(l -> l.getStatutCocktail() == StatutCocktail.TERMINE)) {
            commande.setStatutCommande(StatutCommande.TERMINEE);
        } else if (commande.getLignesDeCommande() != null && commande.getLignesDeCommande().stream().anyMatch(l -> l.getStatutCocktail() != StatutCocktail.COMMANDE)) {
            commande.setStatutCommande(StatutCommande.EN_PREPARATION);
        } else {
            commande.setStatutCommande(StatutCommande.COMMANDEE);
        }
    }
    
    /**
     * Récupère les lignes de commande non terminées associées à un barmaker
     */
    public List<LigneDeCommande> getLignesNonTermineesByBarmaker(Long barmakerId) {
        return ligneDeCommandeRepository.findNonTermineesByBarmakerId(barmakerId);
    }

    public LigneDeCommande updateStatutLigneDeCommande(Long ligneId, StatutCocktail nouveauStatut) {
        LigneDeCommande ldc = ligneDeCommandeRepository.findById(ligneId)
            .orElseThrow(() -> new RuntimeException("Ligne de commande non trouvée"));
        ldc.setStatutCocktail(nouveauStatut);
        ligneDeCommandeRepository.save(ldc);

        Commande commande = ldc.getCommande();

        // Si le statut est > COMMANDE mais != TERMINE, passer la commande à EN_PREPARATION
        if (nouveauStatut != StatutCocktail.COMMANDE && nouveauStatut != StatutCocktail.TERMINE) {
            commande.setStatutCommande(StatutCommande.EN_PREPARATION);
            commandeRepository.save(commande);
        }

        // Si le statut est TERMINE, vérifier les autres lignes
        if (nouveauStatut == StatutCocktail.TERMINE) {
            boolean toutesTerminees = commande.getLignesDeCommande().stream()
                .allMatch(l -> l.getStatutCocktail() == StatutCocktail.TERMINE);
            if (toutesTerminees) {
                commande.setStatutCommande(StatutCommande.TERMINEE);
                commandeRepository.save(commande);
            }
        }
        return ldc;
    }
} 