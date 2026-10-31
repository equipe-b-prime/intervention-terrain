# 🛠️ API REST — Interventions Terrain & Gestion des Équipes Techniques

[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Neon_Cloud-336791?style=for-the-badge&logo=postgresql&logoColor=white)](https://neon.tech/)
[![Render](https://img.shields.io/badge/Render-Hosted-46E3B7?style=for-the-badge&logo=render&logoColor=black)](https://intervention-terrain.onrender.com/swagger-ui/index.html)
[![Swagger](https://img.shields.io/badge/Swagger-OpenAPI_3-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)](https://intervention-terrain.onrender.com/swagger-ui/index.html)
[![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)

---

## 📖 Introduction & Présentation du Projet

L'application **Interventions Terrain** est une API REST d'entreprise développée avec **Spring Boot (Java 21)**, **Spring Data JPA**, **PostgreSQL**, **Flyway** et documentée avec **SpringDoc OpenAPI 3 (Swagger UI)**.

Elle propose une solution complète et robuste pour la gestion logistique des interventions sur site et le pilotage des équipes d'intervention :
- 🏢 **Gestion des agences & des effectifs techniques** : Suivi de l'organigramme opérationnel, matricule unique par technicien, contrôle strict de mutation et d'intégrité lors de la suppression.
- 🎯 **Référentiel des compétences et certifications** : Gestion des dates d'obtention et d'expiration, détection et alerte nocturne planifiée des certifications arrivant à échéance sous 30 jours (F16).
- 👤 **Fiches clients & traçabilité des interventions** : Historique complet chronologique des passages chez chaque client et rattachement géographique par commune.
- 📅 **Planification & Cycle de vie des interventions** : 
  - Statut initial obligatoire à `PLANIFIEE`.
  - Contrôle anti-chevauchement des créneaux (rejet 409 Conflict si conflit d'agenda).
  - Contrôle des compétences requises : validation d'aptitude non expirée du technicien à la date de l'intervention (rejet 400).
  - Machine à états stricte horodatée (`PLANIFIEE` ➔ `EN_COURS` ➔ `TERMINEE` ou `ECHOUEE`, `ANNULEE`).
  - Reprogrammation directe d'une intervention de reprise (F15).
- 📊 **Restitution & Décisionnel** : Moteur de recherche des techniciens disponibles et qualifiés par type de mission (`v_technicien_disponible`), indicateurs d'occupation/charge par agence et analyse mensuelle du taux de réussite (`v_taux_reussite_agence`).

---

## 📌 Accès Rapide & Liens Swagger UI

L'application est **entièrement déployée en continu sur Render** et connectée à une base de données cloud **Neon PostgreSQL**. Elle est également conçue pour s'exécuter et se tester immédiatement en environnement local.

| Environnement | URL de Base API | Documentation Interactive Swagger UI | Spécification OpenAPI JSON |
| :--- | :--- | :--- | :--- |
| **🌐 En Ligne (Render + Neon)** | `https://intervention-terrain.onrender.com` | [Swagger UI En Ligne](https://intervention-terrain.onrender.com/swagger-ui/index.html) | [`/v3/api-docs`](https://intervention-terrain.onrender.com/v3/api-docs) |
| **💻 Local (PostgreSQL)** | `http://localhost:8080` | [Swagger UI Local](http://localhost:8080/swagger-ui/index.html) | [`/v3/api-docs`](http://localhost:8080/v3/api-docs) |

> 💡 **Remarque** : L'interface Swagger UI vous permet d'explorer l'ensemble des 19 fonctionnalités, de tester les endpoints interactivement et d'inspecter les schémas DTO (Request / Response) directement depuis votre navigateur.

---

## 🗄️ Architecture des Bases de Données & Migrations (Flyway)

La persistance des données repose sur une base relationnelle PostgreSQL, disponible sous deux modes synchronisés :

1. **Base de Données Cloud (Neon PostgreSQL)** :
   - Hébergement serverless managé sur **Neon** avec chiffrement SSL.
   - Haute disponibilité et sauvegardes automatiques.
2. **Base de Données Locale** :
   - Instance PostgreSQL locale accessible sur `jdbc:postgresql://localhost:5432/interventions_terrain`.
3. **Migrations Automatisées par Flyway** :
   - [`V1.0.1__DDL.sql`](file:///home/yokan/TAF/FORMATION/TESTO/interventionTerrain/src/main/resources/db/migration/V1.0.1__DDL.sql) : 
     - Création de 22 tables relationnelles : `agence`, `technicien`, `competence`, `technicien_competence`, `client`, `commune`, `departement`, `region`, `statut`, `intervention`, `creneau`, `type_intervention`, `intervention_creneaux`, `historique_statut`, `alerte_competence`, `depot`, `piece`, `stock`, `intervention_piece`, `intervention_technicien`, `equipe`.
     - Contraintes d'intégrité (unicité `uk_technicien_matricule`, `uk_competence_libelle`, `uk_agence_nom`, clés étrangères).
     - **3 Vues SQL analytiques** pour les restitutions complexes :
       - `v_technicien_disponible` : Vue matérialisant les disponibilités et compétences des techniciens.
       - `v_taux_reussite_agence` : Calcul du ratio des interventions terminées par agence et par type de prestation.
       - `v_intervention_stats` : Agrégats d'activité par créneau et par technicien.
   - [`V1.0.2__DML.sql`](file:///home/yokan/TAF/FORMATION/TESTO/interventionTerrain/src/main/resources/db/migration/V1.0.2__DML.sql) :
     - Initialisation des paramètres de référence (Statuts : `PLANIFIEE`, `EN_COURS`, `TERMINEE`, `ANNULEE`, `ECHOUEE`).
     - Injection d'un jeu de données complet de test (agences, compétences, techniciens, clients, interventions tous statuts) et synchronisation des séquences `IDENTITY`.

---

## 📬 Pièce Jointe : Collection Postman Complète

Pour simplifier vos campagnes de tests et vos démonstrations, une collection Postman complète et prête à l'emploi est fournie à la racine du projet :

📄 **[`postman.json`](file:///home/yokan/TAF/FORMATION/TESTO/interventionTerrain/postman.json)** *(également disponible sous `ZKANTAPI.postman_collection.json`)*

### 🚀 Importation et bascule en 1 clic :
1. Ouvrez **Postman**.
2. Cliquez sur le bouton **Import** et sélectionnez le fichier **`postman.json`**.
3. **Bascule instantanée En Ligne / Local** grâce à la variable de collection `baseUrl` (ou `loc`) :
   - **Pour tester l'API déployée en ligne (Render)** :
     - Valeur de `baseUrl` : `https://intervention-terrain.onrender.com`
   - **Pour tester l'API en local** :
     - Valeur de `baseUrl` : `http://localhost:8080`
4. L'arborescence des dossiers Postman couvre l'intégralité des 19 fonctionnalités (43 requêtes de test validées à 100%) :
   - **NIVEAU 1 — LE CRUD (F1 à F5)** : Agences, Techniciens, Compétences, Clients et Interventions.
   - **NIVEAU 2 — CONSULTATION ET RECHERCHE (F6 à F10)** : Effectif d'agence, compétences du technicien, historique client, filtrage par statut/période et planning hebdomadaire.
   - **NIVEAU 3 — RÈGLES MÉTIER (F11 à F16)** : Habilitations de techniciens, prévention anti-chevauchement (409), contrôle de qualification obligatoire (400), transitions de statuts horodatées et reprise d'intervention échouée.
   - **NIVEAU 4 — LA RESTITUTION (F17 à F19)** : Techniciens disponibles et qualifiés, mesure de la charge d'agence et calcul du taux de réussite mensuel.

---

## 🧪 Tests par Requêtes cURL (Ligne & Local)

Vous pouvez exécuter les commandes cURL ci-dessous indifféremment sur l'environnement distant ou en local en adaptant la variable `$BASE` :

```bash
# Cible En Ligne (Par défaut)
export BASE="https://intervention-terrain.onrender.com"

# Cible Locale
# export BASE="http://localhost:8080"
```

### 1️⃣ Niveau 1 : Le CRUD

* **Lister les agences (F1)** :
```bash
curl -X GET "$BASE/agences"
```

* **Créer une nouvelle agence (F1)** :
```bash
curl -X POST "$BASE/agences" \
     -H "Content-Type: application/json" \
     -d '{
       "nom": "Agence Abidjan Sud",
       "ville": "Abidjan",
       "responsable": "Kouadio Marc"
     }'
```

* **Créer un nouveau technicien avec matricule unique (F2)** :
```bash
curl -X POST "$BASE/techniciens" \
     -H "Content-Type: application/json" \
     -d '{
       "nom": "Traoré",
       "prenom": "Mamadou",
       "matricule": "TECH-099",
       "email": "m.traore@synelia.ci",
       "telephone": "0701020304",
       "agenceId": 1
     }'
```

* **Créer une compétence (F3)** :
```bash
curl -X POST "$BASE/competences" \
     -H "Content-Type: application/json" \
     -d '{
       "libelle": "Soudure Fibre Optique D3",
       "description": "Raccordement réseau fibre et certification photométrique"
     }'
```

* **Planifier une intervention dans le futur (F5)** :
```bash
curl -X POST "$BASE/interventions" \
     -H "Content-Type: application/json" \
     -d '{
       "clientId": 1,
       "technicienId": 1,
       "typeInterventionId": 1,
       "datePlanifiee": "2026-12-15",
       "heureDebut": "09:00:00",
       "heureFin": "11:30:00",
       "commentaire": "Pose et raccordement boîtier terminal optique"
     }'
```

---

### 2️⃣ Niveau 2 : Consultation & Recherche

* **Lister l'effectif d'une agence avec compétences valides (F6)** :
```bash
curl -X GET "$BASE/agences/1/techniciens"
```

* **Consulter les compétences d'un technicien (F7)** :
```bash
curl -X GET "$BASE/techniciens/1/competences"
```

* **Consulter l'historique des interventions d'un client (F8)** :
```bash
curl -X GET "$BASE/clients/1/interventions"
```

* **Filtrer les interventions par statut et période (F9)** :
```bash
curl -X GET "$BASE/interventions?statut=TERMINEE&du=2026-10-01&au=2026-10-31"
```

* **Consulter le planning d'un technicien sur une période (F10)** :
```bash
curl -X GET "$BASE/techniciens/1/planning?du=2026-10-01&au=2026-10-31"
```

---

### 3️⃣ Niveau 3 : Règles Métier

* **Associer une compétence à un technicien avec dates (F11)** :
```bash
curl -X POST "$BASE/techniciens/1/competences" \
     -H "Content-Type: application/json" \
     -d '{
       "competenceId": 14,
       "libelle": "Câblage Armoire",
       "dateObtention": "2024-01-01",
       "dateExpiration": "2028-01-01"
     }'
```

* **Faire évoluer le statut d'une intervention (F14 - Ex: EN_COURS ➔ TERMINEE)** :
```bash
curl -X POST "$BASE/interventions/5/statut?nouveauStatut=TERMINEE"
```

* **Programmer une intervention de reprise suite à un échec (F15)** :
```bash
curl -X POST "$BASE/interventions/16/reprise?datePlanifiee=2026-12-30"
```

---

### 4️⃣ Niveau 4 : Restitution & Décisionnel

* **Rechercher les techniciens disponibles et qualifiés pour un type d'intervention (F17)** :
```bash
curl -X GET "$BASE/techniciens/disponibles?debut=2026-12-01T08:00:00&fin=2026-12-01T12:00:00&type=Installation"
```

* **Mesurer la charge d'une agence (F18)** :
```bash
curl -X GET "$BASE/agences/1/charge?du=2026-10-01&au=2026-10-31"
```

* **Calculer le taux de réussite mensuel d'une agence (F19)** :
```bash
curl -X GET "$BASE/agences/1/taux-reussite?mois=2026-10"
```

---

## 📁 Dossier de Scripts & Tests Dédié (`test-docs/`)

Conformément aux bonnes pratiques de versionnement, les scripts de test d'intégration cURL et documentations associées sont regroupés dans un dossier dédié :

📂 **`test-docs/`**
- `curl_interventions_niveaux.sh` : Script shell exécutable avec coloration syntaxique permettant de dérouler tous les tests d'intégration en continu.
- `Tests_CURL_Interventions.md` : Guide exhaustif au format markdown des requêtes, structures JSON et codes HTTP.

> 🔒 **Configuration `.gitignore`** :  
> Le dossier `test-docs/` est expressément exclu du suivi Git via le fichier [`.gitignore`](file:///home/yokan/TAF/FORMATION/TESTO/interventionTerrain/.gitignore) afin de préserver la propreté du référentiel source tout en restant immédiatement opérationnel pour vos tests en local.

---

## 🛠️ Installation & Démarrage du Projet

### Prérequis
- **Java JDK 21**
- **Maven 3.9+** (ou utilisation du wrapper `./mvnw`)
- **PostgreSQL 15+** (ou compte Neon Cloud)
- **Docker** (optionnel)

### Configuration Locale (`application.properties`)
```properties
server.port=${PORT:8080}
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/interventions_terrain}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:postgres}
spring.flyway.enabled=true
```

### Démarrage avec le Wrapper Maven
```bash
# Compilation et lancement de l'application Spring Boot
./mvnw clean spring-boot:run
```

### Déploiement Conteneurisé avec Docker
Un [`Dockerfile`](file:///home/yokan/TAF/FORMATION/TESTO/interventionTerrain/Dockerfile) multi-étape basé sur OpenJDK 21 est inclus à la racine du projet :
```bash
# Construction de l'image Docker
docker build -t intervention-terrain-app .

# Lancement du conteneur avec variables d'environnement
docker run -p 8080:8080 \
  -e PORT=8080 \
  -e DB_URL="jdbc:postgresql://ep-...neon.tech/interventions_terrain?sslmode=require" \
  -e DB_USERNAME="your_username" \
  -e DB_PASSWORD="your_password" \
  intervention-terrain-app
```

---

## 🏛️ Structure du Projet

```
interventionTerrain/
├── Dockerfile                        # Image Docker optimisée OpenJDK 21
├── pom.xml                           # Configuration Maven (Spring Boot 3, Flyway, SpringDoc)
├── postman.json                      # 📬 Collection Postman complète importable
├── ZKANTAPI.postman_collection.json  # Collection multi-projets
├── test-docs/                        # 🧪 Scripts cURL & guides de test (ignoré par Git)
│   ├── curl_interventions_niveaux.sh
│   └── Tests_CURL_Interventions.md
└── src/
    └── main/
        ├── java/com/interventionTerrain/interventionTerrain/
        │   ├── controller/           # Contrôleurs REST (Agences, Techniciens, Compétences, Clients, Interventions)
        │   ├── dto/                  # DTOs Request & Response
        │   ├── entity/               # Entités JPA & Vues SQL @Immutable
        │   ├── exception/            # Gestion globale des exceptions (GlobalException)
        │   ├── repository/           # Interfaces Spring Data JPA & Requêtes spécifiques
        │   ├── service/              # Couche Métier, règles de gestion et validations
        │   └── utils/response/       # Wrappers de réponse standardisée
        └── resources/
            ├── application.properties
            └── db/migration/         # Scripts Flyway (V1.0.1__DDL.sql, V1.0.2__DML.sql)
```
