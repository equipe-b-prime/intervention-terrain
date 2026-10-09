package com.interventionTerrain.interventionTerrain.service;

import com.interventionTerrain.interventionTerrain.dto.AgenceResponseDto;
import com.interventionTerrain.interventionTerrain.dto.CommuneResponseDto;
import com.interventionTerrain.interventionTerrain.dto.CompetenceResponseDto;
import com.interventionTerrain.interventionTerrain.entity.Competence;
import com.interventionTerrain.interventionTerrain.entity.Equipe;
import com.interventionTerrain.interventionTerrain.repository.EquipeRepository;
import com.interventionTerrain.interventionTerrain.dto.EquipeResponseDto;
import com.interventionTerrain.interventionTerrain.dto.RequestDto;
import com.interventionTerrain.interventionTerrain.dto.ResponseDto;
import com.interventionTerrain.interventionTerrain.dto.TechnicienRequestDto;
import com.interventionTerrain.interventionTerrain.dto.TechnicienResponseDto;
import com.interventionTerrain.interventionTerrain.repository.TechnicienCompetenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import com.interventionTerrain.interventionTerrain.entity.Technicien;
import com.interventionTerrain.interventionTerrain.repository.TechnicienRepository;

/**
 * Service métier gérant les techniciens.
 * Fournit la création, la modification et les conversions entité/DTO.
 * La lecture et la suppression sont héritées de {@link AbstractCrudService}.
 */
@Service
public class TechnicienService extends AbstractCrudService {

    private final TechnicienRepository technicienRepository;
    private final EquipeRepository equipeRepository;
    private final TechnicienCompetenceRepository technicienCompetenceRepository;

    /**
     * Constructeur injectant les repositories du technicien.
     *
     * @param technicienRepository le repository des techniciens
     * @param equipeRepository le repository des équipes (résolution de la FK)
     * @param technicienCompetenceRepository le repository de l'association technicien/compétence
     */
    public TechnicienService(TechnicienRepository technicienRepository,
                             EquipeRepository equipeRepository,
                             TechnicienCompetenceRepository technicienCompetenceRepository) {
        super(technicienRepository);
        this.technicienRepository = technicienRepository;
        this.equipeRepository = equipeRepository;
        this.technicienCompetenceRepository = technicienCompetenceRepository;
    }

    /**
     * Convertit les données d'une requête en entité technicien.
     *
     * @param technicienRequest Les données de la requête pour créer le technicien.
     * @param equipe L'équipe associée au technicien.
     * @return Technicien L'entité technicien créée.
     */
    public Technicien toEntity(TechnicienRequestDto technicienRequest, Equipe equipe) {
        return Technicien.builder()
                .nom(technicienRequest.getNom())
                .prenom(technicienRequest.getPrenom())
                .email(technicienRequest.getEmail())
                .telephone(technicienRequest.getTelephone())
                .matricule(technicienRequest.getMatricule())
                .equipe(equipe)
                .build();
    }

    /**
     * Convertit une entité technicien en objet de réponse.
     *
     * @param entity L'entité technicien à convertir.
     * @return ResponseDto Les détails du technicien converti.
     */
    @Override
    public ResponseDto toDto(Object entity) {
        Technicien technicien = (Technicien) entity;
        return TechnicienResponseDto.builder()
                .id(technicien.getId())
                .nom(technicien.getNom())
                .prenom(technicien.getPrenom())
                .email(technicien.getEmail())
                .telephone(technicien.getTelephone())
                .matricule(technicien.getMatricule())
                .equipe(EquipeResponseDto.builder()
                        .id(technicien.getEquipe().getId())
                        .nom(technicien.getEquipe().getNom())
                        .agence(AgenceResponseDto.builder()
                                .id(technicien.getEquipe().getAgence().getId())
                                .nom(technicien.getEquipe().getAgence().getNom())
                                .ville(technicien.getEquipe().getAgence().getVille())
                                .responsable(technicien.getEquipe().getAgence().getResponsable())
                                .commune(CommuneResponseDto.builder()
                                        .id(technicien.getEquipe().getAgence().getCommune().getId())
                                        .nom(technicien.getEquipe().getAgence().getCommune().getNom())
                                        .codePostal(technicien.getEquipe().getAgence().getCommune().getCodePostal())
                                        .build())
                                .build())
                        .build())
                .build();
    }

    /**
     * Crée un nouveau technicien.
     *
     * @param requestDto les informations du technicien à créer
     * @return le technicien créé
     */
    @Override
    public ResponseDto create(RequestDto requestDto) {
        TechnicienRequestDto technicienRequest = (TechnicienRequestDto) requestDto;
        Equipe equipe = equipeRepository.findById(technicienRequest.getEquipeId())
                .orElseThrow(() -> new RuntimeException("Équipe non trouvée"));
        return toDto(technicienRepository.save(toEntity(technicienRequest, equipe)));
    }

    /**
     * Modifie un technicien existant.
     *
     * @param id L'identifiant du technicien à modifier.
     * @param requestDto Les données de la requête pour modifier le technicien.
     * @return ResponseDto Les détails du technicien modifié.
     */
    @Override
    public ResponseDto update(Long id, RequestDto requestDto) {
        TechnicienRequestDto technicienRequest = (TechnicienRequestDto) requestDto;
        Technicien technicien = technicienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Technicien non trouvé"));
        Equipe equipe = equipeRepository.findById(technicienRequest.getEquipeId())
                .orElseThrow(() -> new RuntimeException("Équipe non trouvée"));
        technicien.setNom(technicienRequest.getNom());
        technicien.setPrenom(technicienRequest.getPrenom());
        technicien.setEmail(technicienRequest.getEmail());
        technicien.setTelephone(technicienRequest.getTelephone());
        technicien.setMatricule(technicienRequest.getMatricule());
        technicien.setEquipe(equipe);
        return toDto(technicienRepository.save(technicien));
    }

    /**
     * Récupère les techniciens d'une agence (requête personnalisée avec jointure).
     *
     * @param agenceId L'identifiant de l'agence.
     * @return List<TechnicienResponseDto> Les techniciens de l'agence.
     */
    public List<TechnicienResponseDto> getTechniciensByAgence(Long agenceId) {
        return technicienRepository.findByAgenceId(agenceId).stream()
                .map(this::toDto)
                .map(TechnicienResponseDto.class::cast)
                .toList();
    }

    /**
     * Récupère les compétences d'un technicien (via la table associative).
     *
     * @param technicienId L'identifiant du technicien.
     * @return List<CompetenceResponseDto> Les compétences du technicien.
     */
    public List<CompetenceResponseDto> getCompetencesByTechnicien(Long technicienId) {
        return technicienCompetenceRepository.findByTechnicienId(technicienId).stream()
                .map(link -> toCompetenceResponse(link.getCompetence()))
                .toList();
    }

    /**
     * Construit la réponse d'une compétence.
     *
     * @param competence L'entité compétence.
     * @return CompetenceResponseDto La réponse de la compétence.
     */
    private CompetenceResponseDto toCompetenceResponse(Competence competence) {
        return CompetenceResponseDto.builder()
                .id(competence.getId())
                .nom(competence.getNom())
                .description(competence.getDescription())
                .build();
    }
}
