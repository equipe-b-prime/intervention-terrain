package com.interventionTerrain.interventionTerrain.dto;

import lombok.*;

/**
 * Requête de création ou de modification d'un client.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestDto implements RequestDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;
}