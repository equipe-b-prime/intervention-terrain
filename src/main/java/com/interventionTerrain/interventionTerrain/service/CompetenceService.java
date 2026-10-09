package com.interventionTerrain.interventionTerrain.service;

import com.interventionTerrain.interventionTerrain.dto.CompetenceRequestDto;
import com.interventionTerrain.interventionTerrain.dto.CompetenceResponseDto;
import com.interventionTerrain.interventionTerrain.dto.RequestDto;
import com.interventionTerrain.interventionTerrain.dto.ResponseDto;
import org.springframework.stereotype.Service;

import com.interventionTerrain.interventionTerrain.entity.Competence;
import com.interventionTerrain.interventionTerrain.repository.CompetenceRepository;

/**
 * Service métier gérant les compétences.
 * Fournit la création, la modification et les conversions entité/DTO.
 * La lecture et la suppression sont héritées de {@link AbstractCrudService}.
 */
@Service
public class CompetenceService extends AbstractCrudService {

    private final CompetenceRepository competenceRepository;

    /**
     * Constructeur injectant le repository des compétences.
     *
     * @param competenceRepository le repository des compétences
     */
    public CompetenceService(CompetenceRepository competenceRepository) {
        super(competenceRepository);
        this.competenceRepository = competenceRepository;
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
     * Convertit une entité compétence en objet de réponse.
     *
     * @param entity L'entité compétence à convertir.
     * @return ResponseDto Les détails de la compétence convertie.
     */
    @Override
    public ResponseDto toDto(Object entity) {
        Competence competence = (Competence) entity;
        return CompetenceResponseDto.builder()
                .id(competence.getId())
                .nom(competence.getNom())
                .description(competence.getDescription())
                .build();
    }

    /**
     * Crée une nouvelle compétence.
     *
     * @param requestDto les informations de la compétence à créer
     * @return la compétence créée
     */
    @Override
    public ResponseDto create(RequestDto requestDto) {
        CompetenceRequestDto competenceRequest = (CompetenceRequestDto) requestDto;
        return toDto(competenceRepository.save(toEntity(competenceRequest)));
    }

    /**
     * Modifie une compétence existante.
     *
     * @param id L'identifiant de la compétence à modifier.
     * @param requestDto Les données de la requête pour modifier la compétence.
     * @return ResponseDto Les détails de la compétence modifiée.
     */
    @Override
    public ResponseDto update(Long id, RequestDto requestDto) {
        CompetenceRequestDto competenceRequest = (CompetenceRequestDto) requestDto;
        Competence competence = competenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compétence non trouvée"));
        competence.setNom(competenceRequest.getNom());
        competence.setDescription(competenceRequest.getDescription());
        return toDto(competenceRepository.save(competence));
    }
}
