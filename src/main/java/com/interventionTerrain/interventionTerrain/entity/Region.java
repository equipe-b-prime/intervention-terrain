package com.interventionTerrain.interventionTerrain.entity;
import jakarta.persistence.*;
/**
 * Représente la region dans laquelle se trouve l'agence.
 */
@Entity
@Table(name = "region")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nom;
}
