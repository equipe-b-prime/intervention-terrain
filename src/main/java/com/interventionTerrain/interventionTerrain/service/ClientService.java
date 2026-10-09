package com.interventionTerrain.interventionTerrain.service;

import com.interventionTerrain.interventionTerrain.dto.ClientRequestDto;
import com.interventionTerrain.interventionTerrain.dto.ClientResponseDto;
import com.interventionTerrain.interventionTerrain.dto.RequestDto;
import com.interventionTerrain.interventionTerrain.dto.ResponseDto;
import org.springframework.stereotype.Service;

import com.interventionTerrain.interventionTerrain.entity.Client;
import com.interventionTerrain.interventionTerrain.repository.ClientRepository;

/**
 * Service métier gérant les clients.
 * Fournit la création, la modification et les conversions entité/DTO.
 * La lecture et la suppression sont héritées de {@link AbstractCrudService}.
 */
@Service
public class ClientService extends AbstractCrudService {

    private final ClientRepository clientRepository;

    /**
     * Constructeur injectant le repository des clients.
     *
     * @param clientRepository le repository des clients
     */
    public ClientService(ClientRepository clientRepository) {
        super(clientRepository);
        this.clientRepository = clientRepository;
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
     * Convertit une entité client en objet de réponse.
     *
     * @param entity L'entité client à convertir.
     * @return ResponseDto Les détails du client converti.
     */
    @Override
    public ResponseDto toDto(Object entity) {
        Client client = (Client) entity;
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
     * Crée un nouveau client.
     *
     * @param requestDto les informations du client à créer
     * @return le client créé
     */
    @Override
    public ResponseDto create(RequestDto requestDto) {
        ClientRequestDto clientRequest = (ClientRequestDto) requestDto;
        return toDto(clientRepository.save(toEntity(clientRequest)));
    }

    /**
     * Modifie un client existant.
     *
     * @param id L'identifiant du client à modifier.
     * @param requestDto Les données de la requête pour modifier le client.
     * @return ResponseDto Les détails du client modifié.
     */
    @Override
    public ResponseDto update(Long id, RequestDto requestDto) {
        ClientRequestDto clientRequest = (ClientRequestDto) requestDto;
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        client.setNom(clientRequest.getNom());
        client.setPrenom(clientRequest.getPrenom());
        client.setEmail(clientRequest.getEmail());
        client.setTelephone(clientRequest.getTelephone());
        client.setAdresse(clientRequest.getAdresse());
        return toDto(clientRepository.save(client));
    }
}
