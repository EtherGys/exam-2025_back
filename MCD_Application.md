# Modèle Conceptuel de Données (MCD) - Application de Gestion de Cocktails

## Entités Principales

### 1. CLIENT
**Attributs :**
- id (PK) : Long
- nom : String (2-50 caractères, obligatoire)
- prenom : String (2-50 caractères, obligatoire)
- email : String (unique, obligatoire, format email)
- mot_de_passe : String (min 6 caractères, obligatoire)
- adresse : String (obligatoire, TEXT)
- role : Role (obligatoire, défaut: USER)

**Relations :**
- 1 → N COMMANDE (un client peut avoir plusieurs commandes)
- 1 → N CARTE (un barmaker peut créer plusieurs cartes)

### 2. COCKTAIL
**Attributs :**
- id (PK) : Long
- nom : String (2-100 caractères, obligatoire)
- ingredients : List<String> (obligatoire)
- prix_s : Double (positif, obligatoire)
- prix_m : Double (positif, obligatoire)
- prix_l : Double (positif, obligatoire)
- categorie : String (obligatoire)

**Relations :**
- N → N CARTE (via table de liaison carte_cocktails)
- 1 → N LIGNE_DE_COMMANDE (un cocktail peut être dans plusieurs lignes de commande)
- 1 → N COCKTAIL_COMMANDE (un cocktail peut être dans plusieurs états de commande)

### 3. CARTE
**Attributs :**
- id (PK) : Long
- nom : String (obligatoire)
- date_creation : LocalDateTime (obligatoire)
- barmaker_id (FK) : Long (obligatoire)

**Relations :**
- N → 1 CLIENT (via barmaker_id)
- N → N COCKTAIL (via table de liaison carte_cocktails)

**Contraintes métier :**
- Un barmaker ne peut pas avoir deux cartes avec le même nom

### 4. COMMANDE
**Attributs :**
- id (PK) : Long
- client_id (FK) : Long (obligatoire)
- date_heure_commande : LocalDateTime (obligatoire)
- date_creation : LocalDateTime (obligatoire)
- statut_commande : StatutCommande (obligatoire, défaut: COMMANDEE)

**Relations :**
- N → 1 CLIENT (via client_id)
- 1 → N LIGNE_DE_COMMANDE (une commande peut avoir plusieurs lignes)
- 1 → N COCKTAIL_COMMANDE (une commande peut avoir plusieurs états de cocktails)

### 5. LIGNE_DE_COMMANDE
**Attributs :**
- id (PK) : Long
- commande_id (FK) : Long (obligatoire)
- cocktail_id (FK) : Long (obligatoire)
- taille : Taille (obligatoire)
- prix_taille : Double (obligatoire)
- quantite : Integer (obligatoire)
- statut_cocktail : StatutCocktail (obligatoire, défaut: COMMANDE)

**Relations :**
- N → 1 COMMANDE (via commande_id)
- N → 1 COCKTAIL (via cocktail_id)

### 6. COCKTAIL_COMMANDE
**Attributs :**
- id (PK) : Long
- cocktail_id (FK) : Long (obligatoire)
- commande_id (FK) : Long (obligatoire)
- etat_avancement : EtatAvancementCocktail (obligatoire, défaut: PREPARATION_INGREDIENTS)

**Relations :**
- N → 1 COCKTAIL (via cocktail_id)
- N → 1 COMMANDE (via commande_id)

## Énumérations

### ROLE
- USER
- BARMAKER

### TAILLE
- S
- M
- L

### STATUT_COMMANDE
- COMMANDEE
- EN_PREPARATION
- TERMINEE

### STATUT_COCKTAIL
- COMMANDE
- PREPARATION_INGREDIENTS
- ASSEMBLAGE
- DRESSAGE
- TERMINE

### ETAT_COMMANDE
- EN_CREATION
- COMMANDEE
- EN_PREPARATION
- TERMINEE

### ETAT_AVANCEMENT_COCKTAIL
- PREPARATION_INGREDIENTS
- ASSEMBLAGE
- DRESSAGE
- TERMINE

## Tables de Liaison

### carte_cocktails
- carte_id (FK) : Long
- cocktail_id (FK) : Long

## Diagramme des Relations

```
CLIENT (1) ←→ (N) COMMANDE
CLIENT (1) ←→ (N) CARTE (si role = BARMAKER)
CARTE (N) ←→ (N) COCKTAIL (via carte_cocktails)
COMMANDE (1) ←→ (N) LIGNE_DE_COMMANDE
COMMANDE (1) ←→ (N) COCKTAIL_COMMANDE
COCKTAIL (1) ←→ (N) LIGNE_DE_COMMANDE
COCKTAIL (1) ←→ (N) COCKTAIL_COMMANDE
```

## Contraintes Métier

1. **Authentification** : Seuls les clients authentifiés peuvent passer des commandes
2. **Rôles** : Seuls les BARMAKER peuvent créer des cartes
3. **Unicité des noms de cartes** : Un barmaker ne peut pas avoir deux cartes avec le même nom
4. **Prix par taille** : Chaque cocktail a des prix différents selon la taille (S, M, L)
5. **Suivi des états** : Les commandes et cocktails ont des états de progression
6. **Validation des données** : Contraintes de validation sur les champs obligatoires et formats

## Flux de Données

1. **Création de cocktails** : Les barmakers créent des cocktails avec leurs ingrédients et prix
2. **Création de cartes** : Les barmakers créent des cartes en associant des cocktails
3. **Passation de commandes** : Les clients passent des commandes en sélectionnant des cocktails avec tailles et quantités
4. **Suivi de préparation** : Les états des commandes et cocktails sont mis à jour pendant la préparation
5. **Finalisation** : Les commandes sont marquées comme terminées 