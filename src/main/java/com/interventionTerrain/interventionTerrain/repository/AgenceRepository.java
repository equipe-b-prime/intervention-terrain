package com.interventionTerrain.interventionTerrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.interventionTerrain.interventionTerrain.entity.Agence;

/**
 * Repository JPA pour l'accès aux données des agences.
 * Fournit les opérations CRUD standard sur la table "agence".
 */
@Repository
public interface AgenceRepository extends JpaRepository<Agence, Long> {
}