package com.interventionTerrain.interventionTerrain.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TauxReussiteGlobalDto {
    private Long agenceId;
    private String agenceNom;
    private String mois;
    private double tauxGlobal;
    private List<TauxReussiteDetailDto> details;
}