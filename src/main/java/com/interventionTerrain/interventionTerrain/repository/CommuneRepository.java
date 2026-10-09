package com.interventionTerrain.interventionTerrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.interventionTerrain.interventionTerrain.entity.Agence;
import com.interventionTerrain.interventionTerrain.entity.Commune;

/**
 * Repository JPA pour l'accès aux données des communes.
 * Nécessaire à la résolution des relations de l'entité Agence.
 */
@Repository
public interface CommuneRepository extends JpaRepository<Commune, Long> {
}