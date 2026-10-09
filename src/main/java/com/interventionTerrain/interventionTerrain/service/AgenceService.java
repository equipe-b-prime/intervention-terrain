package com.interventionTerrain.interventionTerrain.service;

import com.interventionTerrain.interventionTerrain.dto.AgenceRequestDto;
import com.interventionTerrain.interventionTerrain.dto.AgenceResponseDto;
import com.interventionTerrain.interventionTerrain.repository.CommuneRepository;
import com.interventionTerrain.interventionTerrain.dto.CommuneResponseDto;
import com.interventionTerrain.interventionTerrain.entity.Commune;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import com.interventionTerrain.interventionTerrain.entity.Agence;
import com.interventionTerrain.interventionTerrain.repository.AgenceRepository;

/**
 * Service métier gérant les agences.
 * Fournit les opérations CRUD ainsi que les conversions entité/DTO.
 */
@Service
@RequiredArgsConstructor
public class AgenceService {

    private final AgenceRepository agenceRepository;
    private final CommuneRepository communeRepository;

    /**
     * Convertit une entité agence en objet de réponse.
     *
     * @param agence L'entité agence à convertir.
     * @return AgenceResponseDto Les détails de l'agence convertie.
     */
    public AgenceResponseDto toDto(Agence agence) {
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
     * Crée une nouvelle agence.
     *
     * @param agenceRequest les informations de l'agence à créer
     * @return l'agence créée
     */
    public AgenceResponseDto createAgence(AgenceRequestDto agenceRequest) {
        Commune commune = communeRepository.findById(agenceRequest.getCommuneId())
                .orElseThrow(() -> new RuntimeException("Commune non trouvée"));
        return toDto(agenceRepository.save(toEntity(agenceRequest, commune)));
    }

    /**
     * Récupère une agence à partir de son identifiant.
     *
     * @param id L'identifiant de l'agence à récupérer.
     * @return AgenceResponseDto Les détails de l'agence récupérée.
     */
    public AgenceResponseDto getAgenceById(Long id) {
        Agence agence = agenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agence non trouvée"));
        return toDto(agence);
    }

    /**
     * Récupère la liste de toutes les agences.
     *
     * @return List<AgenceResponseDto> La liste des agences récupérées.
     */
    public List<AgenceResponseDto> getAllAgences() {
        return agenceRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * Modifie une agence existante.
     *
     * @param id L'identifiant de l'agence à modifier.
     * @param agenceRequest Les données de la requête pour modifier l'agence.
     * @return AgenceResponseDto Les détails de l'agence modifiée.
     */
    public AgenceResponseDto updateAgenceById(Long id, AgenceRequestDto agenceRequest) {
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

    /**
     * Supprime une agence à partir de son identifiant.
     *
     * @param id L'identifiant de l'agence à supprimer.
     * @return AgenceResponseDto Les détails de l'agence supprimée.
     */
    public AgenceResponseDto deleteAgenceById(Long id) {
        Agence agence = agenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agence non trouvée"));
        agenceRepository.deleteById(id);
        return toDto(agence);
    }
}