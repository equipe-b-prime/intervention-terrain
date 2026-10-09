package com.interventionTerrain.interventionTerrain.dto;

import lombok.*;

/**
 * Réponse contenant les données d'un type d'intervention.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeInterventionResponseDto {
    private Long id;
    private String nom;
    private String description;
    private Integer delaiHeures;
}