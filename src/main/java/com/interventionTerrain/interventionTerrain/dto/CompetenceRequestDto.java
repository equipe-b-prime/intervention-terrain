package com.interventionTerrain.interventionTerrain.dto;

import com.interventionTerrain.interventionTerrain.service.AbstractClean;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompetenceRequestDto extends AbstractClean implements RequestDto {

    private Long id;

    @NotBlank(message = "Le libelle est obligatoire")
    private String libelle;

    private String description;

    @NotNull(message = "La date d'obtention est obligatoire")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateObtention;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateExpiration;

    public void setLibelle(String libelle) {
        this.libelle = cleanText(libelle);
    }

    public void setDescription(String description) {
        this.description = cleanText(description);
    }
}