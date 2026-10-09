package com.interventionTerrain.interventionTerrain.controller;

import com.interventionTerrain.interventionTerrain.dto.CompetenceResponseDto;
import com.interventionTerrain.interventionTerrain.dto.TechnicienResponseDto;
import com.interventionTerrain.interventionTerrain.service.TechnicienService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST exposant les endpoints de gestion des techniciens.
 * Le CRUD standard est hérité de {@link CrudController} ; seules les
 * requêtes métier propres aux techniciens sont déclarées ici.
 */
@RestController
@RequestMapping("/techniciens")
public class TechnicienController extends CrudController {
    private final TechnicienService technicienService;

    public TechnicienController(TechnicienService technicienService) {
        super(technicienService);
        this.technicienService = technicienService;
    }

    /**
     * Récupère les techniciens d'une agence.
     *
     * @param agenceId L'identifiant de l'agence.
     * @return List<TechnicienResponseDto> Les techniciens de l'agence.
     */
    @GetMapping("/agence/{agenceId}")
    public List<TechnicienResponseDto> getTechniciensByAgence(@PathVariable Long agenceId) {
        return technicienService.getTechniciensByAgence(agenceId);
    }

    /**
     * Récupère les compétences d'un technicien.
     *
     * @param id L'identifiant du technicien.
     * @return List<CompetenceResponseDto> Les compétences du technicien.
     */
    @GetMapping("/{id}/competences")
    public List<CompetenceResponseDto> getCompetencesByTechnicien(@PathVariable Long id) {
        return technicienService.getCompetencesByTechnicien(id);
    }
}
