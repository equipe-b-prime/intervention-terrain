package com.interventionTerrain.interventionTerrain.service;

import com.interventionTerrain.interventionTerrain.dto.TauxReussiteDetailDto;
import com.interventionTerrain.interventionTerrain.dto.TauxReussiteGlobalDto;
import com.interventionTerrain.interventionTerrain.entity.TauxReussiteView;
import com.interventionTerrain.interventionTerrain.exception.NotFoundException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TauxReussiteService {

    @PersistenceContext
    private EntityManager entityManager;

    public TauxReussiteGlobalDto getTauxReussite(Long agenceId, String mois) {
        List<TauxReussiteView> lignes = entityManager.createQuery(
                "SELECT v FROM TauxReussiteView v WHERE v.agenceId = :agenceId AND v.mois = :mois",
                TauxReussiteView.class)
                .setParameter("agenceId", agenceId)
                .setParameter("mois", mois)
                .getResultList();

        if (lignes.isEmpty()) {
            String agenceNom = (String) entityManager.createQuery(
                    "SELECT a.nom FROM Agence a WHERE a.id = :id")
                    .setParameter("id", agenceId)
                    .getSingleResult();
            return TauxReussiteGlobalDto.builder()
                    .agenceId(agenceId)
                    .agenceNom(agenceNom)
                    .mois(mois)
                    .tauxGlobal(0.0)
                    .details(List.of())
                    .build();
        }

        String agenceNom = lignes.get(0).getAgenceNom();

        List<TauxReussiteDetailDto> details = lignes.stream()
                .map(v -> {
                    long terminee = v.getNbTerminee() != null ? v.getNbTerminee() : 0L;
                    long echouee = v.getNbEchouee() != null ? v.getNbEchouee() : 0L;
                    double taux = (terminee + echouee) > 0
                            ? Math.round((terminee * 100.0 / (terminee + echouee)) * 100.0) / 100.0
                            : 0.0;
                    return TauxReussiteDetailDto.builder()
                            .typeInterventionId(v.getTypeInterventionId())
                            .typeInterventionNom(v.getTypeInterventionNom())
                            .nbTerminee(terminee)
                            .nbEchouee(echouee)
                            .taux(taux)
                            .build();
                })
                .toList();

        long totalTerminee = details.stream().mapToLong(TauxReussiteDetailDto::getNbTerminee).sum();
        long totalEchouee = details.stream().mapToLong(TauxReussiteDetailDto::getNbEchouee).sum();

        double tauxGlobal = (totalTerminee + totalEchouee) > 0
                ? Math.round((totalTerminee * 100.0 / (totalTerminee + totalEchouee)) * 100.0) / 100.0
                : 0.0;

        return TauxReussiteGlobalDto.builder()
                .agenceId(agenceId)
                .agenceNom(agenceNom)
                .mois(mois)
                .tauxGlobal(tauxGlobal)
                .details(details)
                .build();
    }
}