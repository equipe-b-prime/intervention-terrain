package com.interventionTerrain.interventionTerrain.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupeAgenceDto {
    private String agenceNom;
    private List<TechnicienDisponibleDto> techniciens;
}