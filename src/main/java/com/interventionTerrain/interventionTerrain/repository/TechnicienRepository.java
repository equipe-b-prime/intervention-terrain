package com.interventionTerrain.interventionTerrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import com.interventionTerrain.interventionTerrain.entity.Technicien;

@Repository
public interface TechnicienRepository extends JpaRepository<Technicien, Long> {

    @Query("SELECT t FROM Technicien t WHERE t.equipe.agence.id = :agenceId")
    List<Technicien> findByAgenceId(@Param("agenceId") Long agenceId);

    boolean existsByMatriculeIgnoreCase(String matricule);

    @Query("SELECT COUNT(it) > 0 FROM InterventionTechnicien it WHERE it.technicien.id = :technicienId")
    boolean existsInterventionByTechnicienId(@Param("technicienId") Long technicienId);

    List<Technicien> findByNom(String nom);

    @Query("SELECT t FROM Technicien t WHERE LOWER(t.nom) = LOWER(:nom)")
    List<Technicien> findByNomIgnoreCase(@Param("nom") String nom);
}