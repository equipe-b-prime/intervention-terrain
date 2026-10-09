package com.interventionTerrain.interventionTerrain.controller;

import com.interventionTerrain.interventionTerrain.dto.AgenceRequestDto;
import com.interventionTerrain.interventionTerrain.dto.AgenceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.interventionTerrain.interventionTerrain.service.AgenceService;

/**
 * Contrôleur REST exposant les endpoints de gestion des agences.
 */
@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
public class AgenceController {
    private final AgenceService agenceService;

    /**
     * Crée une nouvelle agence.
     *
     * @param agenceRequest les informations de l'agence à créer
     * @return l'agence créée
     */
    @PostMapping
    public AgenceResponseDto createAgence(@RequestBody AgenceRequestDto agenceRequest) {
        return agenceService.createAgence(agenceRequest);
    }

    /**
     * Récupère une agence à partir de son identifiant.
     *
     * @param id L'identifiant de l'agence à récupérer.
     * @return AgenceResponseDto Les détails de l'agence récupérée.
     */
    @GetMapping("/{id}")
    public AgenceResponseDto getAgenceById(@PathVariable Long id) {
        return agenceService.getAgenceById(id);
    }

    /**
     * Récupère la liste de toutes les agences.
     *
     * @return List<AgenceResponseDto> La liste des agences récupérées.
     */
    @GetMapping
    public List<AgenceResponseDto> getAllAgences() {
        return agenceService.getAllAgences();
    }

    /**
     * Modifie une agence existante.
     *
     * @param id L'identifiant de l'agence à modifier.
     * @param agenceRequest Les données de la requête pour modifier l'agence.
     * @return AgenceResponseDto Les détails de l'agence modifiée.
     */
    @PutMapping("/{id}")
    public AgenceResponseDto updateAgenceById(@PathVariable Long id, @RequestBody AgenceRequestDto agenceRequest) {
        return agenceService.updateAgenceById(id, agenceRequest);
    }

    /**
     * Supprime une agence à partir de son identifiant.
     *
     * @param id L'identifiant de l'agence à supprimer.
     * @return AgenceResponseDto Les détails de l'agence supprimée.
     */
    @DeleteMapping("/{id}")
    public AgenceResponseDto deleteAgenceById(@PathVariable Long id) {
        return agenceService.deleteAgenceById(id);
    }
}