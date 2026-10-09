package com.interventionTerrain.interventionTerrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import com.interventionTerrain.interventionTerrain.entity.InterventionCreneaux;

/**
 * Repository JPA pour l'accès aux données de la table associative
 * entre les interventions et les créneaux.
 */
@Repository
public interface InterventionCreneauxRepository extends JpaRepository<InterventionCreneaux, Long> {

    /**
     * Récupère les créneaux associés à une intervention.
     *
     * @param interventionId L'identifiant de l'intervention.
     * @return List<InterventionCreneaux> Les lignes associatives.
     */
    Optional<InterventionCreneaux> findByInterventionId(Long interventionId);

    /**
     * Supprime les créneaux associés à une intervention.
     *
     * @param interventionId L'identifiant de l'intervention.
     */
    @Transactional
    void deleteByInterventionId(Long interventionId);
}