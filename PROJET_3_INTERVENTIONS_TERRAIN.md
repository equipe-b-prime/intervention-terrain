# PROJET 3 — LES INTERVENTIONS TERRAIN

## NIVEAU 2 — CONSULTATION ET RECHERCHE

### F6. Lister les techniciens d'une agence
En tant que responsable d'exploitation, je veux voir l'effectif d'une agence.

- **Appel** — `GET /agences/{id}/techniciens`
- **Résultat** — les techniciens triés par nom, avec le nombre de compétences valides de chacun
- **Cas limites** — une agence sans technicien renvoie une liste vide ; un identifiant inconnu renvoie 404

### F7. Consulter les compétences d'un technicien
En tant que planificateur, je veux savoir ce qu'un technicien sait faire.

- **Appel** — `GET /techniciens/{id}/competences`
- **Résultat** — chaque compétence avec sa date d'obtention et sa date d'expiration
- **Cas limites** — les compétences expirées sont signalées comme telles plutôt que masquées

### F8. Consulter l'historique d'un client
En tant que responsable d'exploitation, je veux voir toutes les interventions faites chez un client.

- **Appel** — `GET /clients/{id}/interventions`
- **Résultat** — les interventions de la plus récente à la plus ancienne, avec le technicien, le type et le statut
- **Cas limites** — un client sans intervention renvoie une liste vide

### F9. Filtrer les interventions par statut et par période
En tant que responsable d'exploitation, je veux isoler un sous-ensemble d'interventions.

- **Appel** — `GET /interventions?statut=TERMINEE&du=2026-11-01&au=2026-11-30`
- **Résultat** — les interventions satisfaisant tous les critères fournis
- **Cas limites** — les critères sont optionnels et combinables ; un statut inconnu renvoie 400 ; du postérieur à au renvoie 400

### F10. Consulter le planning d'un technicien
En tant que planificateur, je veux voir le programme d'un technicien sur une période.

- **Appel** — `GET /techniciens/{id}/planning?du=…&au=…`
- **Résultat** — les interventions triées par date puis par heure de début, avec le client, l'adresse et le créneau
- **Cas limites** — sans paramètre, la semaine en cours est utilisée ; une période supérieure à trois mois renvoie 400

---

## NIVEAU 3 — LES RÈGLES MÉTIER

### F11. Associer une compétence à un technicien
En tant que responsable d'exploitation, je veux certifier qu'un technicien maîtrise une compétence.

- **Déclencheur** — `POST /techniciens/{id}/competences` avec la compétence et ses dates
- **Règle** — la date d'expiration, si elle est fournie, doit être postérieure à l'obtention ; un technicien ne peut pas avoir deux fois la même compétence active
- **Refus** — 409 en cas de doublon, 400 si les dates sont incohérentes
- **Effet** — la compétence devient opposable aux vérifications de planification

### F12. Empêcher le chevauchement de créneaux
En tant que planificateur, je veux qu'un technicien ne puisse pas avoir deux interventions simultanées.

- **Déclencheur** — `POST /interventions` et modification du créneau
- **Règle** — aucune intervention du technicien ne doit recouvrir le créneau demandé
- **Refus** — 409 citant l'intervention bloquante et son créneau
- **Effet** — les interventions annulées ne bloquent pas le créneau

### F13. Vérifier les compétences requises
En tant que planificateur, je veux être empêché d'affecter un technicien non qualifié.

- **Déclencheur** — `POST /interventions` et changement de technicien
- **Règle** — le technicien doit posséder toutes les compétences exigées par le type d'intervention, non expirées à la date prévue
- **Refus** — 400 listant les compétences manquantes ou expirées
- **Effet** — un type d'intervention sans exigence accepte tout technicien

### F14. Faire évoluer le statut d'une intervention
En tant que planificateur, je veux suivre l'avancement d'une intervention.

- **Déclencheur** — `POST /interventions/{id}/statut` avec le nouveau statut
- **Règle** — transitions autorisées : `PLANIFIEE → EN_COURS → TERMINEE` ou `ECHOUEE` ; `ANNULEE` n'est accessible que depuis `PLANIFIEE`
- **Refus** — 409 pour toute autre transition, en indiquant les statuts atteignables
- **Effet** — chaque changement est horodaté et conservé, l'historique reste consultable

### F15. Créer une intervention de reprise
En tant que planificateur, je veux reprogrammer une intervention qui a échoué.

- **Déclencheur** — `POST /interventions/{id}/reprise` avec un nouveau créneau
- **Règle** — l'intervention d'origine doit être `ECHOUEE` et ne pas avoir déjà été reprise
- **Refus** — 409 si le statut ne le permet pas ou si une reprise existe déjà
- **Effet** — la nouvelle intervention reprend le client et le type, et référence la précédente

### F16. Signaler les compétences proches de l'expiration
En tant que responsable d'exploitation, je veux être averti avant qu'un technicien perde une certification.

- **Déclencheur** — tâche planifiée, exécutée chaque nuit
- **Règle** — toute compétence expirant dans les trente jours est signalée
- **Effet** — une alerte est produite par agence, listant le technicien, la compétence et la date d'expiration
- **Traçabilité** — une compétence déjà signalée n'est pas signalée deux fois

---

## NIVEAU 4 — LA RESTITUTION

### F17. Trouver les techniciens disponibles et compétents
En tant que planificateur, je veux savoir qui peut prendre une intervention donnée.

- **Appel** — `GET /techniciens/disponibles?debut=…&fin=…&type=RACCORDEMENT_FIBRE`
- **Calcul** — les techniciens libres sur le créneau et possédant toutes les compétences requises, non expirées à cette date
- **Résultat** — les techniciens groupés par agence, avec leur charge de la journée
- **Cas limites** — si aucun technicien ne convient, une liste vide est renvoyée avec la raison du filtrage

### F18. Mesurer la charge d'une agence
En tant que responsable d'exploitation, je veux connaître l'occupation de mes équipes.

- **Appel** — `GET /agences/{id}/charge?du=…&au=…`
- **Calcul** — par technicien, le nombre d'interventions et le total d'heures occupées
- **Résultat** — le détail par technicien et un total au niveau de l'agence
- **Cas limites** — les interventions annulées sont exclues du calcul

### F19. Calculer le taux de réussite
En tant que responsable d'exploitation, je veux mesurer la qualité du service rendu.

- **Appel** — `GET /agences/{id}/taux-reussite?mois=2026-11`
- **Calcul** — part des interventions `TERMINEE` sur l'ensemble des interventions clôturées du mois
- **Résultat** — le taux global et le détail par type d'intervention
- **Cas limites** — les interventions encore en cours sont exclues ; une agence sans intervention clôturée renvoie 0