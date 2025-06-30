# Configuration PostgreSQL pour l'application Cocktails

## Prérequis

1. **Installer PostgreSQL** sur votre machine :
   - Windows : Télécharger depuis https://www.postgresql.org/download/windows/
   - Ou utiliser Docker : `docker run --name postgres-cocktails -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=cocktail_db -p 5432:5432 -d postgres:15`

## Configuration de la base de données

### Option 1 : Installation locale

1. **Créer la base de données** :
   ```sql
   CREATE DATABASE cocktail_db;
   ```

2. **Créer un utilisateur** (optionnel) :
   ```sql
   CREATE USER cocktails_user WITH PASSWORD 'votre_mot_de_passe';
   GRANT ALL PRIVILEGES ON DATABASE cocktail_db TO cocktails_user;
   ```

3. **Modifier application.properties** si nécessaire :
   ```properties
   spring.datasource.username=cocktails_user
   spring.datasource.password=votre_mot_de_passe
   ```

### Option 2 : Docker (recommandé)

```bash
# Démarrer PostgreSQL avec Docker
docker run --name postgres-cocktails \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=cocktail_db \
  -p 5432:5432 \
  -d postgres:15

# Pour arrêter le conteneur
docker stop postgres-cocktails

# Pour redémarrer le conteneur
docker start postgres-cocktails
```

## Migration des données

Si vous avez des données existantes dans MySQL, vous pouvez les exporter et les importer :

1. **Exporter depuis MySQL** :
   ```bash
   mysqldump -u root -p chaussures_db > backup_mysql.sql
   ```

2. **Convertir le script** (manuellement ou avec un outil de conversion)

3. **Importer dans PostgreSQL** :
   ```bash
   psql -U postgres -d cocktail_db -f backup_postgresql.sql
   ```

## Démarrage de l'application

1. **Nettoyer et recompiler** :
   ```bash
   mvn clean install
   ```

2. **Démarrer l'application** :
   ```bash
   mvn spring-boot:run
   ```

## Vérification

L'application devrait démarrer sans erreur et créer automatiquement les tables grâce à `spring.jpa.hibernate.ddl-auto=update`.

Vous pouvez vérifier la connexion en accédant à : http://localhost:8080/api/produits

## Outils de gestion PostgreSQL

- **pgAdmin** : Interface graphique pour gérer PostgreSQL
- **DBeaver** : Client universel de base de données
- **psql** : Client en ligne de commande PostgreSQL

## Commandes utiles

```bash
# Se connecter à PostgreSQL
psql -U postgres -d cocktail_db

# Lister les bases de données
\l

# Lister les tables
\dt

# Voir la structure d'une table
\d nom_table

# Quitter psql
\q
``` 