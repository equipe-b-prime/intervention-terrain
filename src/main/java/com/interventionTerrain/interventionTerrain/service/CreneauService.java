package com.interventionTerrain.interventionTerrain.service;

import com.interventionTerrain.interventionTerrain.dto.CreneauResponseDto;
import com.interventionTerrain.interventionTerrain.entity.Creneau;
import com.interventionTerrain.interventionTerrain.repository.CreneauRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreneauService {

    private final CreneauRepository creneauRepository;

    /**
     * Construit la réponse d'un créneau.
     *
     * @param creneau L'entité créneau (peut être null).
     * @return CreneauResponseDto La réponse du créneau ou null.
     */
    public CreneauResponseDto toDto(Creneau creneau) {
        if (creneau == null) {
            return null;
        }
        return CreneauResponseDto.builder()
                .id(creneau.getId())
                .dateDebut(creneau.getDateDebut())
                .dateFin(creneau.getDateFin())
                .build();
    }
}
