package com.interventionTerrain.interventionTerrain.service;

import com.interventionTerrain.interventionTerrain.dto.CreneauResponseDto;
import com.interventionTerrain.interventionTerrain.dto.ClientResponseDto;
import com.interventionTerrain.interventionTerrain.dto.RequestDto;
import com.interventionTerrain.interventionTerrain.dto.ResponseDto;
import com.interventionTerrain.interventionTerrain.dto.TypeInterventionResponseDto;
import com.interventionTerrain.interventionTerrain.repository.ClientRepository;
import com.interventionTerrain.interventionTerrain.repository.CreneauRepository;
import com.interventionTerrain.interventionTerrain.entity.Client;
import com.interventionTerrain.interventionTerrain.entity.Creneau;
import com.interventionTerrain.interventionTerrain.entity.InterventionCreneaux;
import com.interventionTerrain.interventionTerrain.entity.InterventionTechnicien;
import com.interventionTerrain.interventionTerrain.entity.Technicien;
import com.interventionTerrain.interventionTerrain.entity.TypeIntervention;
import com.interventionTerrain.interventionTerrain.dto.InterventionRequestDto;
import com.interventionTerrain.interventionTerrain.dto.InterventionResponseDto;
import com.interventionTerrain.interventionTerrain.dto.TechnicienResponseDto;
import com.interventionTerrain.interventionTerrain.repository.InterventionCreneauxRepository;
import com.interventionTerrain.interventionTerrain.repository.InterventionTechnicienRepository;
import com.interventionTerrain.interventionTerrain.repository.TechnicienRepository;
import com.interventionTerrain.interventionTerrain.repository.TypeInterventionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import com.interventionTerrain.interventionTerrain.entity.Intervention;
import com.interventionTerrain.interventionTerrain.repository.InterventionRepository;

/**
 * Service métier gérant les interventions.
 * Fournit la création, la modification, la suppression et les conversions entité/DTO.
 * Le technicien et le créneau sont gérés via les tables associatives existantes
 * (intervention_technicien et intervention_creneau), sans nouvelle relation.
 */
@Service
public class InterventionService extends AbstractCrudService {

    private final InterventionRepository interventionRepository;
    private final ClientRepository clientRepository;
    private final TypeInterventionRepository typeInterventionRepository;
    private final TechnicienRepository technicienRepository;
    private final CreneauRepository creneauRepository;
    private final InterventionTechnicienRepository interventionTechnicienRepository;
    private final InterventionCreneauxRepository interventionCreneauxRepository;
    private final TechnicienService technicienService;

    /**
     * Constructeur injectant les repositories nécessaires à l'intervention.
     *
     * @param interventionRepository le repository des interventions
     * @param clientRepository le repository des clients
     * @param typeInterventionRepository le repository des types d'intervention
     * @param technicienRepository le repository des techniciens
     * @param creneauRepository le repository des créneaux
     * @param interventionTechnicienRepository le repository de l'association intervention/technicien
     * @param interventionCreneauxRepository le repository de l'association intervention/créneau
     * @param technicienService le service des techniciens (conversion du technicien associé)
     */
    public InterventionService(InterventionRepository interventionRepository,
                               ClientRepository clientRepository,
                               TypeInterventionRepository typeInterventionRepository,
                               TechnicienRepository technicienRepository,
                               CreneauRepository creneauRepository,
                               InterventionTechnicienRepository interventionTechnicienRepository,
                               InterventionCreneauxRepository interventionCreneauxRepository,
                               TechnicienService technicienService) {
        super(interventionRepository);
        this.interventionRepository = interventionRepository;
        this.clientRepository = clientRepository;
        this.typeInterventionRepository = typeInterventionRepository;
        this.technicienRepository = technicienRepository;
        this.creneauRepository = creneauRepository;
        this.interventionTechnicienRepository = interventionTechnicienRepository;
        this.interventionCreneauxRepository = interventionCreneauxRepository;
        this.technicienService = technicienService;
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
     * Convertit une entité intervention en objet de réponse.
     *
     * @param entity L'entité intervention à convertir.
     * @return ResponseDto Les détails de l'intervention convertie.
     */
    @Override
    public ResponseDto toDto(Object entity) {
        Intervention intervention = (Intervention) entity;
        Technicien technicien = interventionTechnicienRepository.findByInterventionId(intervention.getId())
                .stream().findFirst().map(InterventionTechnicien::getTechnicien).orElse(null);
        Creneau creneau = interventionCreneauxRepository.findByInterventionId(intervention.getId())
                .stream().findFirst().map(InterventionCreneaux::getCreneau).orElse(null);
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
                .technicien(technicien == null ? null : (TechnicienResponseDto) technicienService.toDto(technicien))
                .creneau(toCreneauResponse(creneau))
                .build();
    }

    /**
     * Crée une nouvelle intervention ainsi que ses associations technicien et créneau.
     *
     * @param requestDto les informations de l'intervention à créer
     * @return l'intervention créée
     */
    @Override
    public ResponseDto create(RequestDto requestDto) {
        InterventionRequestDto interventionRequest = (InterventionRequestDto) requestDto;
        TypeIntervention typeIntervention = typeInterventionRepository.findById(interventionRequest.getTypeInterventionId())
                .orElseThrow(() -> new RuntimeException("Type d'intervention non trouvé"));
        Client client = clientRepository.findById(interventionRequest.getClientId())
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        Intervention intervention = interventionRepository.save(toEntity(interventionRequest, typeIntervention, client));
        saveAssociations(intervention, interventionRequest);
        return toDto(intervention);
    }

    /**
     * Modifie une intervention existante et réassocie technicien et créneau.
     *
     * @param id L'identifiant de l'intervention à modifier.
     * @param requestDto Les données de la requête pour modifier l'intervention.
     * @return ResponseDto Les détails de l'intervention modifiée.
     */
    @Override
    public ResponseDto update(Long id, RequestDto requestDto) {
        InterventionRequestDto interventionRequest = (InterventionRequestDto) requestDto;
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
     * La suppression des associations doit précéder la suppression de l'intervention,
     * sous peine de violation des contraintes de clé étrangère.
     *
     * @param id L'identifiant de l'intervention à supprimer.
     * @return ResponseDto Les détails de l'intervention supprimée.
     * @throws Throwable si l'intervention à supprimer n'existe pas
     */
    @Override
    public ResponseDto delete(Long id) throws Throwable {
        Intervention intervention = interventionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Intervention non trouvée"));
        ResponseDto response = toDto(intervention);
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
                .map(InterventionResponseDto.class::cast)
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
                .map(InterventionResponseDto.class::cast)
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
                .map(InterventionResponseDto.class::cast)
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

    /**
     * Construit la réponse d'un créneau.
     *
     * @param creneau L'entité créneau (peut être null).
     * @return CreneauResponseDto La réponse du créneau ou null.
     */
    private CreneauResponseDto toCreneauResponse(Creneau creneau) {
        if (creneau == null) {
            return null;
        }
        return CreneauResponseDto.builder()
                .id(creneau.getId())
                .dateDebut(creneau.getDateDebut())
                .dateFin(creneau.getDateFin())
                .build();
    }
}
