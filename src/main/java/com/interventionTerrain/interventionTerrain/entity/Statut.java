package com.interventionTerrain.interventionTerrain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "statut")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Statut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String libelle;
}