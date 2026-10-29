package com.interventionTerrain.interventionTerrain.controller;

import com.interventionTerrain.interventionTerrain.dto.CompetenceRequestDto;
import com.interventionTerrain.interventionTerrain.dto.ResponseDto;
import com.interventionTerrain.interventionTerrain.service.CompetenceService;
import com.interventionTerrain.interventionTerrain.utils.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/competences")
@Tag(name = "Compétences", description = "Endpoints pour la gestion du référentiel des compétences")
public class CompetenceController {

    private final CompetenceService competenceService;

    public CompetenceController(CompetenceService competenceService) {
        this.competenceService = competenceService;
    }

    @Operation(summary = "Lister toutes les compétences", description = "Récupère la liste paginée de toutes les compétences enregistrées.")
    @GetMapping
    public Response<ResponseDto> getAll(
            @Parameter(description = "Numéro de la page (commence à 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Nombre d'éléments par page") @RequestParam(defaultValue = "10") int taille) {
        return new Response<>(competenceService.getAll(page, taille));
    }

    @Operation(summary = "Obtenir une compétence par ID", description = "Retourne les détails d'une compétence spécifique à partir de son identifiant.")
    @GetMapping("/{id}")
    public Response<ResponseDto> getById(
            @Parameter(description = "ID unique de la compétence", required = true) @PathVariable Long id) throws Throwable {
        return new Response<>(competenceService.getById(id));
    }

    @Operation(summary = "Rechercher une compétence par nom", description = "Permet de rechercher une compétence en fonction de son nom ou libellé.")
    @GetMapping("/recherche")
    public Response<ResponseDto> getByNom(
            @Parameter(description = "Nom ou partie du nom de la compétence", required = true) @RequestParam String nom) throws Throwable {
        return new Response<>(competenceService.getByNom(nom));
    }

    @Operation(summary = "Créer une nouvelle compétence", description = "Enregistre une nouvelle compétence dans le système.")
    @PostMapping
    public Response<ResponseDto> create(@Valid @RequestBody CompetenceRequestDto competenceRequest) {
        return new Response<>(competenceService.create(competenceRequest));
    }

    @Operation(summary = "Mettre à jour une compétence", description = "Modifie les informations d'une compétence existante.")
    @PutMapping("/{id}")
    public Response<ResponseDto> update(
            @Parameter(description = "ID de la compétence à modifier", required = true) @PathVariable Long id,
            @Valid @RequestBody CompetenceRequestDto competenceRequest) {
        return new Response<>(competenceService.update(id, competenceRequest));
    }

    @Operation(summary = "Supprimer une compétence", description = "Supprime une compétence à partir de son ID.")
    @DeleteMapping("/{id}")
    public Response<ResponseDto> delete(
            @Parameter(description = "ID de la compétence à supprimer", required = true) @PathVariable Long id) throws Throwable {
        return new Response<>(competenceService.delete(id));
    }
}