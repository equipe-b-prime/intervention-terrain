package com.interventionTerrain.interventionTerrain.entity;
import jakarta.persistence.*;
/**
 * Représente la table associative entre créneau et intervention.
 */
@Entity
@Table(name = "piece")
public class Piece {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 120)
    private String nom;
    @Column(nullable = false, length = 120)
    private String reference;
    @Column(length = 500)
    private String description;
}
