package com.interventionTerrain.interventionTerrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.interventionTerrain.interventionTerrain.entity.Intervention;
import com.interventionTerrain.interventionTerrain.entity.TypeIntervention;

/**
 * Repository JPA pour l'accès aux données des types d'intervention.
 * Nécessaire à la résolution des relations de l'entité Intervention.
 */
@Repository
public interface TypeInterventionRepository extends JpaRepository<TypeIntervention, Long> {
}