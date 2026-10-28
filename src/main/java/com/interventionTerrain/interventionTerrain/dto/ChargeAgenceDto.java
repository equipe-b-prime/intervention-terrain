package com.interventionTerrain.interventionTerrain.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChargeAgenceDto {
    private Long agenceId;
    private String agenceNom;
    private List<ChargeTechDto> techniciens;
    private long totalInterventions;
    private double totalHeures;
}