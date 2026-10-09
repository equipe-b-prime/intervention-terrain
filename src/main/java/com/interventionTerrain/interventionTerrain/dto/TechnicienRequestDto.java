package com.interventionTerrain.interventionTerrain.dto;

import lombok.*;

/**
 * Requête de création ou de modification d'un technicien.
 * L'équipe associée est transmise via son identifiant.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TechnicienRequestDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String matricule;
    private Long equipeId;
}