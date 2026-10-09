package com.interventionTerrain.interventionTerrain.entity;
import jakarta.persistence.*;
import lombok.*;
/**
 * Represente le client chez qui l'intervention est faite
 */
@Entity
@Table(name = "client")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {
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
    @Column(length = 255)
    private String adresse;
}
