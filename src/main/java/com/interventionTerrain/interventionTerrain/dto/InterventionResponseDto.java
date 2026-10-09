package com.interventionTerrain.interventionTerrain.dto;

import com.interventionTerrain.interventionTerrain.dto.ClientResponseDto;
import com.interventionTerrain.interventionTerrain.dto.CreneauResponseDto;
import com.interventionTerrain.interventionTerrain.dto.TechnicienResponseDto;
import com.interventionTerrain.interventionTerrain.dto.TypeInterventionResponseDto;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Réponse contenant les données d'une intervention, avec le type d'intervention et le client associés.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterventionResponseDto implements ResponseDto {
    private Long id;
    private LocalDateTime datePlanifiee;
    private String statut;
    private String commentaire;
    private String reference;
    private TypeInterventionResponseDto typeIntervention;
    private ClientResponseDto client;
    private TechnicienResponseDto technicien;
    private CreneauResponseDto creneau;
}