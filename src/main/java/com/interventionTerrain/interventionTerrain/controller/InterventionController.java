package com.interventionTerrain.interventionTerrain.controller;

import com.interventionTerrain.interventionTerrain.dto.InterventionResponseDto;
import com.interventionTerrain.interventionTerrain.service.InterventionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST exposant les endpoints de gestion des interventions.
 * Le CRUD standard est hérité de {@link CrudController} ; seules les
 * requêtes métier propres aux interventions sont déclarées ici.
 */
@RestController
@RequestMapping("/interventions")
public class InterventionController extends CrudController {
    private final InterventionService interventionService;

    public InterventionController(InterventionService interventionService) {
        super(interventionService);
        this.interventionService = interventionService;
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
