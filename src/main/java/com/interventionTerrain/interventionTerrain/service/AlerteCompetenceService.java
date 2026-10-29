package com.interventionTerrain.interventionTerrain.service;

import com.interventionTerrain.interventionTerrain.entity.AlerteCompetence;
import com.interventionTerrain.interventionTerrain.entity.TechnicienCompetence;
import com.interventionTerrain.interventionTerrain.exception.ParametresInvalidesException;
import com.interventionTerrain.interventionTerrain.repository.AlerteCompetenceRepository;
import com.interventionTerrain.interventionTerrain.repository.TechnicienCompetenceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class AlerteCompetenceService {

    private final TechnicienCompetenceRepository technicienCompetenceRepository;
    private final AlerteCompetenceRepository alerteCompetenceRepository;

    public AlerteCompetenceService(TechnicienCompetenceRepository technicienCompetenceRepository,
                                   AlerteCompetenceRepository alerteCompetenceRepository) {
        this.technicienCompetenceRepository = technicienCompetenceRepository;
        this.alerteCompetenceRepository = alerteCompetenceRepository;
    }

    /**
     * Règle F16 : signale toute compétence expirant dans les 30 jours.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void genererAlertesExpiration() {
        LocalDate aujourdhui = LocalDate.now();
        LocalDate finFenetre = aujourdhui.plusDays(30);

        // Recherche optimisée directement en base via le repository
        List<TechnicienCompetence> technoCompetences =
                technicienCompetenceRepository.findByDateExpirationBetween(aujourdhui, finFenetre);

        log.info("Nombre de compétences expirant sous 30 jours trouvées : {}", technoCompetences.size());

        for (TechnicienCompetence tc : technoCompetences) {
            // Vérification de l'existence d'une alerte non traitée via Spring Data JPA
            boolean alerteExiste = alerteCompetenceRepository
                    .existsByTechnicienCompetenceIdAndEstTraiteeFalse(tc.getId());

            if (!alerteExiste) {
                AlerteCompetence alerte = AlerteCompetence.builder()
                        .technicienCompetence(tc)
                        .dateSignalement(LocalDateTime.now())
                        .estTraitee(false)
                        .build();
                alerteCompetenceRepository.save(alerte);
                log.info("Alerte créée avec succès pour la compétence ID {} du technicien ID {}",
                        tc.getId(), tc.getTechnicien() != null ? tc.getTechnicien().getId() : "N/A");
            } else {
                log.info("Une alerte non traitée existe déjà pour la compétence ID {}", tc.getId());
            }
        }
    }

    /**
     * Marque une alerte comme traitée.
     */
    @Transactional
    public void marquerCommeTraitee(Long alerteId) {
        AlerteCompetence alerte = alerteCompetenceRepository.findById(alerteId)
                .orElseThrow(() -> new ParametresInvalidesException(
                        "Alerte non trouvée avec l'ID: " + alerteId));
        alerte.setEstTraitee(true);
        alerteCompetenceRepository.save(alerte);
    }

    /**
     * Récupère les alertes actives (non traitées) par agence.
     */
    public List<AlerteCompetence> findAlertesNonTraiteesParAgence(Long agenceId) {
        return alerteCompetenceRepository.findByAgenceNonTraitee(agenceId);
    }

    /**
     * Récupère les alertes actives pour une compétence donnée.
     */
    public List<AlerteCompetence> findAlertesNonTraiteesParCompetence(Long competenceId) {
        return alerteCompetenceRepository.findByCompetenceNonTraitee(competenceId);
    }
}