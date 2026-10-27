package com.interventionTerrain.interventionTerrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import com.interventionTerrain.interventionTerrain.entity.TechnicienCompetence;

@Repository
public interface TechnicienCompetenceRepository extends JpaRepository<TechnicienCompetence, Long> {

    List<TechnicienCompetence> findByTechnicienId(Long technicienId);

    @Query("SELECT tc.technicien.id, COUNT(tc) FROM TechnicienCompetence tc " +
           "WHERE tc.technicien.equipe.agence.id = :agenceId " +
           "AND (tc.dateExpiration IS NULL OR tc.dateExpiration >= :dateRef) " +
           "GROUP BY tc.technicien.id")
    List<Object[]> compterValidesParAgence(@Param("agenceId") Long agenceId, @Param("dateRef") LocalDate dateRef);
}