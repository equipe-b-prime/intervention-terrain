package com.interventionTerrain.interventionTerrain.controller;

import com.interventionTerrain.interventionTerrain.service.AgenceService;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST exposant les endpoints de gestion des agences.
 */
@RestController
@RequestMapping("/agences")
public class AgenceController extends CrudController {
    public AgenceController(AgenceService agenceService) {
        super(agenceService);
    }
}
