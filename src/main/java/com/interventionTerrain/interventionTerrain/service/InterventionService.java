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
import com.interventionTerrain.interventionTerrain.exception.NotFoundException;
import com.interventionTerrain.interventionTerrain.repository.InterventionCreneauxRepository;
import com.interventionTerrain.interventionTerrain.repository.InterventionTechnicienRepository;
import com.interventionTerrain.interventionTerrain.repository.TechnicienRepository;
import com.interventionTerrain.interventionTerrain.repository.TypeInterventionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import com.interventionTerrain.interventionTerrain.entity.Intervention;
import com.interventionTerrain.interventionTerrain.repository.InterventionRepository;

@Service
public class InterventionService extends AbstractCrudService {

    private static final String STATUT_PLANIFIEE = "PLANIFIEE";
    private static final Set<String> STATUTS_VALIDES = Set.of("PLANIFIEE", "EN_COURS", "TERMINEE", "ECHOUEE", "ANNULEE");
    private static final long MAX_PERIOD_DAYS = 90;

    private final InterventionRepository interventionRepository;
    private final ClientRepository clientRepository;
    private final TypeInterventionRepository typeInterventionRepository;
    private final TechnicienRepository technicienRepository;
    private final CreneauRepository creneauRepository;
    private final InterventionTechnicienRepository interventionTechnicienRepository;
    private final InterventionCreneauxRepository interventionCreneauxRepository;
    private final TechnicienService technicienService;

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

    public Intervention toEntity(InterventionRequestDto interventionRequest, TypeIntervention typeIntervention, Client client) {
        return Intervention.builder()
                .datePlanifiee(interventionRequest.getDatePlanifiee())
                .statut(STATUT_PLANIFIEE)
                .commentaire(interventionRequest.getCommentaire())
                .reference(interventionRequest.getReference())
                .typeIntervention(typeIntervention)
                .client(client)
                .build();
    }

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
                        .commune(intervention.getClient().getCommune())
                        .build())
                .technicien(technicien == null ? null : (TechnicienResponseDto) technicienService.toDto(technicien))
                .creneau(toCreneauResponse(creneau))
                .build();
    }

    @Override
    @Transactional
    public ResponseDto create(RequestDto requestDto) {
        InterventionRequestDto interventionRequest = (InterventionRequestDto) requestDto;

        TypeIntervention typeIntervention = typeInterventionRepository.findById(interventionRequest.getTypeInterventionId())
                .orElseThrow(() -> new NotFoundException("Type d'intervention non trouvé"));
        Client client = clientRepository.findById(interventionRequest.getClientId())
                .orElseThrow(() -> new NotFoundException("Client non trouvé"));
        Technicien technicien = technicienRepository.findById(interventionRequest.getTechnicienId())
                .orElseThrow(() -> new NotFoundException("Technicien non trouvé"));
        Creneau creneau = creneauRepository.findById(interventionRequest.getCreneauId())
                .orElseThrow(() -> new NotFoundException("Créneau non trouvé"));

        if (!creneau.getDateFin().isAfter(creneau.getDateDebut())) {
            throw new IllegalArgumentException("L'heure de fin doit être postérieure à l'heure de début");
        }
        if (creneau.getDateDebut().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Le créneau doit être dans le futur");
        }

        Intervention intervention = toEntity(interventionRequest, typeIntervention, client);
        intervention = interventionRepository.save(intervention);

        interventionTechnicienRepository.save(InterventionTechnicien.builder()
                .intervention(intervention)
                .technicien(technicien)
                .estResponsable(Boolean.TRUE)
                .build());

        interventionCreneauxRepository.save(InterventionCreneaux.builder()
                .intervention(intervention)
                .creneau(creneau)
                .build());

        return toDto(intervention);
    }

    @Override
    @Transactional
    public ResponseDto update(Long id, RequestDto requestDto) {
        Intervention intervention = interventionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Intervention non trouvée"));

        if (!STATUT_PLANIFIEE.equals(intervention.getStatut())) {
            throw new IllegalStateException("Impossible de modifier une intervention qui n'est pas PLANIFIEE");
        }

        InterventionRequestDto interventionRequest = (InterventionRequestDto) requestDto;

        TypeIntervention typeIntervention = typeInterventionRepository.findById(interventionRequest.getTypeInterventionId())
                .orElseThrow(() -> new NotFoundException("Type d'intervention non trouvé"));
        Client client = clientRepository.findById(interventionRequest.getClientId())
                .orElseThrow(() -> new NotFoundException("Client non trouvé"));
        Technicien technicien = technicienRepository.findById(interventionRequest.getTechnicienId())
                .orElseThrow(() -> new NotFoundException("Technicien non trouvé"));
        Creneau creneau = creneauRepository.findById(interventionRequest.getCreneauId())
                .orElseThrow(() -> new NotFoundException("Créneau non trouvé"));

        if (!creneau.getDateFin().isAfter(creneau.getDateDebut())) {
            throw new IllegalArgumentException("L'heure de fin doit être postérieure à l'heure de début");
        }

        intervention.setDatePlanifiee(interventionRequest.getDatePlanifiee());
        intervention.setTypeIntervention(typeIntervention);
        intervention.setClient(client);
        intervention.setCommentaire(interventionRequest.getCommentaire());
        intervention.setReference(interventionRequest.getReference());
        interventionRepository.save(intervention);

        interventionTechnicienRepository.deleteByInterventionId(id);
        interventionTechnicienRepository.save(InterventionTechnicien.builder()
                .intervention(intervention)
                .technicien(technicien)
                .estResponsable(Boolean.TRUE)
                .build());

        interventionCreneauxRepository.deleteByInterventionId(id);
        interventionCreneauxRepository.save(InterventionCreneaux.builder()
                .intervention(intervention)
                .creneau(creneau)
                .build());

        return toDto(intervention);
    }

    @Override
    @Transactional
    public ResponseDto delete(Long id) throws Throwable {
        Intervention intervention = interventionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Intervention non trouvée"));

        if (!STATUT_PLANIFIEE.equals(intervention.getStatut())) {
            throw new IllegalStateException("Seules les interventions PLANIFIEE peuvent être supprimées");
        }

        ResponseDto response = toDto(intervention);
        interventionTechnicienRepository.deleteByInterventionId(id);
        interventionCreneauxRepository.deleteByInterventionId(id);
        interventionRepository.deleteById(id);
        return response;
    }

    @Override
    public Page<ResponseDto> getAll(int page, int taille) {
        Sort sort = Sort.by(Sort.Order.asc("datePlanifiee"));
        Pageable pageable = PageRequest.of(pageAutorisee(page), tailleAutorisee(taille), sort);
        return interventionRepository.findAll(pageable).map(this::toDto);
    }

    public List<ResponseDto> getHistoriqueByClient(Long clientId) {
        clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client non trouvé"));
        return interventionRepository.findByClientId(clientId).stream()
                .map(this::toDto)
                .toList();
    }

    public Page<ResponseDto> getAllFiltre(String statut, LocalDate du, LocalDate au, int page, int taille) {
        if (statut != null && !STATUTS_VALIDES.contains(statut)) {
            throw new IllegalArgumentException("Statut inconnu : " + statut);
        }
        LocalDateTime debut = du != null ? du.atStartOfDay() : LocalDateTime.of(1970, 1, 1, 0, 0);
        LocalDateTime fin = au != null ? au.plusDays(1).atStartOfDay() : LocalDateTime.of(2099, 12, 31, 23, 59);
        if (debut.isAfter(fin)) {
            throw new IllegalArgumentException("La date de début ne peut pas être postérieure à la date de fin");
        }
        Pageable pageable = PageRequest.of(pageAutorisee(page), tailleAutorisee(taille));
        List<Intervention> resultats;
        if (statut != null) {
            resultats = interventionRepository.findByStatutAndDatePlanifieeBetween(statut, debut, fin);
        } else {
            resultats = interventionRepository.findByDatePlanifieeBetween(debut, fin);
        }
        List<ResponseDto> dtos = resultats.stream().map(this::toDto).toList();
        return new org.springframework.data.domain.PageImpl<>(dtos, pageable, dtos.size());
    }

    public List<ResponseDto> getPlanning(Long technicienId, LocalDate du, LocalDate au) {
        technicienRepository.findById(technicienId)
                .orElseThrow(() -> new NotFoundException("Technicien non trouvé"));
        LocalDateTime debut = du != null ? du.atStartOfDay() : LocalDate.now().atStartOfDay();
        LocalDateTime fin = au != null ? au.plusDays(1).atStartOfDay() : debut.plusDays(7);
        if (ChronoUnit.DAYS.between(debut, fin) > MAX_PERIOD_DAYS) {
            throw new IllegalArgumentException("La période ne peut pas dépasser 3 mois");
        }
        return interventionRepository.findByTechnicienIdAndDatePlanifieeBetween(technicienId, debut, fin).stream()
                .map(this::toDto)
                .toList();
    }

    private InterventionResponseDto toInterventionResponse(Intervention intervention) {
        return (InterventionResponseDto) toDto(intervention);
    }

    private CreneauResponseDto toCreneauResponse(Creneau creneau) {
        if (creneau == null) return null;
        return CreneauResponseDto.builder()
                .id(creneau.getId())
                .dateDebut(creneau.getDateDebut())
                .dateFin(creneau.getDateFin())
                .build();
    }

    @Override
    protected String messageIntrouvable() {
        return "Intervention non trouvée";
    }
}