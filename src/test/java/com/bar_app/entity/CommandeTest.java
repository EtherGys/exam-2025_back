package com.bar_app.entity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.bar_app.entity.Client;
import com.bar_app.entity.Commande;
import com.bar_app.entity.LigneDeCommande;
import com.bar_app.entity.Role;
import com.bar_app.entity.StatutCommande;

public class CommandeTest {
    @Test
    public void testCreationCommande() {
        Client client = new Client("Martin", "Sophie", "sophie@email.com", "pass123", "1 rue de Paris", Role.USER);
        LocalDateTime now = LocalDateTime.now();
        List<LigneDeCommande> lignes = Collections.emptyList();
        Commande commande = new Commande(client, now, StatutCommande.COMMANDEE, lignes);
        commande.setDateCreation(now);
        Assertions.assertEquals(client, commande.getClient());
        Assertions.assertEquals(now, commande.getDateHeureCommande());
        Assertions.assertEquals(now, commande.getDateCreation());
        Assertions.assertEquals(StatutCommande.COMMANDEE, commande.getStatutCommande());
        Assertions.assertEquals(0, commande.getLignesDeCommande().size());
    }

    @Test
    public void testSetters() {
        Commande commande = new Commande();
        Client client = new Client();
        commande.setClient(client);
        LocalDateTime now = LocalDateTime.now();
        commande.setDateHeureCommande(now);
        commande.setDateCreation(now);
        commande.setStatutCommande(StatutCommande.EN_PREPARATION);
        List<LigneDeCommande> lignes = Arrays.asList(new LigneDeCommande(), new LigneDeCommande());
        commande.setLignesDeCommande(lignes);
        Assertions.assertEquals(client, commande.getClient());
        Assertions.assertEquals(now, commande.getDateHeureCommande());
        Assertions.assertEquals(now, commande.getDateCreation());
        Assertions.assertEquals(StatutCommande.EN_PREPARATION, commande.getStatutCommande());
        Assertions.assertEquals(2, commande.getLignesDeCommande().size());
    }
} 