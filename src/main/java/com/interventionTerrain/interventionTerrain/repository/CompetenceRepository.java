package com.interventionTerrain.interventionTerrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import com.interventionTerrain.interventionTerrain.entity.Competence;

@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {
    boolean existsByLibelleIgnoreCase(String nom);
    boolean existsByLibelleIgnoreCaseAndIdNot(String nom, Long id);
    Optional<Competence> findByLibelle(String nom);
    List<Competence> findByLibelleIgnoreCase(String nom);

    @Query("SELECT COUNT(tc) > 0 FROM TechnicienCompetence tc WHERE tc.competence.id = :competenceId")
    boolean existsTechnicienByCompetenceId(@Param("competenceId") Long competenceId);
}