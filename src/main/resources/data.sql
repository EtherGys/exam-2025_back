-- Données d'exemple pour l'application de gestion des commandes de chaussures

-- Insertion de clients d'exemple
INSERT INTO clients (nom, prenom, email, mot_de_passe, adresse) VALUES
('Dupont', 'Jean', 'jean.dupont@email.com', 'motdepasse123', '123 Rue de la Paix, 75001 Paris'),
('Martin', 'Sophie', 'sophie.martin@email.com', 'password123', '456 Avenue des Champs, 75008 Paris'),
('Bernard', 'Pierre', 'pierre.bernard@email.com', 'secret456', '789 Boulevard Saint-Germain, 75006 Paris'),
('Petit', 'Marie', 'marie.petit@email.com', 'mypassword', '321 Rue du Commerce, 75015 Paris'),
('Moreau', 'Lucas', 'lucas.moreau@email.com', 'secure789', '654 Avenue Victor Hugo, 75016 Paris');

-- Insertion de produits d'exemple (chaussures)
INSERT INTO produits (nom, pointure, prix) VALUES
('Nike Air Max 270', 38, 129.99),
('Nike Air Max 270', 39, 129.99),
('Nike Air Max 270', 40, 129.99),
('Nike Air Max 270', 41, 129.99),
('Nike Air Max 270', 42, 129.99),
('Adidas Ultraboost 22', 38, 179.99),
('Adidas Ultraboost 22', 39, 179.99),
('Adidas Ultraboost 22', 40, 179.99),
('Adidas Ultraboost 22', 41, 179.99),
('Adidas Ultraboost 22', 42, 179.99),
('Puma RS-X', 38, 89.99),
('Puma RS-X', 39, 89.99),
('Puma RS-X', 40, 89.99),
('Puma RS-X', 41, 89.99),
('Puma RS-X', 42, 89.99),
('New Balance 574', 38, 109.99),
('New Balance 574', 39, 109.99),
('New Balance 574', 40, 109.99),
('New Balance 574', 41, 109.99),
('New Balance 574', 42, 109.99),
('Converse Chuck Taylor', 38, 69.99),
('Converse Chuck Taylor', 39, 69.99),
('Converse Chuck Taylor', 40, 69.99),
('Converse Chuck Taylor', 41, 69.99),
('Converse Chuck Taylor', 42, 69.99);

-- Insertion de commandes d'exemple
INSERT INTO commandes (client_id, produit_id, pointure_choisie, date_commande) VALUES
(1, 3, 40, '2024-01-15 10:30:00'),
(1, 8, 40, '2024-01-20 14:15:00'),
(2, 6, 39, '2024-01-18 09:45:00'),
(2, 12, 39, '2024-01-25 16:20:00'),
(3, 1, 38, '2024-01-22 11:10:00'),
(3, 17, 40, '2024-01-28 13:30:00'),
(4, 4, 41, '2024-01-24 15:45:00'),
(4, 23, 41, '2024-01-30 10:20:00'),
(5, 2, 39, '2024-01-26 12:00:00'),
(5, 9, 41, '2024-02-01 17:30:00');

-- Exemple d'insertion avec image
-- INSERT INTO cocktails (nom, ingredient, prix_s, prix_m, prix_l, categorie, image) VALUES ('Mojito', 'Rhum, Menthe, Sucre, Citron, Eau gazeuse', 5.0, 7.0, 9.0, 'CLASSIC', 'https://exemple.com/mojito.jpg'); 