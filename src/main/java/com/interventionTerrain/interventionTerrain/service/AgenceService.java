package com.interventionTerrain.interventionTerrain.service;

import com.interventionTerrain.interventionTerrain.dto.AgenceRequestDto;
import com.interventionTerrain.interventionTerrain.dto.AgenceResponseDto;
import com.interventionTerrain.interventionTerrain.dto.CommuneResponseDto;
import com.interventionTerrain.interventionTerrain.dto.RequestDto;
import com.interventionTerrain.interventionTerrain.dto.ResponseDto;
import com.interventionTerrain.interventionTerrain.repository.CommuneRepository;
import com.interventionTerrain.interventionTerrain.entity.Commune;
import org.springframework.stereotype.Service;

import com.interventionTerrain.interventionTerrain.entity.Agence;
import com.interventionTerrain.interventionTerrain.repository.AgenceRepository;

/**
 * Service métier gérant les agences.
 * Fournit la création, la modification et les conversions entité/DTO.
 * La lecture et la suppression sont héritées de {@link AbstractCrudService}.
 */
@Service
public class AgenceService extends AbstractCrudService {

    private final AgenceRepository agenceRepository;
    private final CommuneRepository communeRepository;

    /**
     * Constructeur injectant le repository des agences et des communes.
     *
     * @param agenceRepository le repository des agences
     * @param communeRepository le repository des communes (résolution de la FK)
     */
    public AgenceService(AgenceRepository agenceRepository, CommuneRepository communeRepository) {
        super(agenceRepository);
        this.agenceRepository = agenceRepository;
        this.communeRepository = communeRepository;
    }

    /**
     * Convertit les données d'une requête en entité agence.
     *
     * @param agenceRequest Les données de la requête pour créer l'agence.
     * @param commune La commune associée à l'agence.
     * @return Agence L'entité agence créée.
     */
    public Agence toEntity(AgenceRequestDto agenceRequest, Commune commune) {
        return Agence.builder()
                .nom(agenceRequest.getNom())
                .ville(agenceRequest.getVille())
                .responsable(agenceRequest.getResponsable())
                .commune(commune)
                .build();
    }

    /**
     * Convertit une entité agence en objet de réponse.
     *
     * @param entity L'entité agence à convertir.
     * @return ResponseDto Les détails de l'agence convertie.
     */
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

    /**
     * Crée une nouvelle agence.
     *
     * @param requestDto les informations de l'agence à créer
     * @return l'agence créée
     */
    @Override
    public ResponseDto create(RequestDto requestDto) {
        AgenceRequestDto agenceRequest = (AgenceRequestDto) requestDto;
        Commune commune = communeRepository.findById(agenceRequest.getCommuneId())
                .orElseThrow(() -> new RuntimeException("Commune non trouvée"));
        return toDto(agenceRepository.save(toEntity(agenceRequest, commune)));
    }

    /**
     * Modifie une agence existante.
     *
     * @param id L'identifiant de l'agence à modifier.
     * @param requestDto Les données de la requête pour modifier l'agence.
     * @return ResponseDto Les détails de l'agence modifiée.
     */
    @Override
    public ResponseDto update(Long id, RequestDto requestDto) {
        AgenceRequestDto agenceRequest = (AgenceRequestDto) requestDto;
        Agence agence = agenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agence non trouvée"));
        Commune commune = communeRepository.findById(agenceRequest.getCommuneId())
                .orElseThrow(() -> new RuntimeException("Commune non trouvée"));
        agence.setNom(agenceRequest.getNom());
        agence.setVille(agenceRequest.getVille());
        agence.setResponsable(agenceRequest.getResponsable());
        agence.setCommune(commune);
        return toDto(agenceRepository.save(agence));
    }
}
