package com.interventionTerrain.interventionTerrain.dto;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "le nom est obligatoire")
    private String nom;
    @NotBlank(message = "le prenom est obligatoire")
    private String prenom;
    private String email;
    @NotBlank(message = "le telephone est obligatoire")
    private String telephone;
    @NotBlank(message = "l'adresse est obligatoire")
    private String adresse;
    @NotBlank(message = "la commune est obligatoire")
    private String commune;
}