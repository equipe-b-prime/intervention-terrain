package com.interventionTerrain.interventionTerrain.controller;

import com.interventionTerrain.interventionTerrain.service.ClientService;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST exposant les endpoints de gestion des clients.
 */
@RestController
@RequestMapping("/clients")
public class ClientController extends CrudController {
    public ClientController(ClientService clientService) {
        super(clientService);
    }
}
