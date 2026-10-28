package com.interventionTerrain.interventionTerrain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TauxReussiteDetailDto {
    private Long typeInterventionId;
    private String typeInterventionNom;
    private long nbTerminee;
    private long nbEchouee;
    private double taux;
}