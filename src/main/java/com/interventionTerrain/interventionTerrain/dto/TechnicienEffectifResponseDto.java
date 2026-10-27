package com.interventionTerrain.interventionTerrain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TechnicienEffectifResponseDto implements ResponseDto {
    private Long id;
    private String nom;
    private String prenom;
    private String matricule;
    private long nbCompetencesValides;
}