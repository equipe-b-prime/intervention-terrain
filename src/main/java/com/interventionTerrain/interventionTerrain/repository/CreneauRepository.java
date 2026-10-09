package com.interventionTerrain.interventionTerrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.interventionTerrain.interventionTerrain.entity.Creneau;

/**
 * Repository JPA pour l'accès aux données des créneaux.
 * Fournit les opérations CRUD standard sur la table "creneau".
 */
@Repository
public interface CreneauRepository extends JpaRepository<Creneau, Long> {
}