package com.interventionTerrain.interventionTerrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.interventionTerrain.interventionTerrain.entity.InterventionTechnicien;

/**
 * Repository JPA pour l'accès aux données de la table associative
 * entre les interventions et les techniciens.
 */
@Repository
public interface InterventionTechnicienRepository extends JpaRepository<InterventionTechnicien, Long> {

    /**
     * Récupère les affectations de techniciens pour une intervention.
     *
     * @param interventionId L'identifiant de l'intervention.
     * @return List<InterventionTechnicien> Les lignes associatives.
     */
    List<InterventionTechnicien> findByInterventionId(Long interventionId);

    /**
     * Supprime les affectations de techniciens d'une intervention.
     *
     * @param interventionId L'identifiant de l'intervention.
     */
    @Transactional
    void deleteByInterventionId(Long interventionId);
}