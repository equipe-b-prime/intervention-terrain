package com.interventionTerrain.interventionTerrain.dto;

import lombok.*;

/**
 * Requête de création ou de modification d'une compétence.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompetenceRequestDto implements RequestDto {
    private Long id;
    private String nom;
    private String description;
}