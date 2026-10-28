package com.interventionTerrain.interventionTerrain.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisponiblesResponseDto {
    private List<GroupeAgenceDto> agences;
    private String raisonFiltrage;
}