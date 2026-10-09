package com.interventionTerrain.interventionTerrain.service;

import com.interventionTerrain.interventionTerrain.dto.AgenceResponseDto;
import com.interventionTerrain.interventionTerrain.repository.ClientRepository;
import com.interventionTerrain.interventionTerrain.dto.ClientResponseDto;
import com.interventionTerrain.interventionTerrain.dto.CommuneResponseDto;
import com.interventionTerrain.interventionTerrain.repository.CreneauRepository;
import com.interventionTerrain.interventionTerrain.dto.CreneauResponseDto;
import com.interventionTerrain.interventionTerrain.entity.Client;
import com.interventionTerrain.interventionTerrain.entity.Creneau;
import com.interventionTerrain.interventionTerrain.entity.InterventionCreneaux;
import com.interventionTerrain.interventionTerrain.entity.InterventionTechnicien;
import com.interventionTerrain.interventionTerrain.entity.Technicien;
import com.interventionTerrain.interventionTerrain.service.TechnicienService;
import com.interventionTerrain.interventionTerrain.entity.TypeIntervention;
import com.interventionTerrain.interventionTerrain.dto.EquipeResponseDto;
import com.interventionTerrain.interventionTerrain.dto.InterventionRequestDto;
import com.interventionTerrain.interventionTerrain.dto.InterventionResponseDto;
import com.interventionTerrain.interventionTerrain.repository.InterventionCreneauxRepository;
import com.interventionTerrain.interventionTerrain.repository.InterventionTechnicienRepository;
import com.interventionTerrain.interventionTerrain.repository.TechnicienRepository;
import com.interventionTerrain.interventionTerrain.dto.TechnicienResponseDto;
import com.interventionTerrain.interventionTerrain.repository.TypeInterventionRepository;
import com.interventionTerrain.interventionTerrain.dto.TypeInterventionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import com.interventionTerrain.interventionTerrain.entity.Intervention;
import com.interventionTerrain.interventionTerrain.repository.InterventionRepository;

/**
 * Service métier gérant les interventions.
 * Fournit les opérations CRUD ainsi que les conversions entité/DTO.
 * Le technicien et le créneau sont gérés via les tables associatives existantes
 * (intervention_technicien et intervention_creneau), sans nouvelle relation.
 */
@Service
@RequiredArgsConstructor
public class InterventionService {

    private final InterventionRepository interventionRepository;
    private final ClientRepository clientRepository;
    private final TypeInterventionRepository typeInterventionRepository;
    private final TechnicienRepository technicienRepository;
    private final CreneauRepository creneauRepository;
    private final InterventionTechnicienRepository interventionTechnicienRepository;
    private final InterventionCreneauxRepository interventionCreneauxRepository;
    private final TechnicienService technicienService;
    private final CreneauService creneauService;

    /**
     * Convertit une entité intervention en objet de réponse.
     *
     * @param intervention L'entité intervention à convertir.
     * @return InterventionResponseDto Les détails de l'intervention convertie.
     */
    public InterventionResponseDto toDto(Intervention intervention) {
        InterventionTechnicien interventionTechnicien =
                interventionTechnicienRepository.findByInterventionId(intervention.getId())
                        .orElseThrow(() -> new RuntimeException(
                                "Aucun technicien associé à cette intervention"
                        ));

        Technicien technicien = interventionTechnicien.getTechnicien();

        InterventionCreneaux interventionCreneau =
                interventionCreneauxRepository.findByInterventionId(intervention.getId())
                        .orElseThrow(() -> new RuntimeException(
                                "Aucun créneau associé à cette intervention"
                        ));

        Creneau creneau = interventionCreneau.getCreneau();
        return InterventionResponseDto.builder()
                .id(intervention.getId())
                .datePlanifiee(intervention.getDatePlanifiee())
                .statut(intervention.getStatut())
                .commentaire(intervention.getCommentaire())
                .reference(intervention.getReference())
                .typeIntervention(TypeInterventionResponseDto.builder()
                        .id(intervention.getTypeIntervention().getId())
                        .nom(intervention.getTypeIntervention().getNom())
                        .description(intervention.getTypeIntervention().getDescription())
                        .delaiHeures(intervention.getTypeIntervention().getDelaiHeures())
                        .build())
                .client(ClientResponseDto.builder()
                        .id(intervention.getClient().getId())
                        .nom(intervention.getClient().getNom())
                        .prenom(intervention.getClient().getPrenom())
                        .email(intervention.getClient().getEmail())
                        .telephone(intervention.getClient().getTelephone())
                        .adresse(intervention.getClient().getAdresse())
                        .build())
                .technicien(technicienService.toDto(technicien))
                .creneau(creneauService.toDto(creneau))
                .build();
    }

    /**
     * Convertit les données d'une requête en entité intervention.
     *
     * @param interventionRequest Les données de la requête pour créer l'intervention.
     * @param typeIntervention Le type d'intervention associé.
     * @param client Le client associé à l'intervention.
     * @return Intervention L'entité intervention créée.
     */
    public Intervention toEntity(InterventionRequestDto interventionRequest, TypeIntervention typeIntervention, Client client) {
        return Intervention.builder()
                .datePlanifiee(interventionRequest.getDatePlanifiee())
                .statut(interventionRequest.getStatut())
                .commentaire(interventionRequest.getCommentaire())
                .reference(interventionRequest.getReference())
                .typeIntervention(typeIntervention)
                .client(client)
                .build();
    }

    /**
     * Crée une nouvelle intervention ainsi que ses associations technicien et créneau.
     *
     * @param interventionRequest les informations de l'intervention à créer
     * @return l'intervention créée
     */
    public InterventionResponseDto createIntervention(InterventionRequestDto interventionRequest) {
        TypeIntervention typeIntervention = typeInterventionRepository.findById(interventionRequest.getTypeInterventionId())
                .orElseThrow(() -> new RuntimeException("Type d'intervention non trouvé"));
        Client client = clientRepository.findById(interventionRequest.getClientId())
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        Intervention intervention = interventionRepository.save(toEntity(interventionRequest, typeIntervention, client));
        saveAssociations(intervention, interventionRequest);
        return toDto(intervention);
    }

    /**
     * Récupère une intervention à partir de son identifiant.
     *
     * @param id L'identifiant de l'intervention à récupérer.
     * @return InterventionResponseDto Les détails de l'intervention récupérée.
     */
    public InterventionResponseDto getInterventionById(Long id) {
        Intervention intervention = interventionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Intervention non trouvée"));
        return toDto(intervention);
    }

    /**
     * Récupère la liste de toutes les interventions.
     *
     * @return List<InterventionResponseDto> La liste des interventions récupérées.
     */
    public List<InterventionResponseDto> getAllInterventions() {
        return interventionRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * Modifie une intervention existante et réassocie technicien et créneau.
     *
     * @param id L'identifiant de l'intervention à modifier.
     * @param interventionRequest Les données de la requête pour modifier l'intervention.
     * @return InterventionResponseDto Les détails de l'intervention modifiée.
     */
    public InterventionResponseDto updateInterventionById(Long id, InterventionRequestDto interventionRequest) {
        Intervention intervention = interventionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Intervention non trouvée"));
        TypeIntervention typeIntervention = typeInterventionRepository.findById(interventionRequest.getTypeInterventionId())
                .orElseThrow(() -> new RuntimeException("Type d'intervention non trouvé"));
        Client client = clientRepository.findById(interventionRequest.getClientId())
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        intervention.setDatePlanifiee(interventionRequest.getDatePlanifiee());
        intervention.setStatut(interventionRequest.getStatut());
        intervention.setCommentaire(interventionRequest.getCommentaire());
        intervention.setReference(interventionRequest.getReference());
        intervention.setTypeIntervention(typeIntervention);
        intervention.setClient(client);
        interventionRepository.save(intervention);
        interventionTechnicienRepository.deleteByInterventionId(id);
        interventionCreneauxRepository.deleteByInterventionId(id);
        saveAssociations(intervention, interventionRequest);
        return toDto(intervention);
    }

    /**
     * Supprime une intervention ainsi que ses associations technicien et créneau.
     *
     * @param id L'identifiant de l'intervention à supprimer.
     * @return InterventionResponseDto Les détails de l'intervention supprimée.
     */
    public InterventionResponseDto deleteInterventionById(Long id) {
        Intervention intervention = interventionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Intervention non trouvée"));
        InterventionResponseDto response = toDto(intervention);
        interventionTechnicienRepository.deleteByInterventionId(id);
        interventionCreneauxRepository.deleteByInterventionId(id);
        interventionRepository.deleteById(id);
        return response;
    }

    /**
     * Récupère les interventions d'un client (requête personnalisée avec jointure).
     *
     * @param clientId L'identifiant du client.
     * @return List<InterventionResponseDto> Les interventions du client.
     */
    public List<InterventionResponseDto> getInterventionsByClient(Long clientId) {
        return interventionRepository.findByClientId(clientId).stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * Récupère les interventions d'un technicien (requête personnalisée avec jointure).
     *
     * @param technicienId L'identifiant du technicien.
     * @return List<InterventionResponseDto> Les interventions du technicien.
     */
    public List<InterventionResponseDto> getInterventionsByTechnicien(Long technicienId) {
        return interventionRepository.findByTechnicienId(technicienId).stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * Récupère les interventions par statut (requête personnalisée).
     *
     * @param statut Le statut recherché.
     * @return List<InterventionResponseDto> Les interventions correspondantes.
     */
    public List<InterventionResponseDto> getInterventionsByStatut(String statut) {
        return interventionRepository.findByStatut(statut).stream()
                .map(this::toDto)
                .toList();
    }



    /**
     * Crée les associations technicien et créneau d'une intervention.
     *
     * @param intervention L'intervention concernée.
     * @param interventionRequest La requête contenant les identifiants associés.
     */
    private void saveAssociations(Intervention intervention, InterventionRequestDto interventionRequest) {
        if (interventionRequest.getTechnicienId() != null) {
            Technicien technicien = technicienRepository.findById(interventionRequest.getTechnicienId())
                    .orElseThrow(() -> new RuntimeException("Technicien non trouvé"));
            interventionTechnicienRepository.save(InterventionTechnicien.builder()
                    .intervention(intervention)
                    .technicien(technicien)
                    .estResponsable(Boolean.TRUE)
                    .build());
        }
        if (interventionRequest.getCreneauId() != null) {
            Creneau creneau = creneauRepository.findById(interventionRequest.getCreneauId())
                    .orElseThrow(() -> new RuntimeException("Créneau non trouvé"));
            interventionCreneauxRepository.save(InterventionCreneaux.builder()
                    .intervention(intervention)
                    .creneau(creneau)
                    .build());
        }
    }
}