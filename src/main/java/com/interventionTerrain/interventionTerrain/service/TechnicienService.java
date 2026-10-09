package com.interventionTerrain.interventionTerrain.service;

import com.interventionTerrain.interventionTerrain.dto.AgenceResponseDto;
import com.interventionTerrain.interventionTerrain.dto.CommuneResponseDto;
import com.interventionTerrain.interventionTerrain.dto.CompetenceResponseDto;
import com.interventionTerrain.interventionTerrain.entity.Competence;
import com.interventionTerrain.interventionTerrain.entity.Equipe;
import com.interventionTerrain.interventionTerrain.repository.EquipeRepository;
import com.interventionTerrain.interventionTerrain.dto.EquipeResponseDto;
import com.interventionTerrain.interventionTerrain.dto.TechnicienRequestDto;
import com.interventionTerrain.interventionTerrain.dto.TechnicienResponseDto;
import com.interventionTerrain.interventionTerrain.repository.TechnicienCompetenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import com.interventionTerrain.interventionTerrain.entity.Technicien;
import com.interventionTerrain.interventionTerrain.repository.TechnicienRepository;

/**
 * Service métier gérant les techniciens.
 * Fournit les opérations CRUD ainsi que les conversions entité/DTO.
 */
@Service
@RequiredArgsConstructor
public class TechnicienService {

    private final TechnicienRepository technicienRepository;
    private final EquipeRepository equipeRepository;
    private final TechnicienCompetenceRepository technicienCompetenceRepository;


    /**
     * Convertit une entité technicien en objet de réponse.
     *
     * @param technicien L'entité technicien à convertir.
     * @return TechnicienResponseDto Les détails du technicien converti.
     */
    public TechnicienResponseDto toDto(Technicien technicien) {
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
     * Crée un nouveau technicien.
     *
     * @param technicienRequest les informations du technicien à créer
     * @return le technicien créé
     */
    public TechnicienResponseDto createTechnicien(TechnicienRequestDto technicienRequest) {
        Equipe equipe = equipeRepository.findById(technicienRequest.getEquipeId())
                .orElseThrow(() -> new RuntimeException("Équipe non trouvée"));
        return toDto(technicienRepository.save(toEntity(technicienRequest, equipe)));
    }

    /**
     * Récupère un technicien à partir de son identifiant.
     *
     * @param id L'identifiant du technicien à récupérer.
     * @return TechnicienResponseDto Les détails du technicien récupéré.
     */
    public TechnicienResponseDto getTechnicienById(Long id) {
        Technicien technicien = technicienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Technicien non trouvé"));
        return toDto(technicien);
    }

    /**
     * Récupère la liste de tous les techniciens.
     *
     * @return List<TechnicienResponseDto> La liste des techniciens récupérés.
     */
    public List<TechnicienResponseDto> getAllTechniciens() {
        return technicienRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * Modifie un technicien existant.
     *
     * @param id L'identifiant du technicien à modifier.
     * @param technicienRequest Les données de la requête pour modifier le technicien.
     * @return TechnicienResponseDto Les détails du technicien modifié.
     */
    public TechnicienResponseDto updateTechnicienById(Long id, TechnicienRequestDto technicienRequest) {
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
     * Supprime un technicien à partir de son identifiant.
     *
     * @param id L'identifiant du technicien à supprimer.
     * @return TechnicienResponseDto Les détails du technicien supprimé.
     */
    public TechnicienResponseDto deleteTechnicienById(Long id) {
        Technicien technicien = technicienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Technicien non trouvé"));
        technicienRepository.deleteById(id);
        return toDto(technicien);
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