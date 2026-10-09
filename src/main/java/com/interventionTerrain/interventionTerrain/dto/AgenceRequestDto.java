package com.interventionTerrain.interventionTerrain.dto;

import lombok.*;

/**
 * Requête de création ou de modification d'une agence.
 * La commune associée est transmise via son identifiant.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgenceRequestDto implements RequestDto {
    private Long id;
    private String nom;
    private String ville;
    private String responsable;
    private Long communeId;
}