package com.interventionTerrain.interventionTerrain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Réponse contenant les données d'une compétence.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompetenceResponseDto implements ResponseDto {
    private Long id;
    private String libelle;
    private String description;
}