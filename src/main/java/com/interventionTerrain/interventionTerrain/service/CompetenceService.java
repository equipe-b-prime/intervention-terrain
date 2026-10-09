package com.interventionTerrain.interventionTerrain.service;

import com.interventionTerrain.interventionTerrain.dto.CompetenceRequestDto;
import com.interventionTerrain.interventionTerrain.dto.CompetenceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import com.interventionTerrain.interventionTerrain.entity.Competence;
import com.interventionTerrain.interventionTerrain.repository.CompetenceRepository;

/**
 * Service métier gérant les compétences.
 * Fournit les opérations CRUD ainsi que les conversions entité/DTO.
 */
@Service
@RequiredArgsConstructor
public class CompetenceService {

    private final CompetenceRepository competenceRepository;

    /**
     * Convertit une entité compétence en objet de réponse.
     *
     * @param competence L'entité compétence à convertir.
     * @return CompetenceResponseDto Les détails de la compétence convertie.
     */
    public CompetenceResponseDto toDto(Competence competence) {
        return CompetenceResponseDto.builder()
                .id(competence.getId())
                .nom(competence.getNom())
                .description(competence.getDescription())
                .build();
    }

    /**
     * Convertit les données d'une requête en entité compétence.
     *
     * @param competenceRequest Les données de la requête pour créer la compétence.
     * @return Competence L'entité compétence créée.
     */
    public Competence toEntity(CompetenceRequestDto competenceRequest) {
        return Competence.builder()
                .nom(competenceRequest.getNom())
                .description(competenceRequest.getDescription())
                .build();
    }

    /**
     * Crée une nouvelle compétence.
     *
     * @param competenceRequest les informations de la compétence à créer
     * @return la compétence créée
     */
    public CompetenceResponseDto createCompetence(CompetenceRequestDto competenceRequest) {
        return toDto(competenceRepository.save(toEntity(competenceRequest)));
    }

    /**
     * Récupère une compétence à partir de son identifiant.
     *
     * @param id L'identifiant de la compétence à récupérer.
     * @return CompetenceResponseDto Les détails de la compétence récupérée.
     */
    public CompetenceResponseDto getCompetenceById(Long id) {
        Competence competence = competenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compétence non trouvée"));
        return toDto(competence);
    }

    /**
     * Récupère la liste de toutes les compétences.
     *
     * @return List<CompetenceResponseDto> La liste des compétences récupérées.
     */
    public List<CompetenceResponseDto> getAllCompetences() {
        return competenceRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * Modifie une compétence existante.
     *
     * @param id L'identifiant de la compétence à modifier.
     * @param competenceRequest Les données de la requête pour modifier la compétence.
     * @return CompetenceResponseDto Les détails de la compétence modifiée.
     */
    public CompetenceResponseDto updateCompetenceById(Long id, CompetenceRequestDto competenceRequest) {
        Competence competence = competenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compétence non trouvée"));
        competence.setNom(competenceRequest.getNom());
        competence.setDescription(competenceRequest.getDescription());
        return toDto(competenceRepository.save(competence));
    }

    /**
     * Supprime une compétence à partir de son identifiant.
     *
     * @param id L'identifiant de la compétence à supprimer.
     * @return CompetenceResponseDto Les détails de la compétence supprimée.
     */
    public CompetenceResponseDto deleteCompetenceById(Long id) {
        Competence competence = competenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compétence non trouvée"));
        competenceRepository.deleteById(id);
        return toDto(competence);
    }
}