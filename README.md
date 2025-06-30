# Application de Gestion des Commandes de Chaussures

Cette application Spring Boot permet de gérer les commandes de chaussures en ligne avec une base de données PostgreSQL.

## 🚀 Fonctionnalités

- **Gestion des Clients** : CRUD complet pour les clients
- **Gestion des Produits** : CRUD complet pour les chaussures
- **Gestion des Commandes** : CRUD complet pour les commandes
- **Recherche avancée** : Recherche par nom, pointure, prix, etc.
- **Validation des données** : Validation des pointures (38, 39, 40, 41, 42)
- **API REST** : Interface REST complète pour toutes les opérations

## 🛠️ Technologies utilisées

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **PostgreSQL 15**
- **Maven**

## 📋 Prérequis

- Java 17 ou supérieur
- PostgreSQL 15 ou supérieur (ou Docker)
- Maven 3.6 ou supérieur

## 🗄️ Configuration de la base de données

### Option 1 : Installation locale PostgreSQL

1. **Installer PostgreSQL** depuis https://www.postgresql.org/download/
2. **Créer la base de données** :
   ```sql
   CREATE DATABASE cocktail_db;
   ```

### Option 2 : Docker (recommandé)

```bash
docker run --name postgres-cocktails \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=cocktail_db \
  -p 5432:5432 \
  -d postgres:15
```

Pour plus de détails, consultez le fichier `POSTGRESQL_SETUP.md`.

## 🗄️ Structure de la base de données

### Table `clients`
- `id` (Long, Primary Key, Auto-increment)
- `nom` (String, Not Null)
- `prenom` (String, Not Null)
- `email` (String, Not Null, Unique)
- `mot_de_passe` (String, Not Null)
- `adresse` (Text, Not Null)

### Table `cocktails`
- `id` (Long, Primary Key, Auto-increment)
- `nom` (String, Not Null)
- `ingredients` (List<String>, Not Null)
- `prix_s` (Double, Not Null)
- `prix_m` (Double, Not Null)
- `prix_l` (Double, Not Null)
- `categorie` (String, Not Null)

### Table `commandes`
- `id` (Long, Primary Key, Auto-increment)
- `client_id` (Long, Foreign Key vers clients.id)
- `cocktail_id` (Long, Foreign Key vers cocktails.id)
- `pointure_choisie` (Integer, Not Null)
- `date_commande` (DateTime, Not Null)

## ⚙️ Configuration

### Configuration de la base de données

Le fichier `src/main/resources/application.properties` est déjà configuré pour PostgreSQL :

```properties
# Configuration de la base de données PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/cocktail_db
spring.datasource.username=postgres
spring.datasource.password=1234
spring.datasource.driver-class-name=org.postgresql.Driver
```

L'application créera automatiquement les tables grâce à `spring.jpa.hibernate.ddl-auto=update`.

## 🚀 Démarrage de l'application

### 1. Compilation et lancement

```bash
# Compiler le projet
mvn clean compile

# Lancer l'application
mvn spring-boot:run
```

### 2. Accès à l'application

L'application sera accessible à l'adresse : `http://localhost:8080`

## 📚 API REST

### Base URL
```
http://localhost:8080/api
```

### 🔧 Gestion des Clients

#### Récupérer tous les clients
```http
GET /api/clients
```

#### Récupérer un client par ID
```http
GET /api/clients/{id}
```

#### Récupérer un client par email
```http
GET /api/clients/email/{email}
```

#### Créer un nouveau client
```http
POST /api/clients
Content-Type: application/json

{
  "nom": "Dupont",
  "prenom": "Jean",
  "email": "jean.dupont@email.com",
  "motDePasse": "motdepasse123",
  "adresse": "123 Rue de la Paix, 75001 Paris",
  "role": "USER"
}
```

> Le champ `role` est optionnel. Par défaut, la valeur sera `USER` si non précisé. Les valeurs possibles sont :
> - `USER` : utilisateur classique
> - `BARMAKER` : barman/barmaid (accès avancés)

#### Mettre à jour un client
```http
PUT /api/clients/{id}
Content-Type: application/json

{
  "nom": "Dupont",
  "prenom": "Jean-Pierre",
  "email": "jean-pierre.dupont@email.com",
  "motDePasse": "nouveaumotdepasse",
  "adresse": "456 Avenue des Champs, 75008 Paris"
}
```

#### Supprimer un client
```http
DELETE /api/clients/{id}
```

#### Rechercher des clients par nom
```http
GET /api/clients/search/nom/{nom}
```

#### Rechercher des clients par prénom
```http
GET /api/clients/search/prenom/{prenom}
```

### 🍸 Gestion des Cocktails

#### Récupérer tous les cocktails
```http
GET /api/cocktails
```

#### Récupérer un cocktail par ID
```http
GET /api/cocktails/{id}
```

#### Créer un nouveau cocktail
```http
POST /api/cocktails
Content-Type: application/json

{
  "nom": "Mojito",
  "ingredients": ["Rhum", "Menthe", "Sucre", "Citron vert", "Eau gazeuse"],
  "prixS": 6.0,
  "prixM": 8.0,
  "prixL": 10.0,
  "categorie": "Classiques"
}
```

#### Mettre à jour un cocktail
```http
PUT /api/cocktails/{id}
Content-Type: application/json

{
  "nom": "Virgin Mojito",
  "ingredients": ["Menthe", "Sucre", "Citron vert", "Eau gazeuse"],
  "prixS": 5.0,
  "prixM": 7.0,
  "prixL": 9.0,
  "categorie": "Sans alcool"
}
```

#### Supprimer un cocktail
```http
DELETE /api/cocktails/{id}
```

#### Rechercher des cocktails par nom
```http
GET /api/cocktails/search/nom/{nom}
```

#### Vérifier l'existence d'un cocktail
```http
GET /api/cocktails/exists/{id}
```

### 🛒 Gestion des Commandes

#### Récupérer toutes les commandes
```http
GET /api/commandes
```

#### Récupérer une commande par ID
```http
GET /api/commandes/{id}
```

#### Créer une nouvelle commande
```http
POST /api/commandes
Content-Type: application/json

{
  "clientId": 1,
  "produitId": 1,
  "pointureChoisie": 40
}
```

#### Mettre à jour une commande
```http
PUT /api/commandes/{id}
Content-Type: application/json

{
  "clientId": 1,
  "produitId": 2,
  "pointureChoisie": 41
}
```

#### Supprimer une commande
```http
DELETE /api/commandes/{id}
```

#### Récupérer les commandes d'un client
```http
GET /api/commandes/client/{clientId}
```

#### Récupérer les commandes d'un produit
```http
GET /api/commandes/produit/{produitId}
```

#### Récupérer les commandes par pointure choisie
```http
GET /api/commandes/pointure/{pointureChoisie}
```

#### Récupérer les commandes dans une période
```http
GET /api/commandes/date-range?dateDebut=2024-01-01T00:00:00&dateFin=2024-12-31T23:59:59
```

## 🔍 Exemples d'utilisation

### 1. Créer un client
```bash
curl -X POST http://localhost:8080/api/clients \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Martin",
    "prenom": "Sophie",
    "email": "sophie.martin@email.com",
    "motDePasse": "password123",
    "adresse": "789 Boulevard Saint-Germain, 75006 Paris"
  }'
```

### 2. Créer un produit
```bash
curl -X POST http://localhost:8080/api/produits \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Adidas Ultraboost",
    "pointure": 39,
    "prix": 179.99
  }'
```

### 3. Créer une commande
```bash
curl -X POST http://localhost:8080/api/commandes \
  -H "Content-Type: application/json" \
  -d '{
    "clientId": 1,
    "produitId": 1,
    "pointureChoisie": 39
  }'
```

## 🧪 Tests

Pour exécuter les tests :

```bash
mvn test
```

## 📝 Notes importantes

- Les pointures autorisées sont : 38, 39, 40, 41, 42
- L'email des clients doit être unique
- Les prix doivent être positifs
- Les dates de commande sont automatiquement générées

## 🐛 Dépannage

### Problème de connexion à la base de données
1. Vérifiez que PostgreSQL est démarré
2. Vérifiez les paramètres de connexion dans `application.properties`
3. Assurez-vous que l'utilisateur a les droits suffisants

### Erreur de validation
- Vérifiez que les pointures sont dans la liste autorisée
- Vérifiez que l'email n'est pas déjà utilisé
- Vérifiez que les prix sont positifs

## 📄 Licence

Ce projet est sous licence MIT. 