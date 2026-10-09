package com.interventionTerrain.interventionTerrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.interventionTerrain.interventionTerrain.entity.Competence;

/**
 * Repository JPA pour l'accès aux données des compétences.
 * Fournit les opérations CRUD standard sur la table "competence".
 */
@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {
}