package com.interventionTerrain.interventionTerrain.service;

import com.interventionTerrain.interventionTerrain.dto.AgenceRequestDto;
import com.interventionTerrain.interventionTerrain.dto.AgenceResponseDto;
import com.interventionTerrain.interventionTerrain.dto.CommuneResponseDto;
import com.interventionTerrain.interventionTerrain.dto.RequestDto;
import com.interventionTerrain.interventionTerrain.dto.ResponseDto;
import com.interventionTerrain.interventionTerrain.dto.TechnicienEffectifResponseDto;
import com.interventionTerrain.interventionTerrain.exception.DoublonRessourceException;
import com.interventionTerrain.interventionTerrain.exception.NotFoundException;
import com.interventionTerrain.interventionTerrain.exception.RessourceUtiliseeException;
import com.interventionTerrain.interventionTerrain.repository.CommuneRepository;
import com.interventionTerrain.interventionTerrain.repository.TechnicienRepository;
import com.interventionTerrain.interventionTerrain.repository.TechnicienCompetenceRepository;
import com.interventionTerrain.interventionTerrain.entity.Commune;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.interventionTerrain.interventionTerrain.entity.Agence;
import com.interventionTerrain.interventionTerrain.repository.AgenceRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AgenceService extends AbstractCrudService {

    private final AgenceRepository agenceRepository;
    private final CommuneRepository communeRepository;
    private final TechnicienRepository technicienRepository;
    private final TechnicienCompetenceRepository technicienCompetenceRepository;

    public AgenceService(AgenceRepository agenceRepository,
                         CommuneRepository communeRepository,
                         TechnicienRepository technicienRepository,
                         TechnicienCompetenceRepository technicienCompetenceRepository) {
        super(agenceRepository);
        this.agenceRepository = agenceRepository;
        this.communeRepository = communeRepository;
        this.technicienRepository = technicienRepository;
        this.technicienCompetenceRepository = technicienCompetenceRepository;
    }

    public Agence toEntity(AgenceRequestDto agenceRequest, Commune commune) {
        return Agence.builder()
                .nom(agenceRequest.getNom())
                .ville(agenceRequest.getVille())
                .responsable(agenceRequest.getResponsable())
                .commune(commune)
                .build();
    }

    @Override
    public ResponseDto toDto(Object entity) {
        Agence agence = (Agence) entity;
        return AgenceResponseDto.builder()
                .id(agence.getId())
                .nom(agence.getNom())
                .ville(agence.getVille())
                .responsable(agence.getResponsable())
                .commune(CommuneResponseDto.builder()
                        .id(agence.getCommune().getId())
                        .nom(agence.getCommune().getNom())
                        .codePostal(agence.getCommune().getCodePostal())
                        .departementId(agence.getCommune().getDepartement().getId())
                        .build())
                .build();
    }

    @Override
    public Page<ResponseDto> getAll(int page, int taille) {
        Sort sort = Sort.by(Sort.Order.asc("ville"), Sort.Order.asc("nom"));
        Pageable pageable = PageRequest.of(pageAutorisee(page), tailleAutorisee(taille), sort);
        return agenceRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    public ResponseDto create(RequestDto requestDto) {
        AgenceRequestDto agenceRequest = (AgenceRequestDto) requestDto;
        if (agenceRepository.existsByNomIgnoreCase(agenceRequest.getNom())) {
            throw new DoublonRessourceException("Une agence nommée '" + agenceRequest.getNom() + "' existe déjà");
        }
        Commune commune = communeRepository.findById(agenceRequest.getCommuneId())
                .orElseThrow(() -> new NotFoundException("Commune non trouvée"));
        return toDto(agenceRepository.save(toEntity(agenceRequest, commune)));
    }

    @Override
    public ResponseDto update(Long id, RequestDto requestDto) {
        AgenceRequestDto agenceRequest = (AgenceRequestDto) requestDto;
        Agence agence = agenceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageIntrouvable()));
        if (agenceRepository.existsByNomIgnoreCaseAndIdNot(agenceRequest.getNom(), id)) {
            throw new DoublonRessourceException("Une agence nommée '" + agenceRequest.getNom() + "' existe déjà");
        }
        boolean villeModifiee = !java.util.Objects.equals(agence.getVille(), agenceRequest.getVille());
        if (villeModifiee && technicienRepository.findByAgenceId(id).size() > 0) {
            throw new IllegalArgumentException("La ville d'une agence est figée dès que des techniciens y sont rattachés");
        }
        Commune commune = communeRepository.findById(agenceRequest.getCommuneId())
                .orElseThrow(() -> new NotFoundException("Commune non trouvée"));
        agence.setNom(agenceRequest.getNom());
        agence.setVille(agenceRequest.getVille());
        agence.setResponsable(agenceRequest.getResponsable());
        agence.setCommune(commune);
        return toDto(agenceRepository.save(agence));
    }

    @Override
    public ResponseDto delete(Long id) throws Throwable {
        Agence agence = agenceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageIntrouvable()));
        if (technicienRepository.findByAgenceId(id).size() > 0) {
            throw new RessourceUtiliseeException("Agence", id, "Technicien");
        }
        ResponseDto response = toDto(agence);
        agenceRepository.deleteById(id);
        return response;
    }

    public List<TechnicienEffectifResponseDto> getEffectif(Long agenceId) {
        agenceRepository.findById(agenceId)
                .orElseThrow(() -> new NotFoundException(messageIntrouvable()));
        Map<Long, Long> validesParTechnicien = new HashMap<>();
        for (Object[] ligne : technicienCompetenceRepository.compterValidesParAgence(agenceId, LocalDate.now())) {
            validesParTechnicien.put((Long) ligne[0], (Long) ligne[1]);
        }
        return technicienRepository.findByAgenceId(agenceId).stream()
                .sorted(java.util.Comparator.comparing(t -> t.getNom().toLowerCase()))
                .map(t -> TechnicienEffectifResponseDto.builder()
                        .id(t.getId())
                        .nom(t.getNom())
                        .prenom(t.getPrenom())
                        .matricule(t.getMatricule())
                        .nbCompetencesValides(validesParTechnicien.getOrDefault(t.getId(), 0L))
                        .build())
                .toList();
    }

    public ResponseDto getByNom(String nom) {
        Agence agence = agenceRepository.findByNom(nom)
                .orElseThrow(() -> new NotFoundException(messageIntrouvable()));
        return toDto(agence);
    }

    @Override
    protected String messageIntrouvable() {
        return "Agence non trouvée";
    }
}