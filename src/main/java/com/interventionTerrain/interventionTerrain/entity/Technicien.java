package com.interventionTerrain.interventionTerrain.entity;
import com.interventionTerrain.interventionTerrain.entity.Equipe;
import jakarta.persistence.*;
import lombok.*;
/**
 * Représente le techncien qui est affecté à une équipe et qui peut intervenir sur des interventions
 */
@Entity
@Table(name = "technicien")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Technicien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nom;
    @Column(nullable = false, length = 100)
    private String prenom;
    @Column(length = 180)
    private String email;
    @Column(length = 30)
    private String telephone;
    @Column(length = 50)
    private String matricule;
    @ManyToOne
    @JoinColumn(name = "equipe_id", nullable = false)
    private Equipe equipe;
}
