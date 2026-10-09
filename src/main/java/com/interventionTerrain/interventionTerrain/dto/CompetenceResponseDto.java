package com.interventionTerrain.interventionTerrain.dto;

import lombok.*;

/**
 * Réponse contenant les données d'une compétence.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompetenceResponseDto {
    private Long id;
    private String nom;
    private String description;
}