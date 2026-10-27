package com.interventionTerrain.interventionTerrain.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompetenceRequestDto implements RequestDto {
    private Long id;
    private String nom;
    private String description;
    private LocalDate dateObtention;
    private LocalDate dateExpiration;
}