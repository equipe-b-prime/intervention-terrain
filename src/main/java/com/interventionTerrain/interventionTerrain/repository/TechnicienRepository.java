package com.interventionTerrain.interventionTerrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.interventionTerrain.interventionTerrain.entity.Technicien;

/**
 * Repository JPA pour l'accès aux données des techniciens.
 * Fournit les opérations CRUD standard sur la table "technicien"
 * ainsi qu'une requête personnalisée avec jointure vers l'agence.
 */
@Repository
public interface TechnicienRepository extends JpaRepository<Technicien, Long> {

    /**
     * Récupère les techniciens rattachés à une agence (via leur équipe).
     *
     * @param agenceId L'identifiant de l'agence.
     * @return List<Technicien> Les techniciens de l'agence.
     */
    @Query("SELECT t FROM Technicien t WHERE t.equipe.agence.id = :agenceId")
    List<Technicien> findByAgenceId(@Param("agenceId") Long agenceId);
}