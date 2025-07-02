package com.bar_app.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.bar_app.controller.CommandeController.LigneDeCommandeItem;
import com.bar_app.entity.Client;
import com.bar_app.entity.Cocktail;
import com.bar_app.entity.Commande;
import com.bar_app.entity.LigneDeCommande;
import com.bar_app.entity.StatutCocktail;
import com.bar_app.repository.CommandeRepository;
import com.bar_app.repository.LigneDeCommandeRepository;

class CommandeServiceTest {
    @Mock
    private CommandeRepository commandeRepository;
    @Mock
    private ClientService clientService;
    @Mock
    private CocktailService cocktailService;
    @Mock
    private LigneDeCommandeRepository ligneDeCommandeRepository;
    @InjectMocks
    private CommandeService commandeService;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void getAllCommandes_returnsList() {
        List<Commande> commandes = List.of(new Commande());
        when(commandeRepository.findAll()).thenReturn(commandes);
        assertEquals(commandes, commandeService.getAllCommandes());
    }

    @Test
    void getCommandeById_found() {
        Commande commande = new Commande();
        when(commandeRepository.findById(1L)).thenReturn(Optional.of(commande));
        assertTrue(commandeService.getCommandeById(1L).isPresent());
    }

    @Test
    void createCommande_creates() {
        Client client = new Client();
        when(clientService.getClientById(1L)).thenReturn(Optional.of(client));
        Cocktail cocktail = new Cocktail();
        when(cocktailService.getCocktailById(anyLong())).thenReturn(Optional.of(cocktail));
        when(commandeRepository.save(any())).thenReturn(new Commande());
        LigneDeCommandeItem item = new LigneDeCommandeItem();
        item.setCocktailId(1L); item.setTaille("S"); item.setPrixTaille(5.0); item.setQuantite(1);
        List<LigneDeCommandeItem> items = List.of(item);
        assertNotNull(commandeService.createCommande(1L, items));
    }

    @Test
    void deleteCommande_deletes() {
        Commande commande = new Commande();
        when(commandeRepository.findById(1L)).thenReturn(Optional.of(commande));
        doNothing().when(commandeRepository).delete(commande);
        assertDoesNotThrow(() -> commandeService.deleteCommande(1L));
    }

    @Test
    void commandeExists_true() {
        when(commandeRepository.existsById(1L)).thenReturn(true);
        assertTrue(commandeService.commandeExists(1L));
    }

    @Test
    void updateCommande_success() {
        Commande commande = new Commande();
        commande.setId(1L);
        Client client = new Client();
        client.setId(2L);
        when(commandeRepository.findById(1L)).thenReturn(Optional.of(commande));
        when(clientService.getClientById(2L)).thenReturn(Optional.of(client));
        when(commandeRepository.save(any())).thenReturn(commande);
        List<LigneDeCommandeItem> items = List.of();
        Commande result = commandeService.updateCommande(1L, 2L, items);
        assertEquals(client, result.getClient());
    }

    @Test
    void updateCommande_clientNotFound() {
        Commande commande = new Commande();
        commande.setId(1L);
        when(commandeRepository.findById(1L)).thenReturn(Optional.of(commande));
        when(clientService.getClientById(2L)).thenReturn(Optional.empty());
        List<LigneDeCommandeItem> items = List.of();
        assertThrows(RuntimeException.class, () -> commandeService.updateCommande(1L, 2L, items));
    }

    @Test
    void getCommandesByClientId_returnsList() {
        List<Commande> commandes = List.of(new Commande());
        when(commandeRepository.findByClientId(1L)).thenReturn(commandes);
        assertEquals(commandes, commandeService.getCommandesByClientId(1L));
    }

    @Test
    void getCommandesByCocktailId_returnsList() {
        List<Commande> commandes = List.of(new Commande());
        when(commandeRepository.findByCocktailId(1L)).thenReturn(commandes);
        assertEquals(commandes, commandeService.getCommandesByCocktailId(1L));
    }

    @Test
    void getLignesNonTermineesByBarmaker_returnsList() {
        List<LigneDeCommande> lignes = List.of(new LigneDeCommande());
        when(this.ligneDeCommandeRepository.findNonTermineesByBarmakerId(1L)).thenReturn(lignes);
        assertEquals(lignes, commandeService.getLignesNonTermineesByBarmaker(1L));
    }

    @Test
    void updateStatutLigneDeCommande_success() {
        LigneDeCommande ldc = new LigneDeCommande();
        Commande commande = new Commande();
        ldc.setCommande(commande);
        when(this.ligneDeCommandeRepository.findById(1L)).thenReturn(Optional.of(ldc));
        when(this.ligneDeCommandeRepository.save(any())).thenReturn(ldc);
        when(commandeRepository.save(any())).thenReturn(commande);
        LigneDeCommande result = commandeService.updateStatutLigneDeCommande(1L, StatutCocktail.PREPARATION_INGREDIENTS);
        assertEquals(ldc, result);
    }

    @Test
    void updateStatutLigneDeCommande_notFound() {
        when(this.ligneDeCommandeRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> commandeService.updateStatutLigneDeCommande(2L, StatutCocktail.PREPARATION_INGREDIENTS));
    }
} 