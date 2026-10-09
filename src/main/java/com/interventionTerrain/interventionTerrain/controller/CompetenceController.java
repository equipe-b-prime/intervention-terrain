package com.interventionTerrain.interventionTerrain.controller;

import com.interventionTerrain.interventionTerrain.dto.CompetenceRequestDto;
import com.interventionTerrain.interventionTerrain.dto.CompetenceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.interventionTerrain.interventionTerrain.service.CompetenceService;

/**
 * Contrôleur REST exposant les endpoints de gestion des compétences.
 */
@RestController
@RequestMapping("/competences")
@RequiredArgsConstructor
public class CompetenceController {
    private final CompetenceService competenceService;

    /**
     * Crée une nouvelle compétence.
     *
     * @param competenceRequest les informations de la compétence à créer
     * @return la compétence créée
     */
    @PostMapping
    public CompetenceResponseDto createCompetence(@RequestBody CompetenceRequestDto competenceRequest) {
        return competenceService.createCompetence(competenceRequest);
    }

    /**
     * Récupère une compétence à partir de son identifiant.
     *
     * @param id L'identifiant de la compétence à récupérer.
     * @return CompetenceResponseDto Les détails de la compétence récupérée.
     */
    @GetMapping("/{id}")
    public CompetenceResponseDto getCompetenceById(@PathVariable Long id) {
        return competenceService.getCompetenceById(id);
    }

    /**
     * Récupère la liste de toutes les compétences.
     *
     * @return List<CompetenceResponseDto> La liste des compétences récupérées.
     */
    @GetMapping
    public List<CompetenceResponseDto> getAllCompetences() {
        return competenceService.getAllCompetences();
    }

    /**
     * Modifie une compétence existante.
     *
     * @param id L'identifiant de la compétence à modifier.
     * @param competenceRequest Les données de la requête pour modifier la compétence.
     * @return CompetenceResponseDto Les détails de la compétence modifiée.
     */
    @PutMapping("/{id}")
    public CompetenceResponseDto updateCompetenceById(@PathVariable Long id, @RequestBody CompetenceRequestDto competenceRequest) {
        return competenceService.updateCompetenceById(id, competenceRequest);
    }

    /**
     * Supprime une compétence à partir de son identifiant.
     *
     * @param id L'identifiant de la compétence à supprimer.
     * @return CompetenceResponseDto Les détails de la compétence supprimée.
     */
    @DeleteMapping("/{id}")
    public CompetenceResponseDto deleteCompetenceById(@PathVariable Long id) {
        return competenceService.deleteCompetenceById(id);
    }
}