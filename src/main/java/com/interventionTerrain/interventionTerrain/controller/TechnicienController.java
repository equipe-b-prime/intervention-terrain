package com.interventionTerrain.interventionTerrain.controller;

import com.interventionTerrain.interventionTerrain.dto.CompetenceResponseDto;
import com.interventionTerrain.interventionTerrain.dto.TechnicienRequestDto;
import com.interventionTerrain.interventionTerrain.dto.TechnicienResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.interventionTerrain.interventionTerrain.service.TechnicienService;

/**
 * Contrôleur REST exposant les endpoints de gestion des techniciens.
 */
@RestController
@RequestMapping("/techniciens")
@RequiredArgsConstructor
public class TechnicienController {
    private final TechnicienService technicienService;

    /**
     * Crée un nouveau technicien.
     *
     * @param technicienRequest les informations du technicien à créer
     * @return le technicien créé
     */
    @PostMapping
    public TechnicienResponseDto createTechnicien(@RequestBody TechnicienRequestDto technicienRequest) {
        return technicienService.createTechnicien(technicienRequest);
    }

    /**
     * Récupère un technicien à partir de son identifiant.
     *
     * @param id L'identifiant du technicien à récupérer.
     * @return TechnicienResponseDto Les détails du technicien récupéré.
     */
    @GetMapping("/{id}")
    public TechnicienResponseDto getTechnicienById(@PathVariable Long id) {
        return technicienService.getTechnicienById(id);
    }

    /**
     * Récupère la liste de tous les techniciens.
     *
     * @return List<TechnicienResponseDto> La liste des techniciens récupérés.
     */
    @GetMapping
    public List<TechnicienResponseDto> getAllTechniciens() {
        return technicienService.getAllTechniciens();
    }

    /**
     * Modifie un technicien existant.
     *
     * @param id L'identifiant du technicien à modifier.
     * @param technicienRequest Les données de la requête pour modifier le technicien.
     * @return TechnicienResponseDto Les détails du technicien modifié.
     */
    @PutMapping("/{id}")
    public TechnicienResponseDto updateTechnicienById(@PathVariable Long id, @RequestBody TechnicienRequestDto technicienRequest) {
        return technicienService.updateTechnicienById(id, technicienRequest);
    }

    /**
     * Supprime un technicien à partir de son identifiant.
     *
     * @param id L'identifiant du technicien à supprimer.
     * @return TechnicienResponseDto Les détails du technicien supprimé.
     */
    @DeleteMapping("/{id}")
    public TechnicienResponseDto deleteTechnicienById(@PathVariable Long id) {
        return technicienService.deleteTechnicienById(id);
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