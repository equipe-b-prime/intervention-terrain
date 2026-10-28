# Vues SQL — Intervention Terrain

Les vues ci-dessous sont créées à la main dans la base PostgreSQL `interventions_terrain`.
Elles sont mappées en lecture seule avec `@Entity @Immutable` côté JPA (aucune écriture).

---

## v_technicien_disponible

Vue d'agrégation par technicien : informations, agence et charge du jour
(nombre d'interventions et heures occupées). Les interventions annulées sont exclues.

```sql
CREATE OR REPLACE VIEW v_technicien_disponible AS
SELECT
    t.id AS technicien_id,
    a.id AS agence_id,
    a.nom AS agence_nom,
    COALESCE(SUM(
        CASE WHEN i.date_planifiee::date = CURRENT_DATE AND i.statut <> 'ANNULEE'
             THEN EXTRACT(EPOCH FROM (c.date_fin - c.date_debut)) / 3600.0
             ELSE 0 END), 0) AS heures_occupees_jour,
    t.matricule,
    COUNT(DISTINCT tc.id) FILTER (WHERE tc.date_expiration IS NULL OR tc.date_expiration >= CURRENT_DATE) AS nb_competences_valides,
    COUNT(DISTINCT CASE WHEN i.date_planifiee::date = CURRENT_DATE AND i.statut <> 'ANNULEE' THEN i.id END) AS nb_interventions_jour,
    t.nom,
    t.prenom
FROM technicien t
LEFT JOIN equipe e ON e.id = t.equipe_id
LEFT JOIN agence a ON a.id = e.agence_id
LEFT JOIN intervention_technicien it ON it.technicien_id = t.id
LEFT JOIN intervention i ON i.id = it.intervention_id
LEFT JOIN intervention_creneau ic ON ic.intervention_id = i.id
LEFT JOIN creneau c ON c.id = ic.creneau_id
LEFT JOIN technicien_competence tc ON tc.technicien_id = t.id
GROUP BY t.id, a.id, a.nom, t.matricule, t.nom, t.prenom;
```

| Colonne | Type | Description |
|---------|------|-------------|
| `technicien_id` | bigint | PK — identifiant du technicien |
| `agence_id` | bigint | Agence du technicien |
| `agence_nom` | varchar | Nom de l'agence |
| `heures_occupees_jour` | double | Heures occupées pour la journée |
| `matricule` | varchar | Matricule du technicien |
| `nb_competences_valides` | int | Compétences encore valides |
| `nb_interventions_jour` | int | Interventions du jour (hors ANNULEE) |
| `nom` / `prenom` | varchar | Identité du technicien |

Mappée par `entity/TechnicienDisponibleView.java` — utilisée pour la restitution F17.

---

## v_taux_reussite_agence

Vue du taux de réussite par agence, type d'intervention et mois.
Part des interventions TERMINEE parmi les interventions clôturées du mois.

```sql
CREATE OR REPLACE VIEW v_taux_reussite_agence AS
SELECT
    ROW_NUMBER() OVER () AS id,
    a.id AS agence_id,
    a.nom AS agence_nom,
    ti.id AS type_intervention_id,
    ti.nom AS type_intervention_nom,
    TO_CHAR(i.date_planifiee, 'YYYY-MM') AS mois,
    COUNT(CASE WHEN i.statut = 'TERMINEE' THEN 1 END) AS nb_terminee,
    COUNT(CASE WHEN i.statut = 'ECHOUEE' THEN 1 END) AS nb_echouee
FROM intervention i
JOIN types_intervention ti ON ti.id = i.type_intervention_id
JOIN intervention_technicien it ON it.intervention_id = i.id
JOIN technicien t ON t.id = it.technicien_id
JOIN equipe e ON e.id = t.equipe_id
JOIN agence a ON a.id = e.agence_id
WHERE i.statut IN ('TERMINEE', 'ECHOUEE')
GROUP BY a.id, a.nom, ti.id, ti.nom, TO_CHAR(i.date_planifiee, 'YYYY-MM');
```

Mappée par `entity/TauxReussiteView.java` — utilisée pour la restitution F19.