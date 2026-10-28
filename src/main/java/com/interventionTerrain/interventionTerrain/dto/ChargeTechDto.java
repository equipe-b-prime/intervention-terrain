package com.interventionTerrain.interventionTerrain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChargeTechDto {
    private Long id;
    private String nom;
    private String prenom;
    private long nbInterventions;
    private double totalHeures;
}