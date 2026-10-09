package com.interventionTerrain.interventionTerrain.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Requête de création ou de modification d'une intervention.
 * Le type d'intervention et le client sont transmis via leurs identifiants.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterventionRequestDto {
    private Long id;
    private LocalDateTime datePlanifiee;
    private String statut;
    private String commentaire;
    private String reference;
    private Long typeInterventionId;
    private Long clientId;
    private Long technicienId;
    private Long creneauId;
}