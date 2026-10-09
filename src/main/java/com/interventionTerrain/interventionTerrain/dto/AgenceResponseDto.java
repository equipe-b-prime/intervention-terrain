package com.interventionTerrain.interventionTerrain.dto;

import com.interventionTerrain.interventionTerrain.dto.CommuneResponseDto;
import lombok.*;

/**
 * Réponse contenant les données d'une agence, avec la commune associée.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgenceResponseDto implements ResponseDto {
    private Long id;
    private String nom;
    private String ville;
    private String responsable;
    private CommuneResponseDto commune;
}