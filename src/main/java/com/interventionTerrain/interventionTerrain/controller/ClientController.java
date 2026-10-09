package com.interventionTerrain.interventionTerrain.controller;

import com.interventionTerrain.interventionTerrain.dto.ClientRequestDto;
import com.interventionTerrain.interventionTerrain.dto.ClientResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.interventionTerrain.interventionTerrain.service.ClientService;

/**
 * Contrôleur REST exposant les endpoints de gestion des clients.
 */
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    /**
     * Crée un nouveau client.
     *
     * @param clientRequest les informations du client à créer
     * @return le client créé
     */
    @PostMapping
    public ClientResponseDto createClient(@RequestBody ClientRequestDto clientRequest) {
        return clientService.createClient(clientRequest);
    }

    /**
     * Récupère un client à partir de son identifiant.
     *
     * @param id L'identifiant du client à récupérer.
     * @return ClientResponseDto Les détails du client récupéré.
     */
    @GetMapping("/{id}")
    public ClientResponseDto getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    /**
     * Récupère la liste de tous les clients.
     *
     * @return List<ClientResponseDto> La liste des clients récupérés.
     */
    @GetMapping
    public List<ClientResponseDto> getAllClients() {
        return clientService.getAllClients();
    }

    /**
     * Modifie un client existant.
     *
     * @param id L'identifiant du client à modifier.
     * @param clientRequest Les données de la requête pour modifier le client.
     * @return ClientResponseDto Les détails du client modifié.
     */
    @PutMapping("/{id}")
    public ClientResponseDto updateClientById(@PathVariable Long id, @RequestBody ClientRequestDto clientRequest) {
        return clientService.updateClientById(id, clientRequest);
    }

    /**
     * Supprime un client à partir de son identifiant.
     *
     * @param id L'identifiant du client à supprimer.
     * @return ClientResponseDto Les détails du client supprimé.
     */
    @DeleteMapping("/{id}")
    public ClientResponseDto deleteClientById(@PathVariable Long id) {
        return clientService.deleteClientById(id);
    }
}