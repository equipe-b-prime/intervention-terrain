package com.interventionTerrain.interventionTerrain.controller;

import com.interventionTerrain.interventionTerrain.dto.InterventionRequestDto;
import com.interventionTerrain.interventionTerrain.dto.InterventionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.interventionTerrain.interventionTerrain.service.InterventionService;

/**
 * Contrôleur REST exposant les endpoints de gestion des interventions.
 */
@RestController
@RequestMapping("/interventions")
@RequiredArgsConstructor
public class InterventionController {
    private final InterventionService interventionService;

    /**
     * Crée une nouvelle intervention.
     *
     * @param interventionRequest les informations de l'intervention à créer
     * @return l'intervention créée
     */
    @PostMapping
    public InterventionResponseDto createIntervention(@RequestBody InterventionRequestDto interventionRequest) {
        return interventionService.createIntervention(interventionRequest);
    }

    /**
     * Récupère une intervention à partir de son identifiant.
     *
     * @param id L'identifiant de l'intervention à récupérer.
     * @return InterventionResponseDto Les détails de l'intervention récupérée.
     */
    @GetMapping("/{id}")
    public InterventionResponseDto getInterventionById(@PathVariable Long id) {
        return interventionService.getInterventionById(id);
    }

    /**
     * Récupère la liste de toutes les interventions.
     *
     * @return List<InterventionResponseDto> La liste des interventions récupérées.
     */
    @GetMapping
    public List<InterventionResponseDto> getAllInterventions() {
        return interventionService.getAllInterventions();
    }

    /**
     * Modifie une intervention existante.
     *
     * @param id L'identifiant de l'intervention à modifier.
     * @param interventionRequest Les données de la requête pour modifier l'intervention.
     * @return InterventionResponseDto Les détails de l'intervention modifiée.
     */
    @PutMapping("/{id}")
    public InterventionResponseDto updateInterventionById(@PathVariable Long id, @RequestBody InterventionRequestDto interventionRequest) {
        return interventionService.updateInterventionById(id, interventionRequest);
    }

    /**
     * Supprime une intervention à partir de son identifiant.
     *
     * @param id L'identifiant de l'intervention à supprimer.
     * @return InterventionResponseDto Les détails de l'intervention supprimée.
     */
    @DeleteMapping("/{id}")
    public InterventionResponseDto deleteInterventionById(@PathVariable Long id) {
        return interventionService.deleteInterventionById(id);
    }

    /**
     * Récupère les interventions d'un client.
     *
     * @param clientId L'identifiant du client.
     * @return List<InterventionResponseDto> Les interventions du client.
     */
    @GetMapping("/client/{clientId}")
    public List<InterventionResponseDto> getInterventionsByClient(@PathVariable Long clientId) {
        return interventionService.getInterventionsByClient(clientId);
    }

    /**
     * Récupère les interventions d'un technicien.
     *
     * @param technicienId L'identifiant du technicien.
     * @return List<InterventionResponseDto> Les interventions du technicien.
     */
    @GetMapping("/technicien/{technicienId}")
    public List<InterventionResponseDto> getInterventionsByTechnicien(@PathVariable Long technicienId) {
        return interventionService.getInterventionsByTechnicien(technicienId);
    }

    /**
     * Récupère les interventions par statut.
     *
     * @param statut Le statut recherché.
     * @return List<InterventionResponseDto> Les interventions correspondantes.
     */
    @GetMapping("/statut/{statut}")
    public List<InterventionResponseDto> getInterventionsByStatut(@PathVariable String statut) {
        return interventionService.getInterventionsByStatut(statut);
    }
}