package com.interventionTerrain.interventionTerrain.entity;
import com.interventionTerrain.interventionTerrain.entity.TypeIntervention;
import jakarta.persistence.*;
/**
 * Représente la table associative entre piece et intervention.
 */
@Entity
@Table(name = "intervention_piece")
public class InterventionPiece {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer quantiteUtilisee;
    @ManyToOne
    @JoinColumn(name = "type_intervention_id", nullable = false)
    private TypeIntervention typeIntervention;
    @ManyToOne
    @JoinColumn(name = "piece_id", nullable = false)
    private Piece piece;
}
