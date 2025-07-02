package com.bar_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bar_app.entity.Client;
import com.bar_app.entity.Cocktail;
import com.bar_app.entity.CocktailCategorie;
import com.bar_app.entity.Commande;
import com.bar_app.entity.Role;
import com.bar_app.repository.ClientRepository;
import com.bar_app.repository.CocktailRepository;
import com.bar_app.repository.CommandeRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CocktailRepository cocktailRepository;

    @Autowired
    private CommandeRepository commandeRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialiser les données seulement si la base est vide
        if (clientRepository.count() == 0) {
            initializeData();
        }
    }

    private void initializeData() {
        System.out.println("Initialisation des données d'exemple...");

        // Créer des clients d'exemple
        Client client1 = new Client("Dupont", "Jean", "jean.dupont@email.com", "motdepasse123",
                "123 Rue de la Paix, 75001 Paris", Role.USER);
        Client client2 = new Client("Martin", "Sophie", "sophie.martin@email.com", "password123",
                "456 Avenue des Champs, 75008 Paris", Role.BARMAKER);
        Client client3 = new Client("Bernard", "Pierre", "pierre.bernard@email.com", "secret456",
                "789 Boulevard Saint-Germain, 75006 Paris", Role.USER);

        clientRepository.save(client1);
        clientRepository.save(client2);
        clientRepository.save(client3);

        // Créer des cocktails d'exemple
        Cocktail cocktail1 = new Cocktail("Mojito",
                java.util.Arrays.asList("Rhum", "Menthe", "Sucre", "Citron vert", "Eau gazeuse"), 6.0, 8.0, 10.0,
                java.util.List.of(CocktailCategorie.CLASSIQUE, CocktailCategorie.RAFRAICHISSANT), null, null);
        Cocktail cocktail2 = new Cocktail("Virgin Mojito",
                java.util.Arrays.asList("Menthe", "Sucre", "Citron vert", "Eau gazeuse"), 5.0, 7.0, 9.0,
                java.util.List.of(CocktailCategorie.SANS_ALCOOL, CocktailCategorie.RAFRAICHISSANT), null, null);
        Cocktail cocktail3 = new Cocktail("Cosmopolitan",
                java.util.Arrays.asList("Vodka", "Triple sec", "Cranberry", "Citron vert"), 7.0, 9.0, 11.0,
                java.util.List.of(CocktailCategorie.CLASSIQUE, CocktailCategorie.SUCRE), null, null);
        Cocktail cocktail4 = new Cocktail("Pina Colada",
                java.util.Arrays.asList("Rhum", "Lait de coco", "Jus d'ananas"), 7.0, 9.0, 11.0,
                java.util.List.of(CocktailCategorie.CLASSIQUE, CocktailCategorie.FRUITE, CocktailCategorie.CREMEUX),
                null, null);
        Cocktail cocktail5 = new Cocktail("Création du Bar",
                java.util.Arrays.asList("Gin", "Sirop de violette", "Tonic"), 8.0, 10.0, 12.0,
                java.util.List.of(CocktailCategorie.MODERNE, CocktailCategorie.FRUITE), null, null);
        cocktailRepository.save(cocktail1);
        cocktailRepository.save(cocktail2);
        cocktailRepository.save(cocktail3);
        cocktailRepository.save(cocktail4);
        cocktailRepository.save(cocktail5);

        // Créer des commandes d'exemple
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        // Commande 1
        Commande commande1 = new Commande(client1, com.bar_app.entity.StatutCommande.COMMANDEE,
                new java.util.ArrayList<>());
        commande1 = commandeRepository.save(commande1);
        com.bar_app.entity.LigneDeCommande ldc1 = new com.bar_app.entity.LigneDeCommande(commande1, cocktail1,
                com.bar_app.entity.Taille.M, 8.0, 2, com.bar_app.entity.StatutCocktail.COMMANDE);
        commande1.setLignesDeCommande(java.util.List.of(ldc1));
        commandeRepository.save(commande1);
        // Commande 2
        Commande commande2 = new Commande(client2, com.bar_app.entity.StatutCommande.COMMANDEE,
                new java.util.ArrayList<>());
        commande2 = commandeRepository.save(commande2);
        com.bar_app.entity.LigneDeCommande ldc2 = new com.bar_app.entity.LigneDeCommande(commande2, cocktail2,
                com.bar_app.entity.Taille.L, 9.0, 1, com.bar_app.entity.StatutCocktail.COMMANDE);
        commande2.setLignesDeCommande(java.util.List.of(ldc2));
        commandeRepository.save(commande2);
        // Commande 3
        Commande commande3 = new Commande(client3, com.bar_app.entity.StatutCommande.COMMANDEE,
                new java.util.ArrayList<>());
        commande3 = commandeRepository.save(commande3);
        com.bar_app.entity.LigneDeCommande ldc3 = new com.bar_app.entity.LigneDeCommande(commande3, cocktail3,
                com.bar_app.entity.Taille.S, 7.0, 3, com.bar_app.entity.StatutCocktail.COMMANDE);
        commande3.setLignesDeCommande(java.util.List.of(ldc3));
        commandeRepository.save(commande3);
        // Commande 4
        Commande commande4 = new Commande(client1, com.bar_app.entity.StatutCommande.COMMANDEE,
                new java.util.ArrayList<>());
        commande4 = commandeRepository.save(commande4);
        com.bar_app.entity.LigneDeCommande ldc4 = new com.bar_app.entity.LigneDeCommande(commande4, cocktail4,
                com.bar_app.entity.Taille.M, 9.0, 1, com.bar_app.entity.StatutCocktail.COMMANDE);
        commande4.setLignesDeCommande(java.util.List.of(ldc4));
        commandeRepository.save(commande4);
        // Commande 5
        Commande commande5 = new Commande(client2, com.bar_app.entity.StatutCommande.COMMANDEE,
                new java.util.ArrayList<>());
        commande5 = commandeRepository.save(commande5);
        com.bar_app.entity.LigneDeCommande ldc5 = new com.bar_app.entity.LigneDeCommande(commande5, cocktail5,
                com.bar_app.entity.Taille.L, 12.0, 2, com.bar_app.entity.StatutCocktail.COMMANDE);
        commande5.setLignesDeCommande(java.util.List.of(ldc5));
        commandeRepository.save(commande5);

        System.out.println("Données d'exemple initialisées avec succès !");
        System.out.println("- " + clientRepository.count() + " clients créés");
        System.out.println("- " + cocktailRepository.count() + " cocktails créés");
        System.out.println("- " + commandeRepository.count() + " commandes créées");
    }
}