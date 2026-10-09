package com.interventionTerrain.interventionTerrain.dto;

import com.interventionTerrain.interventionTerrain.dto.EquipeResponseDto;
import lombok.*;

/**
 * Réponse contenant les données d'un technicien, avec l'équipe associée.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TechnicienResponseDto implements ResponseDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String matricule;
    private EquipeResponseDto equipe;
}