package com.interventionTerrain.interventionTerrain.dto;

import lombok.*;

/**
 * Réponse contenant les données d'une commune.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommuneResponseDto {
    private Long id;
    private String nom;
    private String codePostal;
    private Long departementId;
}