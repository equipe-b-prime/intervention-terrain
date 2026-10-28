package com.interventionTerrain.interventionTerrain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TechnicienDisponibleDto {
    private Long id;
    private String nom;
    private String prenom;
    private String matricule;
    private int chargeJour;
}