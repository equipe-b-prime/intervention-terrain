package com.interventionTerrain.interventionTerrain.entity;
import com.interventionTerrain.interventionTerrain.entity.Agence;
import jakarta.persistence.*;
import lombok.*;
/**
 * Represente l'equipe dans laquelle les technicen appartiennent
 */
@Entity
@Table(name = "equipe")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 120)
    private String nom;
    @ManyToOne()
    @JoinColumn(name = "agence_id", nullable = false)
    private Agence agence;
}
