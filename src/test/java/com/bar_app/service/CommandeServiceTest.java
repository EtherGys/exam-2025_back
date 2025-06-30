package com.bar_app.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.bar_app.repository.CommandeRepository;
import com.bar_app.service.ClientService;
import com.bar_app.service.CocktailService;
import com.bar_app.service.CommandeService;

class CommandeServiceTest {
    @Mock
    private CommandeRepository commandeRepository;
    @Mock
    private ClientService clientService;
    @Mock
    private CocktailService cocktailService;
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
} 