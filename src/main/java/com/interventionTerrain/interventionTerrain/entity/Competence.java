package com.interventionTerrain.interventionTerrain.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represente la competence que peut avoir un  technicien
 */
@Entity
@Table(name = "competence")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Competence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 120)
    private String libelle;
    @Column(length = 500)
    private String description;

}
