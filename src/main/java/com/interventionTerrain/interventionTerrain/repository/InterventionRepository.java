package com.interventionTerrain.interventionTerrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.interventionTerrain.interventionTerrain.entity.Intervention;
import com.interventionTerrain.interventionTerrain.entity.InterventionTechnicien;

/**
 * Repository JPA pour l'accès aux données des interventions.
 * Fournit les opérations CRUD standard sur la table "intervention"
 * ainsi que des requêtes personnalisées avec jointures.
 */
@Repository
public interface InterventionRepository extends JpaRepository<Intervention, Long> {

    /**
     * Récupère les interventions d'un client.
     *
     * @param clientId L'identifiant du client.
     * @return List<Intervention> Les interventions du client.
     */
    @Query("SELECT i FROM Intervention i WHERE i.client.id = :clientId")
    List<Intervention> findByClientId(@Param("clientId") Long clientId);

    /**
     * Récupère les interventions par statut.
     *
     * @param statut Le statut recherché.
     * @return List<Intervention> Les interventions correspondantes.
     */
    @Query("SELECT i FROM Intervention i WHERE i.statut = :statut")
    List<Intervention> findByStatut(@Param("statut") String statut);

    /**
     * Récupère les interventions affectées à un technicien.
     *
     * @param technicienId L'identifiant du technicien.
     * @return List<Intervention> Les interventions du technicien.
     */
    @Query("SELECT it.intervention FROM InterventionTechnicien it WHERE it.technicien.id = :technicienId")
    List<Intervention> findByTechnicienId(@Param("technicienId") Long technicienId);
}