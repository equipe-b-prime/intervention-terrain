package com.interventionTerrain.interventionTerrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.interventionTerrain.interventionTerrain.entity.Equipe;
import com.interventionTerrain.interventionTerrain.entity.Technicien;

/**
 * Repository JPA pour l'accès aux données des équipes.
 * Nécessaire à la résolution des relations de l'entité Technicien.
 */
@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {
}