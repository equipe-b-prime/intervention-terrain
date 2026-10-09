package com.interventionTerrain.interventionTerrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.interventionTerrain.interventionTerrain.entity.TechnicienCompetence;

/**
 * Repository JPA pour l'accès aux données de la table associative
 * entre les techniciens et leurs compétences.
 */
@Repository
public interface TechnicienCompetenceRepository extends JpaRepository<TechnicienCompetence, Long> {

    /**
     * Récupère les compétences associées à un technicien.
     *
     * @param technicienId L'identifiant du technicien.
     * @return List<TechnicienCompetence> Les lignes associatives.
     */
    List<TechnicienCompetence> findByTechnicienId(Long technicienId);
}