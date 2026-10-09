package com.interventionTerrain.interventionTerrain.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
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
    private LocalDateTime dateDebut;
    @Column(nullable = false)
    private LocalDateTime dateFin;
}
