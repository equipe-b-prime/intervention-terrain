package com.interventionTerrain.interventionTerrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.interventionTerrain.interventionTerrain.entity.TypeIntervention;

@Repository
public interface TypeInterventionRepository extends JpaRepository<TypeIntervention, Long> {

    @Query("SELECT COUNT(ti) > 0 FROM TypeIntervention ti WHERE ti.id IN (SELECT tc.competence.id FROM TechnicienCompetence tc WHERE tc.competence.id = :competenceId)")
    boolean existsByCompetenceId(@Param("competenceId") Long competenceId);
}