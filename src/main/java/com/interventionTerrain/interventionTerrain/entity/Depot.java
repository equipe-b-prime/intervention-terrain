package com.interventionTerrain.interventionTerrain.entity;
import jakarta.persistence.*;
/**
 * Represente le depot dans lequel le les stocks de pieces est reparti
 */
@Entity
@Table(name = "depot")
public class Depot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 120)
    private String nom;

}
