package com.interventionTerrain.interventionTerrain.entity;

import jakarta.persistence.*;
/**
 * Représente status de intervention
 */
@Entity
@Table(name ="statut" )
public class Statut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String libelle ;
}
