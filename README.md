# users-api

Cette application Spring Boot offre diverses fonctionnalités pour gérer des utilisateurs.

## Technologies et librairies utilisées

1. Java 17
2. Liquibase
2. Spring boot (rest, data, test, swagger)
3. Mockito
4. Docker & docker-compose
5. Jupiter
6. PostgreSQL

## Téléchargement des sources

Pour télécharger le code source de l'application, lancer la commande ci-dessous:
```bash
git clone https://github.com/bbenaouicha/users-api.git
```

## Lancement de l'application en local

Vous pouvez lancer l'application de deux manières différentes :
1. En utilisant votre IDE.
2. En utilisant Docker et docker-compose.

### Méthode 1 : Lancement à travers un IDE

#### Prérequis

Avant de lancer l'application, assurez-vous d'avoir les éléments suivants installés sur votre système :

- Java Development Kit (JDK) version 17.
- Docker
- Ports 8080 et 5433 de votre machine sont disponibles.

#### Procédure

1. Ouvrir votre IDE.
2. Importer le projet dans votre IDE en tant que projet Maven.
3. Trouver la classe principale `fr.kata.users.UsersApplication` et exécuter la méthode `main` définie dans cette classe.

### Méthode 2 : Utilisation de Docker et docker-compose

#### Prérequis

Avant de lancer l'application, assurez-vous d'avoir les éléments suivants installés sur votre système :
- Docker & docker-compose.
- Ports 8080 de votre machine est disponible.

#### Procédure

1. Naviguer jusqu'au répertoire principal du projet où se trouve le fichier `docker-compose.yml`.
2. Lancer la commande ci-dessous pour construire l'image docker liée à l'application et démarrer les conteneurs `db` et `app` :

```bash
docker-compose up --build
```
## Documentation API

Une fois l'application est lancée, la documentation de l'api est disponible ici `http://localhost:8080/swagger-ui/index.html` 
