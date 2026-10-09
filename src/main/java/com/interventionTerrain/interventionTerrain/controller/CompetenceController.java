package com.interventionTerrain.interventionTerrain.controller;

import com.interventionTerrain.interventionTerrain.service.CompetenceService;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST exposant les endpoints de gestion des compétences.
 */
@RestController
@RequestMapping("/competences")
public class CompetenceController extends CrudController {
    public CompetenceController(CompetenceService competenceService) {
        super(competenceService);
    }
}
