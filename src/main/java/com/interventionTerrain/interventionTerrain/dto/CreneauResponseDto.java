package com.interventionTerrain.interventionTerrain.dto;

import lombok.*;

import java.time.LocalTime;

/**
 * Réponse contenant les données d'un créneau.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreneauResponseDto {
    private Long id;
    private LocalTime dateDebut;
    private LocalTime dateFin;
}