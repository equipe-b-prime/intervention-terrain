package com.interventionTerrain.interventionTerrain.repository;

import com.interventionTerrain.interventionTerrain.entity.HistoriqueStatut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoriqueStatutRepository extends JpaRepository<HistoriqueStatut, Long> {
}