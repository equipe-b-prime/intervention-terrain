package com.interventionTerrain.interventionTerrain.repository;

import com.interventionTerrain.interventionTerrain.entity.AlerteCompetence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlerteCompetenceRepository extends JpaRepository<AlerteCompetence, Long> {

    /**
     * Vérifie s'il existe déjà une alerte active (non traitée) pour une compétence donnée.
     */
    boolean existsByTechnicienCompetenceIdAndEstTraiteeFalse(Long technicienCompetenceId);

    /**
     * Récupère les alertes non traitées (pas de doublon F16).
     */
    @Query("SELECT a FROM AlerteCompetence a WHERE a.estTraitee = FALSE")
    List<AlerteCompetence> findNonTraitees();

    /**
     * Compte les alertes non traitées par agence.
     */
    @Query("SELECT COUNT(a) FROM AlerteCompetence a "
            + "JOIN a.technicienCompetence tc "
            + "JOIN tc.technicien t "
            + "JOIN t.equipe e "
            + "WHERE a.estTraitee = FALSE AND e.agence.id = :agenceId")
    long compterNonTraiteParAgence(@Param("agenceId") Long agenceId);

    /**
     * Récupère les alertes non traitées pour une agence donnée.
     */
    @Query("SELECT a FROM AlerteCompetence a "
            + "JOIN a.technicienCompetence tc "
            + "JOIN tc.technicien t "
            + "JOIN t.equipe e "
            + "WHERE a.estTraitee = FALSE AND e.agence.id = :agenceId")
    List<AlerteCompetence> findByAgenceNonTraitee(@Param("agenceId") Long agenceId);

    /**
     * Récupère les alertes non traitées pour une compétence donnée.
     */
    @Query("SELECT a FROM AlerteCompetence a "
            + "JOIN a.technicienCompetence tc "
            + "WHERE a.estTraitee = FALSE AND tc.competence.id = :competenceId")
    List<AlerteCompetence> findByCompetenceNonTraitee(@Param("competenceId") Long competenceId);
}