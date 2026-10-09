package com.interventionTerrain.interventionTerrain.dto;

import com.interventionTerrain.interventionTerrain.dto.AgenceResponseDto;
import lombok.*;

/**
 * Réponse contenant les données d'une équipe.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EquipeResponseDto {
    private Long id;
    private String nom;
    private AgenceResponseDto agence;
}