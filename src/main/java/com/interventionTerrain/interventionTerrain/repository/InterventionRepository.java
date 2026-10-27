package com.interventionTerrain.interventionTerrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.interventionTerrain.interventionTerrain.entity.Intervention;

@Repository
public interface InterventionRepository extends JpaRepository<Intervention, Long> {

    @Query("SELECT i FROM Intervention i WHERE i.client.id = :clientId ORDER BY i.datePlanifiee DESC")
    List<Intervention> findByClientId(@Param("clientId") Long clientId);

    @Query("SELECT i FROM Intervention i WHERE i.statut = :statut ORDER BY i.datePlanifiee ASC")
    List<Intervention> findByStatut(@Param("statut") String statut);

    @Query("SELECT i FROM Intervention i WHERE i.datePlanifiee BETWEEN :debut AND :fin ORDER BY i.datePlanifiee ASC")
    List<Intervention> findByDatePlanifieeBetween(@Param("debut") java.time.LocalDateTime debut,
                                                  @Param("fin") java.time.LocalDateTime fin);

    @Query("SELECT i FROM Intervention i WHERE i.statut = :statut AND i.datePlanifiee BETWEEN :debut AND :fin ORDER BY i.datePlanifiee ASC")
    List<Intervention> findByStatutAndDatePlanifieeBetween(@Param("statut") String statut,
                                                           @Param("debut") java.time.LocalDateTime debut,
                                                           @Param("fin") java.time.LocalDateTime fin);

    @Query("SELECT it.intervention FROM InterventionTechnicien it WHERE it.technicien.id = :technicienId")
    List<Intervention> findByTechnicienId(@Param("technicienId") Long technicienId);

    @Query("SELECT it.intervention FROM InterventionTechnicien it WHERE it.technicien.id = :technicienId " +
           "AND it.intervention.datePlanifiee BETWEEN :debut AND :fin ORDER BY it.intervention.datePlanifiee ASC")
    List<Intervention> findByTechnicienIdAndDatePlanifieeBetween(@Param("technicienId") Long technicienId,
                                                                  @Param("debut") java.time.LocalDateTime debut,
                                                                  @Param("fin") java.time.LocalDateTime fin);

    boolean existsByClientId(Long clientId);

    @Query("SELECT COUNT(it) > 0 FROM InterventionTechnicien it WHERE it.technicien.id = :technicienId")
    boolean existsByTechnicienId(@Param("technicienId") Long technicienId);
}