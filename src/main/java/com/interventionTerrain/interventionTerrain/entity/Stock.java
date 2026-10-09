package com.interventionTerrain.interventionTerrain.entity;
import jakarta.persistence.*;
/**
 * Représente le stock où nous retrouvons les pièces dans les dépôts
 */
@Entity
@Table(name = "stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer quantite;
    @ManyToOne
    @JoinColumn(name = "depot_id", nullable = false)
    private Depot depot;
    @ManyToOne
    @JoinColumn(name = "piece_id", nullable = false)
    private Piece piece;
}
