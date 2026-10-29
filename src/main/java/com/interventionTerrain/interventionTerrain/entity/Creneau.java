package com.interventionTerrain.interventionTerrain.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;
/**
 * Represente le creneau de temps pour une intervention
 */
@Entity
@Table(name = "creneau")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Creneau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalTime dateDebut;
    @Column(nullable = false)
    private LocalTime dateFin;
}
