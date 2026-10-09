package com.interventionTerrain.interventionTerrain.service;

import com.interventionTerrain.interventionTerrain.dto.ClientRequestDto;
import com.interventionTerrain.interventionTerrain.dto.ClientResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import com.interventionTerrain.interventionTerrain.entity.Client;
import com.interventionTerrain.interventionTerrain.repository.ClientRepository;

/**
 * Service métier gérant les clients.
 * Fournit les opérations CRUD ainsi que les conversions entité/DTO.
 */
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    /**
     * Convertit une entité client en objet de réponse.
     *
     * @param client L'entité client à convertir.
     * @return ClientResponseDto Les détails du client converti.
     */
    public ClientResponseDto toDto(Client client) {
        return ClientResponseDto.builder()
                .id(client.getId())
                .nom(client.getNom())
                .prenom(client.getPrenom())
                .email(client.getEmail())
                .telephone(client.getTelephone())
                .adresse(client.getAdresse())
                .build();
    }

    /**
     * Convertit les données d'une requête en entité client.
     *
     * @param clientRequest Les données de la requête pour créer le client.
     * @return Client L'entité client créée.
     */
    public Client toEntity(ClientRequestDto clientRequest) {
        return Client.builder()
                .nom(clientRequest.getNom())
                .prenom(clientRequest.getPrenom())
                .email(clientRequest.getEmail())
                .telephone(clientRequest.getTelephone())
                .adresse(clientRequest.getAdresse())
                .build();
    }

    /**
     * Crée un nouveau client.
     *
     * @param clientRequest les informations du client à créer
     * @return le client créé
     */
    public ClientResponseDto createClient(ClientRequestDto clientRequest) {
        return toDto(clientRepository.save(toEntity(clientRequest)));
    }

    /**
     * Récupère un client à partir de son identifiant.
     *
     * @param id L'identifiant du client à récupérer.
     * @return ClientResponseDto Les détails du client récupéré.
     */
    public ClientResponseDto getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        return toDto(client);
    }

    /**
     * Récupère la liste de tous les clients.
     *
     * @return List<ClientResponseDto> La liste des clients récupérés.
     */
    public List<ClientResponseDto> getAllClients() {
        return clientRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * Modifie un client existant.
     *
     * @param id L'identifiant du client à modifier.
     * @param clientRequest Les données de la requête pour modifier le client.
     * @return ClientResponseDto Les détails du client modifié.
     */
    public ClientResponseDto updateClientById(Long id, ClientRequestDto clientRequest) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        client.setNom(clientRequest.getNom());
        client.setPrenom(clientRequest.getPrenom());
        client.setEmail(clientRequest.getEmail());
        client.setTelephone(clientRequest.getTelephone());
        client.setAdresse(clientRequest.getAdresse());
        return toDto(clientRepository.save(client));
    }

    /**
     * Supprime un client à partir de son identifiant.
     *
     * @param id L'identifiant du client à supprimer.
     * @return ClientResponseDto Les détails du client supprimé.
     */
    public ClientResponseDto deleteClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        clientRepository.deleteById(id);
        return toDto(client);
    }
}