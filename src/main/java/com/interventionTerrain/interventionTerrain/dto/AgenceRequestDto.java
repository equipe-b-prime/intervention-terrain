package com.interventionTerrain.interventionTerrain.dto;

import com.interventionTerrain.interventionTerrain.service.AbstractClean;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Requête de création ou de modification d'une agence.
 * La commune associée est transmise via son identifiant.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgenceRequestDto extends AbstractClean implements RequestDto {
    private Long id;
    @NotBlank(message = "le nom est obligatoire")
    private String nom;
    @NotBlank(message = "la ville est obligatoire")
    private String ville;
    private String responsable;
    private Long communeId;
    public void setNom(String nom) {
        this.nom = cleanText(nom);
    }
    public void setVille(String ville) {
        this.ville = cleanText(ville);
    }

}